package it.polimi.ingsw.sagrada.network.client.socket;

import it.polimi.ingsw.sagrada.gui.LoginGuiController;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessage;
import it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan.DiscoverLan;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet.DiscoverInternet;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements Runnable, Client {

    private static final Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
    private static final int PORT = 49152; //change to dynamic in some elegant way
    private static final String ADDRESS = "localhost"; //just for now, next will be obtained in far smarter way

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private JsonMessage loginMessage;
    private ExecutorService executor;
    private String username;
    private int lobbyPort;
    private HeartbeatProtocolManager heartbeatProtocolManager;


    public SocketClient() throws IOException {
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        establishServerConnection();
    }

    private void establishServerConnection() {
        while (!connect())
            try {
                System.out.println(ADDRESS + ":" + PORT + " not responding, retrying in 3 seconds...");
                Thread.sleep(SERVER_WAITING_RESPONSE_TIME);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    private void initializeConnectionStream() throws IOException {
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    private JSONObject createMessage(String userName, String auth) {
        return JsonMessage.createLoginMessage(userName, auth);
    }

    private boolean connect() {
        try {
            socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            return true;
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
            return false;
        }
    }

    private void executeOrders() throws RemoteException{
        int choice;
        while (!executor.isShutdown()) {
            System.out.println("Choose what you wanna do :\n1. Disconnect from server\n2. Send message to server");
            try {
                choice = Integer.parseInt(inKeyboard.readLine());
            } catch (NumberFormatException | IOException exc) {
                continue;
            }
            switch (choice) {
                case 1:
                    disconnect();
                    System.exit(0);
                    break;
                case 2:
                    System.out.println("Write your message");
                    try {
                        outSocket.println(JsonMessage.createMessage(inKeyboard.readLine()).toJSONString());
                    } catch (IOException exc) {
                        continue;
                    }
                    break;
                default:
                    System.out.println("No actions available for " + choice);
            }
        }

    }

    @Override
    public void doActions() {
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());

        }
    }

    @Override
    public void disconnect() throws RemoteException {
        outSocket.println(JsonMessage.creatDisconnectMessage(username).toJSONString());
        heartbeatProtocolManager.kill();
        executor.shutdown();
        close();
    }

    private void login() {
        try {
            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                outVideo.println("Connected to " + ADDRESS + ":" + PORT + "\nThis is the first login firewall : \n");
                username = LoginGuiController.getUsername();
                JSONObject message = createMessage(username, LoginGuiController.getPassword());
                initializeConnectionStream();
                outSocket.println(message.toJSONString());
                String jsonResponse = inSocket.readLine();
                Map<String, String> dataMap = JsonMessage.parseJsonData(jsonResponse);
                if (dataMap.get("login").equals("successful")) {
                    lobbyPort = Integer.parseInt(dataMap.get("lobby_port"));
                    socket.close();
                    initializeLobbyLink(username);
                    loginSuccessful = true;
                    this.username = username;
                } else if (dataMap.get("login").equals("register")) {
                    outVideo.println("Registering...");
                    jsonResponse = inSocket.readLine();
                    dataMap = JsonMessage.parseJsonData(jsonResponse);
                    if (dataMap.get("login").equals("successful")) {
                        outVideo.println("Connecting to lobby");
                        lobbyPort = Integer.parseInt(dataMap.get("lobby_port"));
                        socket.close();
                        initializeLobbyLink(username);
                        outVideo.println("Login successful");
                        loginSuccessful = true;
                        this.username = username;
                    } else
                        outVideo.println("Username already taken");
                } else if (dataMap.get("login").equals("error")) {
                    outVideo.println("User already logged on");
                    socket.close();
                }
            }
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
            close();
        }
    }

    private void initializeLobbyLink(String identifier) throws IOException {
        socket = new Socket(ADDRESS, lobbyPort);
        initializeConnectionStream();
        outSocket.println(JsonMessage.createTokenMessage(identifier).toJSONString());
        System.out.println("Waiting lobby response");
        String jsonResponse = inSocket.readLine();
        Map<String, String> dataMap = JsonMessage.parseJsonData(jsonResponse);
        if (dataMap.get("login").equals("successful_lobby"))
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, Integer.parseInt(dataMap.get("heartbeat_port")), identifier);
        else
            outVideo.println("Second level auth");
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this);
        executeOrders();
    }

    private void fastRecovery() {
        DiscoverLan discoverLan = new DiscoverLan();
        try {
            while ((!discoverLan.isDirectlyAttachedAndReachable(Inet4Address.getByName(ADDRESS))
                    && DiscoverInternet.isPrivateIP(Inet4Address.getByName(ADDRESS))) ||
                    (DiscoverInternet.checkInternetConnection() && !DiscoverInternet.isPrivateIP(Inet4Address.getByName(ADDRESS))))
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            initializeLobbyLink(username);
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
    }

    public void run() {
        while (!executor.isShutdown()) {
            try {
                outVideo.println("Message from server : " + inSocket.readLine());
            } catch (IOException exc) {
                fastRecovery();
                executor.shutdown();
            }
        }
    }
}