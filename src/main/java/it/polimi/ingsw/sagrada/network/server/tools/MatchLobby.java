package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.network.client.Client;
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
import java.util.function.Function;


public class MatchLobby extends UnicastRemoteObject implements HeartbeatListener, Runnable, AbstractMatchLobbyRMI {

    private static final int MAX_POOL_SIZE = 4;

    private Map<String, Client> clientPool;
    private List<String> clientIds;
    private List<String> clientIdTokens;
    private ExecutorService executor;
    private ExecutorService lobbyServer;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private PortDiscovery portDiscovery;
    private Function<String, Boolean> signOut;
    private ServerSocket serverSocket;
    private String identifier;
    private int port;
    private int heartbeatPort;

    public MatchLobby(Function<String, Boolean> signOut, String identifier) throws IOException {
        clientPool = new HashMap<>();
        portDiscovery = new PortDiscovery();
        clientIds = new ArrayList<>();
        clientIdTokens = new ArrayList<>();
        this.signOut = signOut;
        port = portDiscovery.obtainAvailableTCPPort();
        serverSocket = new ServerSocket(port);
        heartbeatProtocolManager = new HeartbeatProtocolManager(this, heartbeatPort = portDiscovery.obtainAvailableUDPPort());
        executor = Executors.newCachedThreadPool();
        lobbyServer = Executors.newSingleThreadExecutor();
        lobbyServer.submit(this);
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
            }
            catch (RemoteException exc) {
                System.out.println("RMI error on " + clientId);
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
                System.out.println("RMI error on " + clientId);
            }
    }

    @Override
    public void onLossCommunication(HeartbeatEvent event) {

    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {

    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        for(String clientId : clientIds) {
            if (!clientId.equals(event.getSource()))
                try {
                    clientPool.get(clientId).sendMessage(event.getSource() + " is online");
                }
                catch (RemoteException exc) {
                    System.out.println("RMI error on " + clientId);
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
        clientRMI.notifyHeartbeatPort(heartbeatPort);
        Function<String, Boolean> disconnect = this::removePlayer;
        clientRMI.notifyRemoteClientInterface(new RemoteRMIClient(token, disconnect));
        return true;
    }

    @Override
    public void run() {
        while(!lobbyServer.isShutdown()) {
            try {
                Socket client = serverSocket.accept();
                int tokenIndex = LoginManager.tokenAuthentication(clientIdTokens, client);
                if(tokenIndex != -1) {
                    String id = clientIdTokens.remove(tokenIndex);
                    Function<String, Boolean> disconnect = this::removePlayer;
                    Function<String, Boolean> fastRecovery = this::fastRecoveryClientConnection;
                    RemoteSocketClient socketClient = new RemoteSocketClient(client, id, disconnect, fastRecovery);
                    if(!clientIds.contains(id)) //in case of communication loss
                        clientIds.add(id);
                    clientPool.put(id, socketClient);
                    LoginManager.sendLoginLobbyResponse(client, heartbeatPort);
                    executor.submit(socketClient);
                    System.out.println(id + " correctly logged on lobby server");
                }
                else
                    LoginManager.sendLoginError(client);
            }
            catch (IOException exc) {
            }
        }
    }
}