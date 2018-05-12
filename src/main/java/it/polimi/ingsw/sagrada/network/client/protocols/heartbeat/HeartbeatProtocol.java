package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.InetAddress;

class HeartbeatProtocol implements Runnable {

    private InetAddress serverAddress;
    private UDPHeartbeatSender udpHeartbeatSender;
    private int port;

    public HeartbeatProtocol(String serverAddress, int port) throws IOException {
        this.serverAddress = InetAddress.getByName(serverAddress);
        udpHeartbeatSender = new UDPHeartbeatSender();
        this.port = port;
    }

    /**
     * @apiNote send an heartbeat
     */
    @Override
    public void run() {
        udpHeartbeatSender.sendHeartBeat(serverAddress, port);
    }
}
