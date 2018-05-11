
package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.*;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameControllerTest {

    private GameController gameController;
    private ToolManager toolManager;
    private DiceController diceController;
    private Player playerOne = new Player(gameController, toolManager, diceController);
    private Player playerTwo = new Player(gameController, toolManager, diceController);
    private List<Player> players = new ArrayList<>();


    @Test
    public void testGameController() {

        players.add(playerOne);
        players.add(playerTwo);
        GameController gameController = GameController.getGameController(players);
        assertTrue(gameController.getPlayerNumber() > 0);
        gameController.setupGame();
        assertEquals(StateGameEnum.DEAL_PUBLIC_OBJECTIVE, gameController.getCurrentState());
        gameController.playRound();


    }
}
