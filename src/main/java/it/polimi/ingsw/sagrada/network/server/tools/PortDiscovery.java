package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;



/**
 * The Class PortDiscovery.
 */
public class PortDiscovery {

    /** The Constant MIN_PORT_NUMBER. */
    private static final int MIN_PORT_NUMBER = 49152;
    
    /** The Constant MAX_PORT_NUMBER. */
    private static final int MAX_PORT_NUMBER = 65535;

    /** The Constant DISCOVERY_ERROR. */
    private static final String DISCOVERY_ERROR = "No available port has been found";
    
    /** The Constant INVALID_INIT_PORT. */
    private static final String INVALID_INIT_PORT = "Invalid start port:";

    /** The executor. */
    private ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Obtain available port.
     *
     * @return the int
     */
    public int obtainAvailablePort() {
        Optional<Integer> port = IntStream.range(MIN_PORT_NUMBER, MAX_PORT_NUMBER).boxed().filter(this::isPortAvailable).findFirst();
        if(port.isPresent())
            return port.get();
        throw new RuntimeException(DISCOVERY_ERROR);
    }

    /**
     * Obtain available TCP port.
     *
     * @return the int
     */
    public int obtainAvailableTCPPort() {
        Optional<Integer> port = IntStream.range(MIN_PORT_NUMBER, MAX_PORT_NUMBER).boxed().filter(this::isPortAvailableOnTCP).findFirst();
        if(port.isPresent())
            return port.get();
        throw new RuntimeException(DISCOVERY_ERROR);
    }

    /**
     * Obtain available UDP port.
     *
     * @return the int
     */
    public int obtainAvailableUDPPort() {
        Optional<Integer> port = IntStream.range(MIN_PORT_NUMBER, MAX_PORT_NUMBER).boxed().filter(this::isPortAvailableOnUDP).findFirst();
        if(port.isPresent())
            return port.get();
        throw new RuntimeException(DISCOVERY_ERROR);
    }

    /**
     * Obtain available port async.
     *
     * @return the future
     */
    public Future<Integer> obtainAvailablePortAsync() {

        return executor.submit(this::obtainAvailablePort);
    }

    /**
     * Obtain available port on TCP async.
     *
     * @return the future
     */
    public Future<Integer> obtainAvailablePortOnTCPAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(this::obtainAvailableTCPPort);
    }

    /**
     * Obtain available port on UDP async.
     *
     * @return the future
     */
    public Future<Integer> obtainAvailablePortOnUDPAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(this::obtainAvailableUDPPort);
    }

    /**
     * Checks if is port available.
     *
     * @param port the port
     * @return true, if is port available
     */
    private boolean isPortAvailable(int port) {
        return isPortAvailableOnTCP(port) && isPortAvailableOnUDP(port);
    }

    /**
     * Checks if is port available on UDP.
     *
     * @param port the port
     * @return true, if is port available on UDP
     */
    private boolean isPortAvailableOnUDP(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException(INVALID_INIT_PORT + port);
        }
        try(DatagramSocket datagramSocket = new DatagramSocket(port)) {
            datagramSocket.setReuseAddress(true);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    /**
     * Checks if is port available on TCP.
     *
     * @param port the port
     * @return true, if is port available on TCP
     */
    private boolean isPortAvailableOnTCP(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException(INVALID_INIT_PORT + port);
        }
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }
}
