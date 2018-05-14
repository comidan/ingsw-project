
package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameControllerTest {

    private Player playerOne = new Player(0);
    private Player playerTwo = new Player(1);
    private List<Player> players = new ArrayList<>();


    @Test
    public void testGameController() {
        GameController gameController;

        players.add(playerOne);
        players.add(playerTwo);
        gameController = GameController.getGameController(players);
        assertTrue(gameController.getPlayerNumber() > 0);
        gameController.startGame();
        assertEquals(StateGameEnum.DEAL_WINDOWS, gameController.getCurrentState());
        gameController.playRound();

    }
}
