package it.polimi.ingsw;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.WindowParser;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void testWindowCreation() {
        WindowParser windowParser=new WindowParser();
        Window window=windowParser.generateWindow(0, WindowSide.FRONT);

        Dice dice = new Dice(3, Colors.RED);
        assertTrue(window.setCell(dice, 1, 1));
        assertFalse(window.setCell(dice, 1, 1));
        System.out.println(window.toString());

    }

    @Test
    public void testAllWindowsJSON() {
        WindowParser windowParser=new WindowParser();
        Window window;
        int counter=0;

        while(windowParser.isWindowsLeft()) {
            List<Integer> id = windowParser.dealWindowId();
            for(int i:id) {
                window=windowParser.generateWindow(i, WindowSide.FRONT);
                System.out.println(window.toString());
                counter++;
                window=windowParser.generateWindow(i, WindowSide.REAR);
                System.out.println(window.toString());
                counter++;
            }
        }
        assertEquals(24, counter);
    }
}
