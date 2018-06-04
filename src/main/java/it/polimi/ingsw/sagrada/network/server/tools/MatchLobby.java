package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientBase;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchLobby extends UnicastRemoteObject implements HeartbeatListener, Runnable, AbstractMatchLobbyRMI {

    private static final int MAX_POOL_SIZE = 4;
    private static final long TIME_WAIT_UNIT = 10000;
    private static final Logger LOGGER = Logger.getLogger(MatchLobby.class.getName());

    private Map<String, ClientBase> clientPool;
    private List<String> clientIds;
    private List<String> clientIdTokens;
    private ExecutorService executor;
    private ExecutorService lobbyServer;
    private ExecutorService checkGameStart;
    private long startTime;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private PortDiscovery portDiscovery;
    private Function<String, Boolean> signOut;
    private ServerSocket serverSocket;
    private String identifier;
    private int port;
    private GameManager gameManager;
    private DynamicRouter dynamicRouter;
    private GameDataManager gameDataManager;
    private boolean inGame;

    public MatchLobby(Function<String, Boolean> signOut, String identifier) throws IOException {
        clientPool = new HashMap<>();
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
        catch (RemoteException|AlreadyBoundException exc ) {

        }
    }

    public boolean isFull() {
        return clientPool.size() == MAX_POOL_SIZE;
    }

    public boolean isInGame() {
        return inGame;
    }

    @Override
    public void addClient(String clientID) {
        clientIdTokens.add(clientID);
    }

    public void closeLobby() {
        heartbeatProtocolManager.kill();
    }

    public int getPort() {
        return port;
    }

    public String getLobbyIdentifier() {
        return identifier;
    }

    public boolean removePlayer(String username) {
        synchronized (signOut) {
            signOut.apply(username);
        }
        clientPool.remove(username);
        clientIds.remove(username);
        clientIdTokens.remove(username);
        heartbeatProtocolManager.removeFromMonitoredHost(username);
        System.out.println(username + " disconnected");
        for(String clientId : clientIds)
            try {
                clientPool.get(clientId).sendMessage(username + " disconnected");
                clientPool.get(clientId).removePlayer(username);
            }
            catch (RemoteException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
        return true;
    }

    @Override
    public void onHeartbeat(HeartbeatEvent event) {

    }

    @Override
    public void onDeath(HeartbeatEvent event) {
        System.out.println(event.getSource() + " is offline");
        removePlayer(event.getSource());
        for(String clientId : clientIds)
            try {
                clientPool.get(clientId).sendMessage(event.getSource() + " is offline");
            }
            catch (RemoteException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
    }

    @Override
    public void onLossCommunication(HeartbeatEvent event) {
        System.out.println(event.getSource() + " maybe offline");
    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {
        System.out.println(event.getSource() + " came back");
    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        for(String clientId : clientIds) {
            if (!clientId.equals(event.getSource()))
                try {
                    clientPool.get(clientId).sendMessage(event.getSource() + " is online");
                    clientPool.get(clientId).setPlayer(event.getSource());
                }
                catch (RemoteException exc) {
                    LOGGER.log(Level.SEVERE, exc::getMessage);
                }
        }
    }

    private boolean fastRecoveryClientConnection(String identifier) {
        clientIdTokens.add(identifier);
        return true;
    }

    @Override
    public boolean joinLobby(String token, ClientRMI clientRMI) throws RemoteException {
        if(!clientIdTokens.contains(token))
            return false;
        if(!clientIds.contains(token))
            clientIds.add(token);
        clientPool.put(token, clientRMI);
        Function<String, Boolean> disconnect = this::removePlayer;
        Client remoteClient = new RemoteRMIClient(token, disconnect, clientRMI);
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
        for(String username : clientIds) {
            System.out.println("Remote user set");
            clientPool.get(token).setPlayer(username);
        }
        return true;
    }

    @Override
    public void run() {
        while(!lobbyServer.isShutdown()) {
            try {
                Socket client = serverSocket.accept();
                int tokenIndex = DataManager.tokenAuthentication(clientIdTokens, client);
                if(tokenIndex != -1) {
                    String id = clientIdTokens.remove(tokenIndex);
                    Function<String, Boolean> disconnect = this::removePlayer;
                    Function<String, Boolean> fastRecovery = this::fastRecoveryClientConnection;
                    Consumer<Message> sendToModel = this::sendToModel;
                    RemoteSocketClient socketClient = new RemoteSocketClient(client, id, disconnect, fastRecovery, sendToModel);
                    if(!clientIds.contains(id)) //in case of communication loss
                        clientIds.add(id);
                    clientPool.put(id, socketClient);
                    int heartbeatPort = portDiscovery.obtainAvailableUDPPort();
                    DataManager.sendLoginLobbyResponse(client, heartbeatPort);
                    heartbeatProtocolManager.addHost(id, heartbeatPort);
                    executor.submit(socketClient);
                    System.out.println(id + " correctly logged on lobby server");
                    for(String username : clientIds) {
                        System.out.println("Remote user set");
                        clientPool.get(id).setPlayer(username);
                    }
                }
                else
                    DataManager.sendLoginError(client);
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, exc::getMessage);
            }
        }
    }

    private void startGame() {
        inGame = true;
        List<Player> players = new ArrayList<>();
        clientIds.forEach(username -> players.add(new Player(username)));
        dynamicRouter = new MessageDispatcher();
        gameDataManager = new GameDataManager(dynamicRouter, clientPool);
        gameManager = new GameManager(players, dynamicRouter);
        gameManager.startGame();
        System.out.println("Starting game now...");
    }

    private void sendToModel(Message message) {
        dynamicRouter.dispatch(message);
    }

    private class CheckStartGameCondition implements Runnable{
        long elapsedTime;
        int elapsedTimeSecond=0;
        long previousTimeToWait;

        @Override
        public void run() {
            long timeToWait = TIME_WAIT_UNIT * (MAX_POOL_SIZE - clientIds.size() + 1);
            previousTimeToWait = timeToWait;
            while (elapsedTime < timeToWait) {
                elapsedTime = System.currentTimeMillis() - startTime;
                timeToWait = TIME_WAIT_UNIT * (MAX_POOL_SIZE - clientIds.size() + 1);
                if(timeToWait != previousTimeToWait) {
                    previousTimeToWait = timeToWait;
                    elapsedTime = 0;
                }
                int currentSeconds = (int) elapsedTime / 1000;
                if (currentSeconds != elapsedTimeSecond) {
                    elapsedTimeSecond = currentSeconds;
                    System.out.println(elapsedTimeSecond);
                    for(String username : clientIds)
                        try {
                            System.out.println("Remote time set");
                            clientPool.get(username).setTimer((timeToWait / 1000 - currentSeconds) + "");
                        }
                        catch (RemoteException exc) {
                            LOGGER.log(Level.SEVERE, exc::getMessage);
                        }
                }
            }

            if(clientPool.size()>1) startGame();
        }
    }
}