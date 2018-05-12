package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

class UDPHeartbeatSender {

    /**
     * @param ipAddress ip address to send UDP datagram
     * @param port port number to associate to UDP datagram
     */
    public void sendHeartBeat(InetAddress ipAddress, int port) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            byte[] sendData = NetworkUtils.getMACAddress().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
        }
        catch (SocketException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
        catch (UnknownHostException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
    }
}
