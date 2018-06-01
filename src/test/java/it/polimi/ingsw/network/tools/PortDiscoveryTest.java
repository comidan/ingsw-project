package it.polimi.ingsw.network.tools;

import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PortDiscoveryTest {

    private static final Logger LOGGER = Logger.getLogger(PortDiscoveryTest.class.getName());
    private static final String ASYNC_LOG = "waiting port being discovered, you can do something else";
    private static final String ERROR_LOG = "error while discovering port";

    private static final int MIN_PORT_NUMBER = 49152;
    private static final int MAX_PORT_NUMBER = 65535;

    @Test
    public void portDiscoveryTest() {
        PortDiscovery portDiscovery = new PortDiscovery();
        int port = portDiscovery.obtainAvailablePort();
        assertTrue(MIN_PORT_NUMBER <= port && port <= MAX_PORT_NUMBER);
        port = portDiscovery.obtainAvailableTCPPort();
        assertTrue(MIN_PORT_NUMBER <= port && port <= MAX_PORT_NUMBER);
        port = portDiscovery.obtainAvailableUDPPort();
        assertTrue(MIN_PORT_NUMBER <= port && port <= MAX_PORT_NUMBER);
        try {
            Future<Integer> discoveringPort = portDiscovery.obtainAvailablePortAsync();
            while (!discoveringPort.isDone())
                LOGGER.log(Level.INFO, () -> ASYNC_LOG);
            final int discoveredPort = discoveringPort.get();
            assertTrue(MIN_PORT_NUMBER <= discoveredPort && discoveredPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available TCP and UDP port : " + discoveredPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () ->  ERROR_LOG);
            fail();
        }
        try {
            Future<Integer> discoveringPort = portDiscovery.obtainAvailablePortOnTCPAsync();
            while (!discoveringPort.isDone())
                LOGGER.log(Level.INFO, () -> ASYNC_LOG);
            final int discoveredPort = discoveringPort.get();
            assertTrue(MIN_PORT_NUMBER <= discoveredPort && discoveredPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available TCP port : " + discoveredPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () -> ERROR_LOG);
            fail();
        }
        try {
            Future<Integer> discoveringPort = portDiscovery.obtainAvailablePortOnUDPAsync();
            while (!discoveringPort.isDone())
                LOGGER.log(Level.INFO, () -> ASYNC_LOG);
            final int discoveredPort = discoveringPort.get();
            assertTrue(MIN_PORT_NUMBER <= discoveredPort && discoveredPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available UDP port : " + discoveredPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () -> ERROR_LOG);
            fail();
        }

    }
}
