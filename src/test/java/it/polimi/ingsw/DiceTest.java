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
        for (int id = 0; id < 90; id++) {
            dice = new Dice(id, Color.RED);
            assertEquals(id, dice.getId());
        }
    }
}