
package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.StateGameController;
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
    public void TestGameController() {

        players.add(playerOne);
        players.add(playerTwo);
        GameController gameController = GameController.getGameController(players);
        assertTrue(gameController.getPlayerNumber() > 0);

        gameController.setupGame();
        assertEquals(StateGameController.DEAL_PUBLIC_OBJECTIVE, gameController.getCurrentState());
    }
}
