package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.socket.SocketServer;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class GameServer {

    public static void main(String[] args) throws InterruptedException, SQLException, ExecutionException, SocketException, RemoteException {
        SocketServer socketServer = new SocketServer();
        ServerRMI serverRMI = new ServerRMI();
    }
}
