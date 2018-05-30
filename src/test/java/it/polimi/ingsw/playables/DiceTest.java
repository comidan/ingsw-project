package it.polimi.ingsw.playables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.junit.Test;

public class DiceTest {

    @Test
    public void testDice() {
        Dice dice;
        for (int id = 0; id < 90; id++) {
            dice = new Dice(id, Colors.RED);
            assertEquals(id, dice.getId());
        }

        Dice diceOne = new Dice(3, Colors.RED);
        diceOne.setValue(2);
        assertEquals(diceOne.getValue(), 2);
    }
}