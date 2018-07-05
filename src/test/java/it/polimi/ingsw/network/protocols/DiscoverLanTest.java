package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan.DiscoverLan;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertTrue;

public class DiscoverLanTest {

    @Test
    public void testDiscoverLan() throws UnknownHostException {
        DiscoverLan discoverLan = new DiscoverLan();
        assertTrue(discoverLan.isDirectlyAttachedAndReachable(InetAddress.getByName(Inet4Address.getByName(System.getProperty("myapplication.ip")).getHostAddress())));
    }
}
