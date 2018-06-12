package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.NetworkUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkUtilsTest {

    @Test
    public void testNetworkUtils() throws Exception {
        assertEquals(17, NetworkUtils.getMACAddress().length());
    }
}