package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.*;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DiceManagerTest implements Channel<DiceResponse, DiceEvent> {
    private int numberOfPlayers = 3;
    private DiceManager diceManager;

    @Test
    public void testDicePick() {
        DynamicRouter dynamicRouter = new MessageDispatcher();
        dynamicRouter.subscribeChannel(DiceResponse.class, this);
        diceManager = new DiceManager(numberOfPlayers, null, dynamicRouter);
        diceManager.bagToDraft();
        diceManager.setNumberOfPlayers(++numberOfPlayers);
        diceManager.bagToDraft();
    }

    @Override
    public void dispatch(DiceResponse message) {
        assertEquals(2 * numberOfPlayers + 1, diceManager.getDraft().size());
        assertArrayEquals(message.getDiceList().toArray(new Dice[0]), diceManager.getDraft().toArray(new Dice[0]));
    }

    @Override
    public void sendMessage(DiceEvent message) {
        throw new UnsupportedOperationException();
    }
}
