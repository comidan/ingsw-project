package it.polimi.ingsw;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.DiceExcpetion;
import it.polimi.ingsw.sagrada.game.playables.Window;
import org.junit.Test;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void TestWindow() throws DiceExcpetion {
        Window window = new Window();
        Colors color = new Colors();
        Dice dice = new Dice(3, color.RED);
        assertTrue(window.setCell(dice, 1, 1));
        assertFalse(window.setCell(dice, 1, 1));

    }


}
