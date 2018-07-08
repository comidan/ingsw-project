package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.gui.login.LoginGuiAdapter;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandExecutor;
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

    /** THe Constant RMI_PORT. */
    private static final int RMI_PORT = getConfigRMIPort();

    /** THe Constant DEFAULT_RMI_PORT. */
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

    /** The url. */
    private final String URL = PROTOCOL + ADDRESS + ":" + 1099;

    /** The active. */
    private boolean active;

    /** The lobby id. */
    private String lobbyId;

    /** The executor. */
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
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"RMI server at " + ADDRESS + " not responding, retrying in 3 seconds...");
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
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Logging in");
                loginState = server.login(this, username, LoginGuiAdapter.getPassword());
                final LoginState state = loginState;
                Logger.getLogger(getClass().getName()).log(Level.INFO, () -> state+"");
                if (loginState == LoginState.AUTH_OK) {
                    sendMessage(loginState);
                    try {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Acquiring lobby");
                        lobbyId = server.getMatchLobbyId();
                        lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby acquired");
                        if (lobby.joinLobby(username, this)) {
                            CommandExecutor.setClientData(username, this);
                            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby joined");
                        } else
                            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Error");
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

    /**
     * Fast recovery.
     */
    private void fastRecovery() {
        try {
            while (!new DiscoverLan().isHostReachable(InetAddress.getByName(ADDRESS)))
                try {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Waiting for available connection...");
                    sleep(1000);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
        }
        catch (UnknownHostException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Restoring connection...");
        try {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->PROTOCOL + ADDRESS + "/" + lobbyId);
            lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby acquired");
            if (lobby.joinLobby(username, this)) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby joined");
            } else
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Error");
        } catch (NotBoundException | MalformedURLException | RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#notifyLobby(java.lang.String)
     */
    @Override
    public void notifyLobby(String lobbyId) throws RemoteException {
        try {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Acquiring lobby");
            lobby = (AbstractMatchLobbyRMI) Naming.lookup(URL + "/" + lobbyId);
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby acquired");
            if (lobby.joinLobby(username, this)) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Lobby joined");
            } else
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Error");
        } catch (NotBoundException | MalformedURLException exc) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"RMI Error");
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI#signUp()
     */
    @Override
    public void signUp() {
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Signing up");
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
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Heartbeat started");
        } catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
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
        CommandExecutor.setTimer(time);

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
        CommandExecutor.setPlayer(playerName, position);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#removePlayer(java.lang.String)
     */
    @Override
    public void removePlayer(String playerName) {
        CommandExecutor.removePlayer(playerName);
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
        return false;
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
        CommandExecutor.executePayload(message);
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

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while(true) {
            try {
                if(!new DiscoverLan().isHostReachable(InetAddress.getByName(ADDRESS))) {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Fast recovery");
                    fastRecovery();
                    return;
                }
                Thread.sleep(2000);
            } catch (UnknownHostException | InterruptedException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
        }
    }
}
