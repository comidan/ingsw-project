package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.InetAddress;


/**
 * The Class HeartbeatProtocol.
 */
class HeartbeatProtocol implements Runnable {

    /** The server address. */
    private InetAddress serverAddress;
    
    /** The udp heartbeat sender. */
    private UDPHeartbeatSender udpHeartbeatSender;
    
    /** The port. */
    private int port;
    
    /** The payload. */
    private String payload;

    /**
     * Instantiates a new heartbeat protocol.
     *
     * @param serverAddress the server address
     * @param port the port
     * @param payload the payload
     * @throws IOException Signals that an I/O exception has occurred.
     */
    HeartbeatProtocol(String serverAddress, int port, String payload) throws IOException {
        this.serverAddress = InetAddress.getByName(serverAddress);
        udpHeartbeatSender = new UDPHeartbeatSender();
        this.port = port;
        this.payload = payload;
    }

    /**
     * Run.
     *
     * @apiNote send an heartbeat
     */
    @Override
    public void run() {
        udpHeartbeatSender.sendHeartBeat(serverAddress, port, payload);
    }
}
