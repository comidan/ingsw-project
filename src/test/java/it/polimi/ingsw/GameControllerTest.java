
package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import org.junit.Test;

import static org.junit.Assert.*;


public class GameControllerTest {

    GameController gameController;
    ToolManager toolManager;
    DiceController diceController;
    Player playerOne = new Player(gameController, toolManager, diceController);
    Player playerTwo = new Player(gameController, toolManager, diceController);
    Player[] players = new Player[2];


    @Test
    public void TestGameController() {

        players[0] = playerOne;
        players[1] = playerTwo;
        GameController gameController = GameController.getGameController(players);
        assertTrue(gameController.getPlayerNumber() > 0);
    }
}
