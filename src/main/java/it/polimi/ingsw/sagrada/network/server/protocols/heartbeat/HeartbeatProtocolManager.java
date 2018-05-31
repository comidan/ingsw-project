package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeartbeatProtocolManager implements Runnable, Observer<HeartbeatState, HeartbeatEvent> {

    private static final Logger LOGGER = Logger.getLogger(HeartbeatProtocolManager.class.getName());

    private HeartbeatListener listener;
    private ExecutorService executor;
    private ExecutorService serverExecutor;
    private DatagramSocket datagramSocket;
    private Map<String, HeartbeatProtocol> monitoredHosts;

    public HeartbeatProtocolManager(HeartbeatListener listener, int port) throws IOException {
        this.listener = listener;
        datagramSocket = new DatagramSocket(port);
        monitoredHosts = new HashMap<>();
        executor = Executors.newCachedThreadPool();
        serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.submit(this);
    }

    public void kill() {
        executor.shutdown();
    }

    @Override
    public void run() {
        while (!serverExecutor.isShutdown()) {
            try {
                byte[] remoteData = NetworkUtils.receiveData(datagramSocket);
                String hostId = new String(remoteData);
                if(monitoredHosts.get(hostId) == null) {
                    HeartbeatProtocol heartbeatProtocol = new HeartbeatProtocol(datagramSocket, this, new String(remoteData));
                    monitoredHosts.put(hostId, heartbeatProtocol);
                    executor.submit(heartbeatProtocol);
                    listener.onAcquiredCommunication(new HeartbeatEvent(hostId, 0, new Date().getTime()));
                }
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, exc.getMessage());
            }
        }
    }

    public boolean removeFromMonitoredHost(String identifier) {
        HeartbeatProtocol monitor = monitoredHosts.remove(identifier);
        if(monitor != null) {
            monitor.kill();
            return true;
        }
        return false;
    }

    @Override
    public synchronized void update(HeartbeatState heartbeatState, HeartbeatEvent event) {
        switch (heartbeatState) {

            case HOST_OFFLINE: monitoredHosts.remove(event.getSource());
                               listener.onDeath(event);
                               break;
            case HEARTBEAT_RECEIVED: listener.onHeartbeat(event); break;
            case HOST_ONLINE: listener.onReacquiredCommunication(event); break;
            case COMMUNICATION_LOST: listener.onLossCommunication(event); break;
            default: break;
        }
    }
}
