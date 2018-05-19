package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SocketClient implements Client {

    private static final int PORT = 49152; //change to dynamic in some elegant way
    private static final String ADDRESS = "localhost";

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private JsonMessage loginMessage;
    private ExecutorService executor;
    private HeartbeatProtocolManager heartbeatProtocolManager;


    SocketClient() throws IOException {
        socket = new Socket(ADDRESS, PORT);
        executor = Executors.newSingleThreadExecutor();
        initializeConnectionStream();
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
    }

    private void initializeConnectionStream() throws IOException {
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    private JSONObject createMessage(String userName, String auth) {
        loginMessage = new JsonMessage();
        return loginMessage.createLoginMessage(userName, auth);
    }

    private void doActions() {
        try {
            connect();
            login();
            close();
        } catch (Exception e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void connect() {
        try {
            socket = new Socket(ADDRESS, PORT);
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            inKeyboard = new BufferedReader(new InputStreamReader(System.in));
            outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        } catch (IOException e) {

        }
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {

        }
    }

    private String getServerData() throws IOException {
        String data;
        StringBuilder partialJSON = new StringBuilder();
        while ((data = inSocket.readLine()) != null)
            partialJSON.append(data);
        return partialJSON.toString();
    }

    private void login() {
        try {
            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                outVideo.println("username:");
                String username = inKeyboard.readLine();
                outVideo.println("password:");
                String auth = inKeyboard.readLine();
                JSONObject message = createMessage(username, auth);
                outSocket.println(message);
                outSocket.flush();
                String jsonResponse = getServerData();
                Map<String, String> dataMap = JsonMessage.parseJsonData(jsonResponse);
                if (dataMap.get("login").equals("successful"))
                    initializeLobbyLink(dataMap, username);
                else if (dataMap.get("login").equals("register")) {
                    outVideo.println("Registering...");
                    jsonResponse = getServerData();
                    dataMap = JsonMessage.parseJsonData(jsonResponse);
                    if (dataMap.get("login").equals("successful"))
                        initializeLobbyLink(dataMap, username);
                    else
                        outVideo.println("Username already taken");
                } else if (dataMap.get("login").equals("error"))
                    outVideo.println("Username already logged on");
            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    private void initializeLobbyLink(Map<String, String> dataMap, String identifier) throws IOException {
        socket.close();
        socket = new Socket(ADDRESS, Integer.parseInt(dataMap.get("lobby_port")));
        initializeConnectionStream();
        String jsonResponse = getServerData();
        dataMap = JsonMessage.parseJsonData(jsonResponse);
        if (dataMap.get("login").equals("successful"))
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, Integer.parseInt(dataMap.get("heartbeat_port")), identifier);
        else
            outVideo.println("Second level auth");
        executor.submit(this);
    }

    public void run() {
        while (!executor.isShutdown()) {
            String data;
            StringBuilder partialJSON = new StringBuilder();
            try {
                while ((data = inSocket.readLine()) != null)
                    partialJSON.append(data);
            } catch (IOException exc) {

            }
            outVideo.println("message received");
        }
    }
}
