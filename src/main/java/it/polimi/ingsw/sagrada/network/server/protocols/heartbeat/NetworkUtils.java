package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



/**
 * The Class NetworkUtils.
 */
class NetworkUtils {

    /** The Constant UDP_VALID_PACKET_SIZE. */
    private static final int UDP_VALID_PACKET_SIZE  = 1024;

    /**
     * Receive data.
     *
     * @param datagramSocket socket used to receive from sent data
     * @return received data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static synchronized byte[] receiveData(DatagramSocket datagramSocket) throws IOException {
        byte[] receiveData = new byte[UDP_VALID_PACKET_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        datagramSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        byte[] data = new byte[receivePacket.getLength()];
        System.arraycopy(receiveData, receivePacket.getOffset(), data, 0, receivePacket.getLength());
        return data;
    }
}
