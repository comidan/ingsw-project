package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class HeartbeatProtocolManager.
 */
public class HeartbeatProtocolManager implements Observer<HeartbeatState, HeartbeatEvent> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(HeartbeatProtocolManager.class.getName());

    /** The listener. */
    private HeartbeatListener listener;
    
    /** The executor. */
    private ExecutorService executor;
    
    /** The monitored hosts. */
    private Map<String, HeartbeatProtocol> monitoredHosts;

    /**
     * Instantiates a new heartbeat protocol manager.
     *
     * @param listener the listener
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public HeartbeatProtocolManager(HeartbeatListener listener) throws IOException {
        this.listener = listener;
        monitoredHosts = new HashMap<>();
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Kill.
     */
    public void kill() {
        executor.shutdown();
    }

    /**
     * Adds the host.
     *
     * @param hostId the host id
     * @param port the port
     * @return true if host added as monitored host
     */
    public boolean addHost(String hostId, int port) {
        try {
            if (monitoredHosts.get(hostId) == null) {
                HeartbeatProtocol heartbeatProtocol = new HeartbeatProtocol(port, this, hostId);
                monitoredHosts.put(hostId, heartbeatProtocol);
                executor.submit(heartbeatProtocol);
                listener.onAcquiredCommunication(new HeartbeatEvent(hostId, 0, new Date().getTime()));
                return true;
            }
            return false;
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            return false;
        }
    }

    /**
     * Removes the from monitored host.
     *
     * @param identifier the identifier
     * @return true, if successful
     */
    public boolean removeFromMonitoredHost(String identifier) {
        HeartbeatProtocol monitor = monitoredHosts.remove(identifier);
        if(monitor != null) {
            monitor.kill();
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.Observer#update(java.lang.Object, java.lang.Object)
     */
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
