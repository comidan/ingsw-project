package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class HeartbeatProtocolManager implements Runnable, Observer<HeartbeatState, HeartbeatEvent>, NetworkUtils {

    private int port;
    private HeartbeatListener listener;
    private ExecutorService executor;
    private ExecutorService serverExecutor;
    private DatagramSocket datagramSocket;
    private HashSet<String> monitoredHosts;

    public HeartbeatProtocolManager(HeartbeatListener listener, int port) throws IOException {
        this.listener = listener;
        this.port = port;
        datagramSocket = new DatagramSocket(port);
        monitoredHosts = new HashSet<>();
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
                byte[] remoteData = receiveData(datagramSocket);
                String hostId = new String(remoteData);
                if(!monitoredHosts.contains(hostId)) {
                    monitoredHosts.add(hostId);
                    HeartbeatProtocol heartbeatProtocol = new HeartbeatProtocol(datagramSocket, this);
                    executor.submit(heartbeatProtocol);
                    listener.onAcquiredCommunication(new HeartbeatEvent(hostId, 0, new Date().getTime()));
                }
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, exc.getMessage());
            }
        }
    }

    @Override
    public void update(HeartbeatState heartbeatState, HeartbeatEvent event) {
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
