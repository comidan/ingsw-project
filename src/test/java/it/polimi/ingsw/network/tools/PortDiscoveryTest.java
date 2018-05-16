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
            Future<Integer> disocveryingPort = portDiscovery.obtainAvailablePortAsync();
            while (!disocveryingPort.isDone())
                LOGGER.log(Level.INFO, () -> "waiting port being discovered, you can do something else");
            final int disocoveriedPort = disocveryingPort.get();
            assertTrue(MIN_PORT_NUMBER <= disocoveriedPort && disocoveriedPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available TCP and UDP port : " + disocoveriedPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () -> "error while discoverying port");
            fail();
        }
        try {
            Future<Integer> disocveryingPort = portDiscovery.obtainAvailablePortOnTCPAsync();
            while (!disocveryingPort.isDone())
                LOGGER.log(Level.INFO, () -> "waiting port being discovered, you can do something else");
            final int disocoveriedPort = disocveryingPort.get();
            assertTrue(MIN_PORT_NUMBER <= disocoveriedPort && disocoveriedPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available TCP port : " + disocoveriedPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () -> "error while discoverying port");
            fail();
        }
        try {
            Future<Integer> disocveryingPort = portDiscovery.obtainAvailablePortOnUDPAsync();
            while (!disocveryingPort.isDone())
                LOGGER.log(Level.INFO, () -> "waiting port being discovered, you can do something else");
            final int disocoveriedPort = disocveryingPort.get();
            assertTrue(MIN_PORT_NUMBER <= disocoveriedPort && disocoveriedPort <= MAX_PORT_NUMBER);
            LOGGER.log(Level.INFO, () -> "discover available UDP port : " + disocoveriedPort);
        }
        catch (ExecutionException|InterruptedException exc) {
            LOGGER.log(Level.SEVERE, () -> "error while discoverying port");
            fail();
        }

    }
}
