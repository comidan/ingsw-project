package it.polimi.ingsw;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.WindowParser;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void TestWindowCreation() {
        WindowParser windowParser=WindowParser.getInstance();
        List<Window> windowsCard=windowParser.generateWindowCard();

        for(Window w:windowsCard) {
            Dice dice = new Dice(3, Colors.RED);
            assertTrue(w.setCell(dice, 1, 1));
            assertFalse(w.setCell(dice, 1, 1));
            System.out.println(w.toString());
        }
    }

}
