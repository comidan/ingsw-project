package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.logging.Level;
import java.util.logging.Logger;

class UDPHeartbeatSender {

    private static final Logger LOGGER = Logger.getLogger(UDPHeartbeatSender.class.getName());

    /**
     * @param ipAddress ip address to send UDP datagram
     * @param port port number to associate to UDP datagram
     */
    void sendHeartBeat(InetAddress ipAddress, int port) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            byte[] sendData = NetworkUtils.getMACAddress().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
        }
        catch (SocketException exc) {
            LOGGER.log(Level.SEVERE, "Socket error\n" + exc.getMessage());
        }
        catch (UnknownHostException exc) {
            LOGGER.log(Level.SEVERE, "Unknown host error\n" + exc.getMessage());
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, "IO error\n" + exc.getMessage());
        }
    }
}
