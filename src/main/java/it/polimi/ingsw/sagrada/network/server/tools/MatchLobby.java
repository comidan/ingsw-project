package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientBase;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;
import it.polimi.ingsw.sagrada.network.security.Security;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.RemoteRMIClient;
import it.polimi.ingsw.sagrada.network.server.socket.RemoteSocketClient;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class MatchLobby.
 */
public class MatchLobby extends UnicastRemoteObject implements HeartbeatListener, Runnable, AbstractMatchLobbyRMI {

    /** The Constant MAX_POOL_SIZE. */
    private static final int MAX_POOL_SIZE = 4;

    /** The Constant TIME_WAIT_UNIT. */
    private static final int TIME_WAIT_UNIT = 10000;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MatchLobby.class.getName());

    /** The client pool. */
    private Map<String, ClientBase> clientPool;

    /** The client state*/
    private Map<String, Long> clientLinkState;

    private Map<String, Boolean> clientTCPLinkEstablished;

    /** The client ids. */
    private List<String> clientIds;

    /** The client id tokens. */
    private List<String> clientIdTokens;

    /** The executor. */
    private ExecutorService executor;

    /** The lobby server. */
    private ExecutorService lobbyServer;

    /** The check game start. */
    private ExecutorService checkGameStart;

    /** The start time. */
    private long startTime;

    /** The heartbeat protocol manager. */
    private HeartbeatProtocolManager heartbeatProtocolManager;

    /** The port discovery. */
    private PortDiscovery portDiscovery;

    /** The sign out. */
    private Function<String, Boolean> signOut;

    /** The server socket. */
    private ServerSocket serverSocket;

    /** The identifier. */
    private String identifier;

    /** The port. */
    private int port;

    /** The game manager. */
    private GameManager gameManager;

    /** The dynamic router. */
    private DynamicRouter dynamicRouter;

    /** The game data manager. */
    private GameDataManager gameDataManager;

    /** The in game. */
    private boolean inGame;

    /**
     * Instantiates a new match lobby.
     *
     * @param signOut the sign out
     * @param identifier the identifier
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MatchLobby(Function<String, Boolean> signOut, String identifier) throws IOException {
        clientPool = new ConcurrentHashMap<>();
        clientLinkState = new HashMap<>();
        clientTCPLinkEstablished = new HashMap<>();
        portDiscovery = new PortDiscovery();
        clientIds = new ArrayList<>();
        clientIdTokens = new ArrayList<>();
        this.signOut = signOut;
        inGame = false;
        port = portDiscovery.obtainAvailableTCPPort();
        serverSocket = new ServerSocket(port);
        heartbeatProtocolManager = new HeartbeatProtocolManager(this);
        executor = Executors.newCachedThreadPool();
        lobbyServer = Executors.newSingleThreadExecutor();
        lobbyServer.submit(this);
        checkGameStart = Executors.newSingleThreadExecutor();
        checkGameStart.submit(new CheckStartGameCondition());
        startTime = System.currentTimeMillis();
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind("Lobby:"+identifier , this);
            this.identifier = "Lobby:" + identifier;
        }
        catch (RemoteException | AlreadyBoundException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /**
     * Checks if is full.
     *
     * @return true, if is full
     */
    public boolean isFull() {
        return clientPool.size() == MAX_POOL_SIZE;
    }

    boolean wasHere(String username) {
        return clientIdTokens.contains(username);
    }

    /**
     * Checks if is in game.
     *
     * @return true, if is in game
     */
    boolean isInGame() {
        return inGame;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI#addClient(java.lang.String)
     */
    @Override
    public void addClient(String clientID) {
        if(!clientIdTokens.contains(clientID))
            clientIdTokens.add(clientID);
    }

    /**
     * Close lobby.
     */
    void closeLobby() {
        heartbeatProtocolManager.kill();
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the lobby identifier.
     *
     * @return the lobby identifier
     */
    public String getLobbyIdentifier() {
        return identifier;
    }

    /**
     * Removes the player.
     *
     * @param username the username
     * @return true, if successful
     */
    private boolean removePlayer(String username) {
        synchronized (signOut) {
            signOut.apply(username);
        }
        if(inGame)
            gameManager.removePlayer(username);
        try {
            if (clientPool.get(username).isInFastRecovery()) {
                clientPool.remove(username);
                clientIds.remove(username);
                heartbeatProtocolManager.removeFromMonitoredHost(username);
            }
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
        //heartbeatProtocolManager.removeFromMonitoredHost(username); use ONLY on end game
        System.out.println(username + " disconnected");
        clientIds.stream()
                 .filter(id -> !id.equals(username))
                 .forEach(clientId -> {
                    try {
                        clientPool.get(clientId).removePlayer(username);
                    } catch (RemoteException exc) {
                        LOGGER.log(Level.SEVERE, exc::getMessage);
                    }
                });
        return true;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener#onHeartbeat(it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent)
     */
    @Override
    public void onHeartbeat(HeartbeatEvent event) {
        clientLinkState.put(event.getSource(), event.getBeatTimeStamp());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener#onDeath(it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent)
     */
    @Override
    public void onDeath(HeartbeatEvent event) {
        System.out.println(event.getSource() + " is offline");
        removePlayer(event.getSource());
        clientIds.stream()
                 .filter(id -> !id.equals(event.getSource()))
                 .forEach(clientId -> {
                    try {
                        clientPool.get(clientId).sendMessage(event.getSource() + " is offline");
                    } catch (RemoteException exc) {
                        LOGGER.log(Level.SEVERE, exc::getMessage);
                    }
                });
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener#onLossCommunication(it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent)
     */
    @Override
    public void onLossCommunication(HeartbeatEvent event) {
        synchronized (clientTCPLinkEstablished) {
            clientTCPLinkEstablished.put(event.getSource(), false);
        }
        System.out.println(event.getSource() + " maybe offline");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener#onReacquiredCommunication(it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent)
     */
    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {
        clientLinkState.put(event.getSource(), event.getBeatTimeStamp());
        try {
            clientPool.get(event.getSource()).sendMessage(Security.getEncryptedData(CommandKeyword.MESSAGE));
            clientTCPLinkEstablished.put(event.getSource(), true);
            if(inGame)
                gameManager.updateClientState(event.getSource());
        }
        catch (IOException exc) {
            LOGGER.log(Level.INFO, () -> "TCP not ready yet");
        }
        System.out.println(event.getSource() + " came back");

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener#onAcquiredCommunication(it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent)
     */
    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        clientLinkState.put(event.getSource(), event.getBeatTimeStamp());
    }

    /**
     * Fast recovery client connection.
     *
     * @param identifier the identifier
     * @return true, if successful
     */
    private boolean fastRecoveryClientConnection(String identifier) {
        clientIdTokens.add(identifier);
        return true;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI#joinLobby(java.lang.String, it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI)
     */
    @Override
    public boolean joinLobby(String token, ClientRMI clientRMI) throws RemoteException {
        if(!clientIdTokens.contains(token))
            return false;
        if(!clientIds.contains(token))
            clientIds.add(token);
        clientPool.put(token, clientRMI);
        Function<String, Boolean> disconnect = this::removePlayer;
        Consumer<Message> sendToModel = this::sendToModel;
        Client remoteClient = new RemoteRMIClient(token, disconnect, clientRMI, sendToModel);
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind(token, remoteClient);
            clientRMI.notifyRemoteClientInterface(token);
            int heartbeatPort = portDiscovery.obtainAvailableUDPPort();
            clientRMI.notifyHeartbeatPort(heartbeatPort);
            heartbeatProtocolManager.addHost(token, heartbeatPort);
        }
        catch (RemoteException|AlreadyBoundException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
        for(String username : clientIds)
            for(String receiver : clientIds)
                clientPool.get(receiver).setPlayer(username, clientIds.indexOf(username));
        synchronized (clientTCPLinkEstablished) {
            clientTCPLinkEstablished.put(token, true);
        }
        if(inGame)
            gameManager.updateClientState(token);
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while(!lobbyServer.isShutdown()) {
            try {
                Socket client = serverSocket.accept();
                int tokenIndex = DataManager.tokenAuthentication(clientIdTokens, client);
                if(tokenIndex != -1) {
                    String id = clientIdTokens.get(tokenIndex);
                    Function<String, Boolean> disconnect = this::removePlayer;
                    Function<String, Boolean> fastRecovery = this::fastRecoveryClientConnection;
                    Consumer<Message> sendToModel = this::sendToModel;
                    RemoteSocketClient socketClient = new RemoteSocketClient(client, id, disconnect, fastRecovery, sendToModel);
                    if(!clientIds.contains(id)) {
                        clientIds.add(id);
                        int heartbeatPort = portDiscovery.obtainAvailableUDPPort();
                        if(heartbeatProtocolManager.addHost(id, heartbeatPort))
                            DataManager.sendLoginLobbyResponse(client, heartbeatPort);
                    }
                    clientPool.put(id, socketClient);
                    executor.submit(socketClient);
                    for(String username : clientIds)
                        for(String receiver : clientIds)
                            clientPool.get(receiver).setPlayer(username, clientIds.indexOf(username));
                    synchronized (clientTCPLinkEstablished) {
                        clientTCPLinkEstablished.put(id, true);
                    }
                    if(inGame)
                        gameManager.updateClientState(id);
                }
                else
                    DataManager.sendLoginError(client);
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
        }
    }

    private boolean checkValidClientLinkState() {
        for(String id : clientIdTokens)
            synchronized (clientTCPLinkEstablished) {
                if (new Date().getTime() - clientLinkState.get(id) > 2000 || !clientTCPLinkEstablished.get(id)) {
                    //clientTCPLinkEstablished.put(id, false);
                    return false;
                }
            }
        return true;
    }

    /**
     * Start game.
     */
    private void startGame() {
        while(!checkValidClientLinkState()) {
            try {
                Thread.sleep(500);
                System.out.println("Waiting clients stable link...");
            }
            catch (InterruptedException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
                Thread.currentThread().interrupt();
            }
        }
        if(clientIds.size() <= 1) {
            sendPayload("Lobby terminated : not enough players");
            closeLobby();
            return;
        }
        inGame = true;
        List<Player> players = new ArrayList<>();
        clientIds.forEach(username -> players.add(new Player(username)));
        dynamicRouter = new MessageDispatcher();
        gameDataManager = new GameDataManager(dynamicRouter, clientPool);
        gameManager = new GameManager(players, dynamicRouter, gameDataManager::fastRecoveryDispatch);
        gameManager.startGame();
    }

    /**
     * Send to model.
     *
     * @param message the message
     */
    private void sendToModel(Message message) {
        dynamicRouter.dispatch(message);
    }

    private void sendPayload(String payload) {
        clientIds.forEach(username -> new Thread(() -> {
            try {
                clientPool.get(username).setTimer(payload);
                clientTCPLinkEstablished.put(username, true);
            } catch (RemoteException exc) {
                clientTCPLinkEstablished.put(username, false);
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
            catch (NullPointerException exc) {
                clientTCPLinkEstablished.put(username, false);
            }
        }).start());
    }

    /**
     * The Class CheckStartGameCondition.
     */
    private class CheckStartGameCondition implements Runnable{

        /** The elapsed time. */
        int elapsedTime = 0;

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            int timeToWait = TIME_WAIT_UNIT * (MAX_POOL_SIZE - clientIds.size() + 1);
            while (elapsedTime < timeToWait / 1000 && clientIds.size() != MAX_POOL_SIZE) {
                timeToWait = TIME_WAIT_UNIT * (MAX_POOL_SIZE - clientIds.size() + 1);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException exc) {
                    LOGGER.log(Level.SEVERE, exc::getMessage);
                    Thread.currentThread().interrupt();
                }
                if(clientIds.size() > 1)
                    elapsedTime += 1;
                else
                    elapsedTime = 0;
                sendPayload(timeToWait / 1000 - elapsedTime + "");
            }
            if(clientPool.size()>1) {
                sendPayload("Starting game...\nWaiting players...");
                startGame();
            }
            else
                checkGameStart.submit(new CheckStartGameCondition());
        }
    }
}