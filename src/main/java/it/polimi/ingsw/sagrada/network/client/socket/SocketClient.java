package it.polimi.ingsw.sagrada.network.client.socket;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RegisterEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.ErrorEvent;
import it.polimi.ingsw.sagrada.gui.login.LoginGuiAdapter;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandExecutor;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessageBidirectionalConverter;
import it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan.DiscoverLan;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.security.Security;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;



/**
 * The Class SocketClient.
 */
public class SocketClient implements Runnable, Client, Channel<Message, LoginState> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SocketClient.class.getName());

    /** The Constant PORT. */
    private static final int PORT = getConfigPort();

    /**  The Constant DEFAULT_PORT. */
    private static final int DEFAULT_PORT = 49152;

    /** The Constant ADDRESS. */
    private static final String ADDRESS = getConfigAddress();

    /** The Constant SERVER_WAITING_RESPONSE_TIME. */
    private static final int SERVER_WAITING_RESPONSE_TIME = 3000;

    /** The Constant NETWORK_CONFIG_PATH. */
    private static final String NETWORK_CONFIG_PATH = "/json/config/network_config.json";

    /** The socket. */
    private Socket socket;

    /** The in socket. */
    private BufferedReader inSocket;

    /** The out socket. */
    private PrintWriter outSocket;

    /** The executor. */
    private ExecutorService executor;

    /** The username. */
    private String username;

    /** The lobby port. */
    private int lobbyPort;

    /** The heartbeat protocol manager. */
    private HeartbeatProtocolManager heartbeatProtocolManager;

    /** The dynamic router. */
    private DynamicRouter dynamicRouter;

    /** The is in fast recovery. */
    private boolean isInFastRecovery;

    /** The active. */
    private boolean active;

    /** The recoverying. */
    private Semaphore recoverying;


    /**
     * Instantiates a new socket client.
     */
    public SocketClient() {
        isInFastRecovery = false;
        active = true;
        dynamicRouter = LoginGuiAdapter.getDynamicRouter();
        recoverying = new Semaphore(1);
        establishServerConnection();
    }

    /**
     * Establish server connection.
     */
    private void establishServerConnection() {
        while (!connect())
            try {
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->ADDRESS + ":" + PORT + " not responding, retrying in 3 seconds...");
                sleep(SERVER_WAITING_RESPONSE_TIME);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    /**
     * Initialize connection stream.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initializeConnectionStream() throws IOException {
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(socket.getOutputStream(), true);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#startHeartbeat(int)
     */
    public void startHeartbeat(int port) {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, username);
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Heartbeat port : " + port);
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /**
     * Creates the message.
     *
     * @param userName the user name
     * @param auth the auth
     * @return the JSON object
     */
    private JSONObject createMessage(String userName, String auth) {
        return JsonMessageBidirectionalConverter.createLoginMessage(userName, auth);
    }

    /**
     * Connect.
     *
     * @return true, if successful
     */
    private boolean connect() {
        try {
            socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException exc) {
            LOGGER.log(Level.INFO, exc.getMessage());
            return false;
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendRemoteMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendRemoteMessage(Message message) {
        outSocket.println(Security.getEncryptedData(CommandExecutor.createPayload(message)));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#close()
     */
    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());

        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#disconnect()
     */
    @Override
    public void disconnect() {
        outSocket.println(Security.getEncryptedData(JsonMessageBidirectionalConverter.createDisconnectMessage(username).toJSONString()));
        heartbeatProtocolManager.kill();
        executor.shutdown();
        close();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#getId()
     */
    @Override
    public String getId() {
        return username;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#isInFastRecovery()
     */
    @Override
    public boolean isInFastRecovery() throws RemoteException {
        return isInFastRecovery;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#setActive(boolean)
     */
    @Override
    public void setActive(boolean active) throws RemoteException {
        this.active = active;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendResponse(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendResponse(Message message) {
        
    }

    /**
     * Login.
     */
    private void login() {
        try {
            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "Connected to " + ADDRESS + ":" + PORT + "\nThis is the first login firewall : \n");
                username = LoginGuiAdapter.getUsername();
                JSONObject message = createMessage(username, LoginGuiAdapter.getPassword());
                initializeConnectionStream();
                outSocket.println(Security.getEncryptedData(message.toJSONString()));
                String jsonResponse = Security.getDecryptedData(inSocket.readLine());
                Message response = JsonMessageBidirectionalConverter.parseJsonData(jsonResponse);
                if (response instanceof LobbyLoginEvent) {
                    lobbyPort = ((LobbyLoginEvent)response).getLobbyPort();
                    socket.close();
                    sendMessage(LoginState.AUTH_OK);
                    loginSuccessful = true;
                    CommandExecutor.setClientData(username, this);
                    initializeLobbyLink(username);
                }
                else if (response instanceof RegisterEvent) {
                    jsonResponse = Security.getDecryptedData(inSocket.readLine());
                    response = JsonMessageBidirectionalConverter.parseJsonData(jsonResponse);
                    if (response instanceof LobbyLoginEvent) {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "Registering...");
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "Connecting to lobby");
                        lobbyPort = ((LobbyLoginEvent)response).getLobbyPort();
                        socket.close();
                        sendMessage(LoginState.AUTH_OK);
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "Login successful");
                        loginSuccessful = true;
                        CommandExecutor.setClientData(username, this);
                        initializeLobbyLink(username);
                    }
                    else {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "Wrong password");
                        sendMessage(LoginState.AUTH_WRONG_PASSWORD);
                    }
                } else if (response instanceof ErrorEvent) {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () -> "User already logged on");
                    sendMessage(LoginState.AUTH_FAILED_USER_ALREADY_LOGGED);
                }
            }
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
            close();
        }
    }

    /**
     * Initialize lobby link.
     *
     * @param identifier the identifier
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initializeLobbyLink(String identifier) throws IOException {
        socket = new Socket(ADDRESS, lobbyPort);
        initializeConnectionStream();
        outSocket.println(Security.getEncryptedData(JsonMessageBidirectionalConverter.createTokenMessage(identifier).toJSONString()));
        isInFastRecovery = false;
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Waiting lobby response");
        executor = Executors.newCachedThreadPool();
        executor.submit(this);
        executor.submit(() -> {
            while(true) {
                try {
                    if (active && !new DiscoverLan().isHostReachable(InetAddress.getByName(ADDRESS))) {
                        if(recoverying.tryAcquire()) {
                            close();
                            isInFastRecovery = true;
                            active = false;
                            heartbeatProtocolManager.kill();
                            executor.shutdown();
                            fastRecovery();
                            recoverying.release();
                        }
                    }
                    Thread.sleep(1000);
                } catch (UnknownHostException exc) {
                    LOGGER.log(Level.SEVERE, exc::getMessage);
                }
            }
        });
    }

    /**
     * Fast recovery.
     */
    private void fastRecovery() {
        DiscoverLan discoverLan = new DiscoverLan();
        try {
            while (!discoverLan.isHostReachable(Inet4Address.getByName(ADDRESS)))
                try {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Waiting for available connection...");
                    sleep(1000);
                }
                catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Restoring connection...");
            initializeLobbyLink(username);
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
    }

    /**
     * Gets the config address.
     *
     * @return the config address
     */
    private static String getConfigAddress() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(new FileInputStream(new File("resource" + NETWORK_CONFIG_PATH))));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_address");
        }
        catch (Exception exc) {
            try {
                Object obj = parser.parse(new InputStreamReader(SocketClient.class.getResourceAsStream(NETWORK_CONFIG_PATH)));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get("ip_address");
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> "network config fatal error");
                return "";
            }
        }
    }

    /**
     * Gets the config port.
     *
     * @return the config port
     */
    private static int getConfigPort() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(new FileInputStream(new File("resource" + NETWORK_CONFIG_PATH))));
            JSONObject jsonObject = (JSONObject) obj;
            return ((Long) jsonObject.get("port")).intValue();
        }
        catch (Exception exc) {
            try {
                Object obj = parser.parse(new InputStreamReader(SocketClient.class.getResourceAsStream(NETWORK_CONFIG_PATH)));
                JSONObject jsonObject = (JSONObject) obj;
                return ((Long) jsonObject.get("port")).intValue();
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> "network config fatal error");
                return DEFAULT_PORT;
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (!executor.isShutdown()) {
            try {
                String json = Security.getDecryptedData(inSocket.readLine());
                CommandExecutor.executePayload(json);
            } catch (IOException exc) {
                if (recoverying.tryAcquire()) {
                    close();
                    isInFastRecovery = true;
                    active = false;
                    LOGGER.log(Level.SEVERE, exc::getMessage);
                    executor.shutdown();
                    heartbeatProtocolManager.kill();
                    fastRecovery();
                    recoverying.release();
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(Message message) {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(LoginState message) {
        LoginGuiAdapter.getDynamicRouter().dispatch(message);
    }
}