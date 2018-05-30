package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.socket.SocketServer;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class GameServer {
    
    private static final Registry registry = getRegistry();

    public static void main(String[] args) throws InterruptedException, SQLException, ExecutionException, SocketException, RemoteException {
        SocketServer socketServer = new SocketServer();
        ServerRMI serverRMI = new ServerRMI();
    }

    private static Registry getRegistry() {
        try {
            return LocateRegistry.createRegistry(1099);
        }
        catch (RemoteException exc) {
            exc.printStackTrace();
            System.exit(-1);
            return null;
        }
    }
}
