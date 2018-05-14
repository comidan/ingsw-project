package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.RoundIterator;
import it.polimi.ingsw.sagrada.game.playables.DiceController;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RoundIteratorTest {

    @Test
    public void testRoundIterator() {
        assertNotNull(RoundIterator.getRoundIterator());
        assertNotNull(DiceController.getDiceController(2));

    }


}
