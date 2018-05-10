package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.RoundIterator;

import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.*;

public class RoundIteratorTest {

    @Test
    public void TestRoundIterator() {
        RoundIterator roundIterator = RoundIterator.getRoundIterator();
        DiceController diceController = DiceController.getDiceController(2);

    }


}
