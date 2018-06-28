package it.polimi.ingsw.network;

import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobbyPool;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class MatchLobbyPoolTest {
    MatchLobbyPool matchLobbyPool;


    @Test
    public void testMatchLobby() {
        matchLobbyPool = new MatchLobbyPool();
        try {
            MatchLobby matchLobby = matchLobbyPool.getAvailableLobby("test");
            assertNotNull(matchLobby);
            assertFalse(matchLobby.isFull());
            assertTrue(matchLobbyPool.releaseLobby(matchLobby));

        } catch (IOException exc) {

        }


    }
}
