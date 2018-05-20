package it.polimi.ingsw.network.server;

import it.polimi.ingsw.sagrada.network.client.SocketClient;
import it.polimi.ingsw.sagrada.network.server.Server;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServerTest {

    @Test
    public void testServer() {
        try {
            Server server = new Server();
            assertTrue(true);
        }
        catch (Exception exc) {
            fail();
        }

    }
}
