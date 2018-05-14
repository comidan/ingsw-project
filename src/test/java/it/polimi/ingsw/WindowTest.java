package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.WindowController;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void testWindowCreation() {
        WindowController windowController =new WindowController();
        Window window= windowController.generateWindow(0, WindowSide.FRONT);

        Dice dice = new Dice(3, Colors.RED);
        assertTrue(window.setCell(dice, 1, 1));
        assertFalse(window.setCell(dice, 1, 1));
        System.out.println(window.toString());

    }

    @Test
    public void testAllWindowsJSON() {
        WindowController windowController =new WindowController();
        Window window;
        int counter=0;

        while(windowController.isWindowsLeft()) {
            List<Integer> id = windowController.dealWindowId();
            for(int i:id) {
                window= windowController.generateWindow(i, WindowSide.FRONT);
                System.out.println(window.toString());
                counter++;
                window= windowController.generateWindow(i, WindowSide.REAR);
                System.out.println(window.toString());
                counter++;
            }
        }
        assertEquals(24, counter);
    }
}
