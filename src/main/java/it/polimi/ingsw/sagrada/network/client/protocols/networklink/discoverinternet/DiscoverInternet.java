package it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

public class DiscoverInternet {

    public static boolean checkInternetConnection() {
        try {
            int timeout = 2000;
            InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
            for (InetAddress address : addresses)
                if (address.isReachable(timeout))
                    return true;
            return false;
        }
        catch (IOException exc) {
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
