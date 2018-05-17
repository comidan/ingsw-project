package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortDiscovery {

    private static final int MIN_PORT_NUMBER = 49152;
    private static final int MAX_PORT_NUMBER = 65535;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public int obtainAvailablePort() {
        for(int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++)
            if(isPortAvailable(port))
                return port;
        throw new RuntimeException("No available port has been found");
    }

    public int obtainAvailableTCPPort() {
        for(int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++)
            if(isPortAvailableOnTCP(port))
                return port;
        throw new RuntimeException("No available port has been found");
    }

    public int obtainAvailableUDPPort() {
        for(int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++)
            if(isPortAvailableOnUDP(port))
                return port;
        throw new RuntimeException("No available port has been found");
    }

    public Future<Integer> obtainAvailablePortAsync() {

        return executor.submit(this::obtainAvailablePort);
    }

    public Future<Integer> obtainAvailablePortOnTCPAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(this::obtainAvailableTCPPort);
    }

    public Future<Integer> obtainAvailablePortOnUDPAsync() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(this::obtainAvailableUDPPort);
    }

    private boolean isPortAvailable(int port) {
        return isPortAvailableOnTCP(port) && isPortAvailableOnUDP(port);
    }

    private boolean isPortAvailableOnUDP(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }
        try(DatagramSocket datagramSocket = new DatagramSocket(port)) {
            datagramSocket.setReuseAddress(true);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    private boolean isPortAvailableOnTCP(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
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
