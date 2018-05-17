package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.network.client.SocketClient;
import it.polimi.ingsw.sagrada.network.utilities.MatchLobby;
import org.junit.Test;

import java.net.Socket;
import java.util.List;

import static org.junit.Assert.*;

public class MatchLobbyTest {

    @Test
    public void testMatchLobby() {
        MatchLobby matchLobby = new MatchLobby();
        assertNotNull(matchLobby);
        Socket socket = new Socket();
        SocketClient socketClient = new SocketClient(socket);
        matchLobby.addClient(socketClient);
        assertFalse(matchLobby.isFull());
        SocketClient socketClientTwo = new SocketClient(socket);
        SocketClient socketClientThree = new SocketClient(socket);
        SocketClient socketClientFour = new SocketClient(socket);
        matchLobby.addClient(socketClientTwo);
        matchLobby.addClient(socketClientThree);
        matchLobby.addClient(socketClientFour);
        assertTrue(matchLobby.isFull());


    }


}
