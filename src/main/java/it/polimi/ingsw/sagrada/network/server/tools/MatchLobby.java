package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.network.server.rmi.ClientRMI;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.server.socket.SocketClient;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


public class MatchLobby extends UnicastRemoteObject implements HeartbeatListener, Runnable, Remote {

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
    private int port;
    private int heartbeatPort;

    public MatchLobby(Function<String, Boolean> signOut) throws IOException {
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
    }

    public boolean isFull() {
        return clientPool.size() == MAX_POOL_SIZE;
    }

    public void addClient(String clientID) {
        clientIdTokens.add(clientID);
    }

    public void closeLobby() {
        heartbeatProtocolManager.kill();
    }

    public int getPort() {
        return port;
    }

    private boolean removePlayer(String username) {
        synchronized (signOut) {
            signOut.apply(username);
        }
        clientPool.remove(username);
        clientIds.remove(username);
        clientIdTokens.remove(username);
        heartbeatProtocolManager.removeFromMonitoredHost(username);
        System.out.println(username + " disconnected");
        for(String clientId : clientIds)
            clientPool.get(clientId).sendMessage(username + " disconnected");
        return true;
    }

    @Override
    public void onHeartbeat(HeartbeatEvent event) {
        System.out.println(event.getSource() + " : i'm alive");
    }

    @Override
    public void onDeath(HeartbeatEvent event) {
        System.out.println(event.getSource() + " is offline");
        removePlayer(event.getSource());
        for(String clientId : clientIds)
            clientPool.get(clientId).sendMessage(event.getSource() + " is offline");
    }

    @Override
    public void onLossCommunication(HeartbeatEvent event) {
        System.out.println(event.getSource() + " has lost communication");
        for(String clientId : clientIds)
            if(!clientId.equals(event.getSource()))
                clientPool.get(clientId).sendMessage(event.getSource() + " may have lost communication");
    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {
        for(String clientId : clientIds)
            if(!clientId.equals(event.getSource()))
                clientPool.get(clientId).sendMessage(event.getSource() + " came back!");
    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        for(String clientId : clientIds) {
            if (!clientId.equals(event.getSource()))
                clientPool.get(clientId).sendMessage(event.getSource() + " is online");
        }
    }

    private boolean fastRecoveryClientConnection(String identifier) {
        clientIdTokens.add(identifier);
        return true;
    }

    public boolean joinLobby(String token, ClientRMI clientRMI) {
        if(!clientIdTokens.contains(token))
            return false;
        if(!clientIds.contains(token))
            clientIds.add(token);
        clientPool.put(token, clientRMI);
        clientRMI.notifyHeartbeatPort(heartbeatPort);
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
                    SocketClient socketClient = new SocketClient(client, id, disconnect, fastRecovery);
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