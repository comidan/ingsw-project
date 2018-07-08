package it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



/**
 * The Class DiscoverInternet.
 */
public class DiscoverInternet {

    /**
     * Check internet connection.
     *
     * @return true, if successful
     */
    public static boolean checkInternetConnection() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if is private IP.
     *
     * @param address the address
     * @return true, if is private IP
     */
    public static boolean isPrivateIP(InetAddress address) {
        if(!(address instanceof Inet4Address))
            return false;
        int[] octets = new int[4];
        String[] toParseOctets = address.getHostAddress().split("\\.");
        for(int i = 0; i < toParseOctets.length; i++)
            octets[i] = Integer.parseInt(toParseOctets[i]);
        if(octets[0] == 10)
            return true;
        if(octets[0] == 172 && (octets[1] >= 16 && octets[1] <= 32))
            return true;
        if(octets[0] == 192 && octets[1] == 168)
            return true;
        return false;
    }
}