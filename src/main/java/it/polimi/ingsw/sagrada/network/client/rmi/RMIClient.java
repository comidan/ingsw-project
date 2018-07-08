package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.gui.login.LoginGuiAdapter;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandManager;
import it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan.DiscoverLan;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


/**
 * The Class RMIClient.
 */
public class RMIClient extends UnicastRemoteObject implements ClientRMI, Channel<Message, LoginState>, Runnable {

    /**
     * The Constant SERVER_WAITING_RESPONSE_TIME.
     */
    private static final int SERVER_WAITING_RESPONSE_TIME = 3000;

    /**
     * The Constant NETWORK_CONFIG_PATH.
     */
    private static final String NETWORK_CONFIG_PATH = "/json/config/network_config.json";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(RMIClient.class.getName());

    /**
     * The Constant ADDRESS.
     */
    private static final String ADDRESS = getConfigAddress();

    /**
     * The Constant PROTOCOL.
     */
    private static final String PROTOCOL = "rmi://";

    /**
     * THe Constant RMI_PORT
     */
    private static final int RMI_PORT = getConfigRMIPort();

    /**
     * THe Constant DEFAULT_RMI_PORT
     */
    private static final int DEFAULT_RMI_PORT = 1099;

    /**
     * The lobby.
     */
    private AbstractMatchLobbyRMI lobby;

    /**
     * The username.
     */
    private String username;

    /**
     * The heartbeat protocol manager.
     */
    private HeartbeatProtocolManager heartbeatProtocolManager;

    /**
     * The remote client.
     */
    private Client remoteClient;

    /**
     * The server.
     */
    private AbstractServerRMI server;

    private final String URL = PROTOCOL + ADDRESS + ":" + 1099;

    private boolean active;

    private String lobbyId;

    private ExecutorService executor;

    /**
     * Instantiates a new RMI client.
     *
     * @throws RemoteException the remote exception
     */
    public RMIClient() throws RemoteException {
        System.setProperty("java.rmi.server.codebase", "http://" + ADDRESS + ":8080/");
        executor = Executors.newSingleThreadExecutor();
        establishServerConnection();
    }

    /**
     * Connect.
     *
     * @return true, if successful
     */
    private boolean connect() {
        try {
            server = (AbstractServerRMI) Naming.lookup(URL + "/ServerRMI");
            return true;
        } catch (RemoteException | NotBoundException | MalformedURLException exc) {
            return false;
        }
    }

