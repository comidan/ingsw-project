package it.polimi.ingsw.sagrada.network.client.socket;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.gui.*;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessage;
import it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan.DiscoverLan;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet.DiscoverInternet;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class SocketClient implements Runnable, Client, Channel<Message, LoginState> {

    private static final Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
    private static final int PORT = 49152; //change to dynamic in some elegant way
    private static final String ADDRESS = getConfigAddress(); //just for now, next will be obtained in far smarter way
    private static final int SERVER_WAITING_RESPONSE_TIME = 3000;
    private static final String NETWORK_CONFIG_PATH = "src/main/resources/json/config/network_config.json";

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private PrintWriter outVideo;
    private ExecutorService executor;
    private String username;
    private int lobbyPort;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private DynamicRouter dynamicRouter;


    public SocketClient() throws IOException {
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        dynamicRouter = LoginGuiManager.getDynamicRouter();
        establishServerConnection();
    }

    private void establishServerConnection() {
        while (!connect())
            try {
                System.out.println(ADDRESS + ":" + PORT + " not responding, retrying in 3 seconds...");
                sleep(SERVER_WAITING_RESPONSE_TIME);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    private void initializeConnectionStream() throws IOException {
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(socket.getOutputStream(), true);
    }

    public void startHeartbeat(int port) {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, username);
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    private JSONObject createMessage(String userName, String auth) {
        return JsonMessage.createLoginMessage(userName, auth);
    }

    private boolean connect() {
        try {
            socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
            return false;
        }
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void sendRemoteMessage(Message message) throws RemoteException {
        outSocket.println(CommandManager.createPayload(message));
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
        outSocket.println(JsonMessage.createDisconnectMessage(username).toJSONString());
        heartbeatProtocolManager.kill();
        executor.shutdown();
        close();
    }

    @Override
    public String getId() throws RemoteException {
        return username;
    }

    @Override
    public void sendResponse(Message message) throws RemoteException {
        //outSocket.println(commandManager.createPayload(message));
    }

    private void login() {
        try {
            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                outVideo.println("Connected to " + ADDRESS + ":" + PORT + "\nThis is the first login firewall : \n");
                username = LoginGuiManager.getUsername();
                JSONObject message = createMessage(username, LoginGuiManager.getPassword());
                initializeConnectionStream();
                outSocket.println(message.toJSONString());
                String jsonResponse = inSocket.readLine();
                Message response = JsonMessage.parseJsonData(jsonResponse);
                if (response instanceof LobbyLoginEvent) {
                    lobbyPort = ((LobbyLoginEvent)response).getLobbyPort();
                    socket.close();
                    sendMessage(LoginState.AUTH_OK);
                    loginSuccessful = true;
                    CommandManager.setClientData(username, this);
                    initializeLobbyLink(username);
                }
                else if (response instanceof RegisterEvent) {
                    jsonResponse = inSocket.readLine();
                    response = JsonMessage.parseJsonData(jsonResponse);
                    if (response instanceof LobbyLoginEvent) {
                        outVideo.println("Registering...");
                        outVideo.println("Connecting to lobby");
                        lobbyPort = ((LobbyLoginEvent)response).getLobbyPort();
                        socket.close();
                        sendMessage(LoginState.AUTH_OK);
                        outVideo.println("Login successful");
                        loginSuccessful = true;
                        CommandManager.setClientData(username, this);
                        initializeLobbyLink(username);
                    }
                    else {
                        outVideo.println("Wrong password");
                        sendMessage(LoginState.AUTH_WRONG_PASSWORD);
                    }
                } else if (response instanceof ErrorEvent) {
                    outVideo.println("User already logged on");
                    sendMessage(LoginState.AUTH_FAILED_USER_ALREADY_LOGGED);
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
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this);
    }

    private void fastRecovery() {
        DiscoverLan discoverLan = new DiscoverLan();
        try {
            //DiscoverLan.isDirectlyAttacchedAndReachable to be fixed to check non-directly connected host
            while ((DiscoverInternet.isPrivateIP(Inet4Address.getByName(ADDRESS)) && !discoverLan.isHostReachable(Inet4Address.getByName(ADDRESS)))
                    || (!DiscoverInternet.isPrivateIP(Inet4Address.getByName(ADDRESS)) && !DiscoverInternet.checkInternetConnection()))
                try {
                    System.out.println("Waiting for available connection...");
                    sleep(1000);
                }
                catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Restoring connection...");
                initializeLobbyLink(username);
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
    }

    private static String getConfigAddress() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(NETWORK_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_address");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> "network config fatal error");
            return "";
        }
    }

    public void run() {
        while (!executor.isShutdown()) {
            try {
                String json = inSocket.readLine();
                CommandManager.executePayload(json);
            } catch (IOException exc) {
                fastRecovery();
                executor.shutdown();
            }
        }
    }

    @Override
    public void dispatch(Message message) {

    }

    @Override
    public void sendMessage(LoginState message) {
        LoginGuiManager.getDynamicRouter().dispatch(message);
    }
}