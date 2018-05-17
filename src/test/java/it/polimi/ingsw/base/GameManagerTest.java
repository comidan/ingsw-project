
package it.polimi.ingsw.base;


import it.polimi.ingsw.sagrada.game.base.*;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
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
        DynamicRouter dynamicRouter = new MessageDispatcher();

        players.add(playerOne);
        players.add(playerTwo);
        gameManager = new GameManager(players, dynamicRouter);
        assertTrue(gameManager.getPlayerNumber() > 0);
        gameManager.startGame();
        assertEquals(StateGameEnum.DEAL_WINDOWS, gameManager.getCurrentState());
        gameManager.playRound();

    }
}
