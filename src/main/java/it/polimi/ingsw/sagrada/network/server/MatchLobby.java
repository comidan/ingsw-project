package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


public class MatchLobby implements HeartbeatListener, Runnable {

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

    public MatchLobby(Function<String, Boolean> signOut) throws IOException {
        clientPool = new HashMap<>();
        portDiscovery = new PortDiscovery();
        clientIds = new ArrayList<>();
        clientIdTokens = new ArrayList<>();
        this.signOut = signOut;
        port = portDiscovery.obtainAvailableTCPPort();
        serverSocket = new ServerSocket(port);
        heartbeatProtocolManager = new HeartbeatProtocolManager(this, portDiscovery.obtainAvailableUDPPort());
        executor = Executors.newCachedThreadPool();
        lobbyServer = Executors.newSingleThreadExecutor();
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

    private void removePlayer(String username) {
        synchronized (signOut) {
            signOut.apply(username);
        }
        clientPool.remove(username);
        clientIds.remove(username);
    }

    @Override
    public void onHeartbeat(HeartbeatEvent event) {
        //ok
    }

    @Override
    public void onDeath(HeartbeatEvent event) {
        removePlayer(event.getSource());
    }

    @Override
    public void onLossCommunication(HeartbeatEvent event) {
        //warning : maybe is down, wait for onDeath event before removal AND notify other player of imminent loss comm to player x
    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {
        //notify every player that player x came back online
    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        //notifyAll or use addClient above
    }

    @Override
    public void run() {
        while(!lobbyServer.isShutdown()) {
            try {
                Socket client = serverSocket.accept();
                int tokenIndex = LoginManager.tokenAuthentication(clientIdTokens, client);
                if(tokenIndex != -1) {
                    SocketClient socketClient = new SocketClient(client);
                    String id = clientIdTokens.remove(tokenIndex);
                    clientIds.add(id);
                    clientPool.put(id, socketClient);
                    executor.submit(socketClient);
                }
                else
                    LoginManager.sendLoginError(client, "level two auth error");
            }
            catch (IOException exc) {
            }
        }
    }
}
