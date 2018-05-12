package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exc.getMessage());
        }
        catch (UnknownHostException exc) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exc.getMessage());
        }
        catch (IOException exc) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exc.getMessage());
        }
    }
}
