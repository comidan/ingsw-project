package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.server.SocketClient;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


public class MatchLobby implements HeartbeatListener {

    private static final int MAX_POOL_SIZE = 4;
    private Map<String, SocketClient> socketClientPool;
    private List<String> clientIds;
    private ExecutorService executor;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private PortDiscovery portDiscovery;
    private Function<String, Boolean> signOut;

    public MatchLobby(Function<String, Boolean> signOut) throws IOException {
        socketClientPool = new HashMap<>();
        portDiscovery = new PortDiscovery();
        clientIds = new ArrayList<>();
        this.signOut = signOut;
        heartbeatProtocolManager = new HeartbeatProtocolManager(this, portDiscovery.obtainAvailableUDPPort());
        executor = Executors.newCachedThreadPool();
    }

    public boolean isFull() {
        return socketClientPool.size() == MAX_POOL_SIZE;
    }

    public void addClient(String clientID, SocketClient socketClient) {
        socketClientPool.put(clientID, socketClient);
        clientIds.add(clientID);
        executor.submit(socketClient);
    }

    public void closeLobby() {
        heartbeatProtocolManager.kill();
    }

    private void removePlayer(String username) {
        synchronized (signOut) {
            signOut.apply(username);
        }
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
        //warning : maybe is down, wait for onDeath event before removal
    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {

    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        //notifyAll or use addClient above
    }


    // deve implementare listener del protocollo heartbeat
}
