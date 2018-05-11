package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.PlayerIterator;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {

    @Test
    public void testPlayerIterator() {
        GameController gameController = GameController.getGameController();
        ToolManager toolManager = new ToolManager();
        DiceController diceController = DiceController.getDiceController(2);
        Player playerOne = new Player(gameController, toolManager, diceController);
        Player playerTwo = new Player(gameController, toolManager, diceController);
        Player playerThree = new Player(gameController, toolManager, diceController);
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
