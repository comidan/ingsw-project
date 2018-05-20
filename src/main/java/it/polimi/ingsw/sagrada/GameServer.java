package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.Server;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class GameServer {

    public static void main(String[] args) throws InterruptedException, SQLException, ExecutionException, SocketException {
        Server server = new Server();
    }
}