    /**
     * Establish server connection.
     */
    private void establishServerConnection() {
        while (!connect())
            try {
                System.out.println("RMI server at " + ADDRESS + " not responding, retrying in 3 seconds...");
                Thread.sleep(SERVER_WAITING_RESPONSE_TIME);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    /**
     * Login.
     */
    private void login() {
        LoginState loginState = null;

        while (loginState != LoginState.AUTH_OK) {
            try {
                username = LoginGuiAdapter.getUsername();
                System.out.println("Logging in");
                loginState = server.login(this, username, LoginGuiAdapter.getPassword());
                System.out.println(loginState);
                if (loginState == LoginState.AUTH_OK) {
                    sendMessage(loginState); //sendMessage(LoginState.AUTH_OK);
                    try {
                        System.out.println("Acquiring lobby");
                        lobbyId = server.getMatchLobbyId();
                        lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
                        System.out.println("Lobby acquired");
                        if (lobby.joinLobby(username, this)) {
                            CommandManager.setClientData(username, this);
                            System.out.println("Lobby joined");
                        } else
                            System.out.println("Error");
                    } catch (NotBoundException | MalformedURLException exc) {
                        LOGGER.log(Level.SEVERE, exc::getMessage);
                    }
                } else
                    sendMessage(loginState);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
    }

    /**
     * Gets the config address.
     *
     * @return the config address
     */
    private static String getConfigAddress() {  //add control on existing external set resource or check on internal value : DO THE SAME FOR OTHER POSSIBLY EXTERNAL RESOURCE METHOD
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(new FileInputStream(new File("resource" + NETWORK_CONFIG_PATH))));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_address");
        } catch (Exception exc) {
            try {
                Object obj = parser.parse(new InputStreamReader(RMIClient.class.getResourceAsStream(NETWORK_CONFIG_PATH)));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get("ip_address");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> "network config fatal error");
                return "";
            }
        }
    }

    /**
     * Gets the config address.
     *
     * @return the config address
     */
    private static int getConfigRMIPort() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(new FileInputStream(new File("resource" + NETWORK_CONFIG_PATH))));
            JSONObject jsonObject = (JSONObject) obj;
            return ((Long) jsonObject.get("rmi_port")).intValue();
        } catch (Exception exc) {
            try {
                Object obj = parser.parse(new InputStreamReader(RMIClient.class.getResourceAsStream(NETWORK_CONFIG_PATH)));
                JSONObject jsonObject = (JSONObject) obj;
                return ((Long) jsonObject.get("rmi_port")).intValue();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> "network config fatal error");
                return DEFAULT_RMI_PORT;
            }
        }
    }

    private void fastRecovery() {
        try {
            while (!new DiscoverLan().isHostReachable(InetAddress.getByName(ADDRESS)))
                try {
                    System.out.println("Waiting for available connection...");
                    sleep(1000);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
        }
        catch (UnknownHostException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
        System.out.println("Restoring connection...");
        try {
            System.out.println(PROTOCOL + ADDRESS + "/" + lobbyId);
            lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
            System.out.println("Lobby acquired");
            if (lobby.joinLobby(username, this)) {
                System.out.println("Lobby joined");
            } else
                System.out.println("Error");
        } catch (NotBoundException | MalformedURLException | RemoteException exc) {
            exc.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#notifyLobby(java.lang.String)
     */
    @Override
    public void notifyLobby(String lobbyId) throws RemoteException {
        try {
            System.out.println("Acquiring lobby");
            lobby = (AbstractMatchLobbyRMI) Naming.lookup(URL + "/" + lobbyId);
            System.out.println("Lobby acquired");
            if (lobby.joinLobby(username, this)) {
                System.out.println("Lobby joined");
            } else
                System.out.println("Error");
        } catch (NotBoundException | MalformedURLException exc) {
            System.out.println("RMI Error");
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#signUp()
     */
    @Override
    public void signUp() {
        System.out.println("Signing up");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#notifyRemoteClientInterface(java.lang.String)
     */
    @Override
    public void notifyRemoteClientInterface(String id) {
        try {
            executor = Executors.newSingleThreadExecutor();
            executor.submit(this);
            remoteClient = (Client) Naming.lookup(URL + "/" + id);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#notifyHeartbeatPort(java.lang.Integer)
     */
    @Override
    public void notifyHeartbeatPort(Integer port) {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, username);
            System.out.println("Heartbeat started");
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendRemoteMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendRemoteMessage(Message message) throws RemoteException {
        remoteClient.sendRemoteMessage(message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#close()
     */
    @Override
    public void close() {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#disconnect()
     */
    @Override
    public void disconnect() {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#setTimer(java.lang.String)
     */
    @Override
    public void setTimer(String time) {
        CommandManager.setTimer(time);

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#startHeartbeat(int)
     */
    @Override
    public void startHeartbeat(int port) {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#setPlayer(java.lang.String)
     */
    @Override
    public void setPlayer(String playerName, int position) {
        CommandManager.setPlayer(playerName, position);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#removePlayer(java.lang.String)
     */
    @Override
    public void removePlayer(String playerName) {
        CommandManager.removePlayer(playerName);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#getId()
     */
    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isInFastRecovery() throws RemoteException {
        return false;
    }

    @Override
    public void setActive(boolean active) throws RemoteException {
        this.active = active;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendResponse(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendResponse(Message message) {
        CommandManager.executePayload(message);
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

    @Override
    public void run() {
        while(true) {
            try {
                if(!new DiscoverLan().isHostReachable(InetAddress.getByName(ADDRESS))) {
                    System.out.println("Fast recovery");
                    fastRecovery();
                    return;
                }
                System.out.println("Checking RMI Server");
                Thread.sleep(2000);
            } catch (UnknownHostException | InterruptedException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
        }
    }
}
