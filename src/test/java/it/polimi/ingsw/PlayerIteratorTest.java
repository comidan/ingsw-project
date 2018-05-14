package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.PlayerIterator;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {

    @Test
    public void testPlayerIterator() {
        Player playerOne = new Player(0);
        Player playerTwo = new Player(1);
        Player playerThree = new Player(2);
        List<Player> playerList = new ArrayList<>();
        playerList.add(playerOne);
        playerList.add(playerTwo);
        playerList.add(playerThree);
        PlayerIterator playerIterator = PlayerIterator.getPlayerIterator(playerList);
        assertNotNull(playerIterator);
        assertNotNull(playerIterator.playerList());
        while (playerIterator.hasNext()) {
            assertNotNull(playerIterator.next());
        }


    }
}
