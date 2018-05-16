package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.RoundIterator;

import it.polimi.ingsw.sagrada.game.playables.DiceManager;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RoundIteratorTest {

    @Test
    public void testRoundIterator() {
        assertNotNull(RoundIterator.getRoundIterator());
        assertNotNull(DiceManager.getDiceManager(2));

    }


}
