package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.socket.SocketServer;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer {

    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());
    private static final Registry registry = getRegistry();

    public static void main(String[] args) throws InterruptedException, SQLException, ExecutionException, SocketException, RemoteException {
        new SocketServer();
        new ServerRMI();
    }

    private static Registry getRegistry() {
        try {
            return LocateRegistry.createRegistry(1099);
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, () -> exc.getMessage());
            System.exit(-1);
            return null;
        }
    }
}
