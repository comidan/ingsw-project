
package it.polimi.ingsw.base;


import it.polimi.ingsw.sagrada.game.base.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameManagerTest {

    private Player playerOne = new Player(0);
    private Player playerTwo = new Player(1);
    private List<Player> players = new ArrayList<>();


    @Test
    public void testGameController() {
        GameManager gameManager;

        players.add(playerOne);
        players.add(playerTwo);
        gameManager = GameManager.getGameController(players);
        assertTrue(gameManager.getPlayerNumber() > 0);
        gameManager.startGame();
        assertEquals(StateGameEnum.DEAL_WINDOWS, gameManager.getCurrentState());
        gameManager.playRound();

    }
}
