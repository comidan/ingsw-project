package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.Server;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class GameServer {

    public static void main(String[] args) throws InterruptedException, SQLException, ExecutionException {
        Server server = new Server();
    }
}
