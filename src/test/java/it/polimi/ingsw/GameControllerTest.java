
package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.*;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameControllerTest {

    private GameController gameController;
    private ToolManager toolManager;
    private Player playerOne = new Player(gameController, toolManager);
    private Player playerTwo = new Player(gameController, toolManager);
    private List<Player> players = new ArrayList<>();


    @Test
    public void testGameController() {

        players.add(playerOne);
        players.add(playerTwo);
        gameController = GameController.getGameController(players);
        assertTrue(gameController.getPlayerNumber() > 0);
        gameController.startGame();
        assertEquals(StateGameEnum.DEAL_WINDOWS, gameController.getCurrentState());
        gameController.playRound();

    }
}
