package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.junit.Test;

import java.awt.*;

public class DiceTest {

    @Test
    public void testApp() {
        Dice dice;
        for (int diceValue = 1; diceValue < 7; diceValue++) {
            try {
                dice = new Dice(diceValue, Color.RED);
                assertEquals(diceValue, dice.getValue());
            } catch (Exception exc) {
                assertTrue(false);
            }
        }
        try {
            dice = new Dice(7, Color.RED);
            assertTrue(false);
        } catch (Exception exc) {
            assertTrue(true);
        }
    }
}