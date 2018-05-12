package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public interface NetworkUtils {

    int UDP_VALID_PACKET_SIZE  = 1024;

    /**
     * @param datagramSocket socket used to receive from sent data
     * @return received data
     */
    default byte[] receiveData(DatagramSocket datagramSocket) throws IOException {
        byte[] receiveData = new byte[UDP_VALID_PACKET_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        datagramSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        return receiveData;
    }
}
