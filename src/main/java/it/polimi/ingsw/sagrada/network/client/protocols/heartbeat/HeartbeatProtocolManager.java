package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The Class HeartbeatProtocolManager.
 */
public class HeartbeatProtocolManager {

    /** The Constant DELAY. */
    private static final long DELAY  = 1000L;
    
    /** The Constant PERIOD. */
    private static final long PERIOD = 1000L;
    
    /** The heartbeat protocol. */
    private HeartbeatProtocol heartbeatProtocol;
    
    /** The executor. */
    private ScheduledExecutorService executor;

    /**
     * Instantiates a new heartbeat protocol manager.
     *
     * @param host the host
     * @param port the port
     * @param payload the payload
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public HeartbeatProtocolManager(String host, int port, String payload) throws IOException {
        heartbeatProtocol = new HeartbeatProtocol(host, port, payload);
        executor = Executors.newSingleThreadScheduledExecutor();
        start();
    }

    /**
     * Start.
     *
     * @apiNote send an heartbeat
     */
    private void start() {
        executor.scheduleAtFixedRate(heartbeatProtocol, DELAY, PERIOD, TimeUnit.MILLISECONDS);
    }

    /**
     * Kill.
     *
     * @apiNote kill protocol execution
     */
    public void kill() {
        System.out.println("Someone called me");
        executor.shutdownNow();
    }

}
