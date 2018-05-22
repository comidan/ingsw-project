package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet.DiscoverInternet;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscoverInternetTest {

    @Test
    public void testIsPrivateIP() throws UnknownHostException {
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName("192.168.1.1")));
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName("172.32.5.90")));
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName("10.124.123.3")));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName("192.167.1.1")));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName("172.15.1.1")));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName("11.1.1.1")));
    }
}