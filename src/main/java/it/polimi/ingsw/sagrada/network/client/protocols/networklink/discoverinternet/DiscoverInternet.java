package it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DiscoverInternet {

    public static boolean checkInternetConnection() {
        /*try {
            int timeout = 2000;
            InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
            for (InetAddress address : addresses)
            {
                System.out.println(address.getCanonicalHostName());
                if (address.isReachable(timeout))
                    return true;
            }
            return false;
        }
        catch (IOException exc) {
            return false;
        }*/

        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
    }

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