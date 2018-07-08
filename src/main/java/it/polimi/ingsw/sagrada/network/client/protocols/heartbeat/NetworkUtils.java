package it.polimi.ingsw.sagrada.network.client.protocols.heartbeat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;



/**
 * The Interface NetworkUtils.
 */
public interface NetworkUtils {

    /**
     * Gets the MAC address.
     *
     * @return the MAC address
     * @throws UnknownHostException the unknown host exception
     * @throws SocketException the socket exception
     * @apiNote get this network interface's MAC address
     */
    static String getMACAddress() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }
}
