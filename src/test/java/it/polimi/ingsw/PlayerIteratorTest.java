package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.PlayerIterator;
import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {

    @Test
    public void testPlayerIterator() {
        GameController gameController = GameController.getGameController();
        CardController cardController = new CardController();
        ToolManager toolManager = ToolManager.getInstance(cardController.dealTool());
        Player playerOne = new Player(gameController, toolManager);
        Player playerTwo = new Player(gameController, toolManager);
        Player playerThree = new Player(gameController, toolManager);
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
