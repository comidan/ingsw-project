package it.polimi.ingsw.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.WindowManager;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    public void testWindowCreation() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(1));
        players.add(new Player(2));
        DynamicRouter dynamicRouter = new MessageDispatcher();
        GameManager gameManager = new GameManager(players, dynamicRouter);

        WindowManager windowManager = new WindowManager(gameManager.getDispatchReference(), dynamicRouter);
        Window window = windowManager.generateWindow(0, WindowSide.FRONT);

        Dice dice = new Dice(3, Colors.RED);
        assertTrue(window.setCell(dice, 1, 1));
        assertFalse(window.setCell(dice, 1, 1));
        System.out.println(window.toString());

    }

    /*@Test //dubbia utilità, se ne deve discutere
    public void testAllWindowsJSON() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(1));
        players.add(new Player(2));
        DynamicRouter dynamicRouter = new MessageDispatcher();
        GameManager gameManager = new GameManager(players, dynamicRouter);

        WindowManager windowManager = new WindowManager(gameManager.getDispatchReference(), dynamicRouter);
        Window window;
        int counter = 0;

        while (windowManager.isWindowsLeft()) {
            List<Integer> id = windowManager.dealWindowId(0);
            for (int i : id) {
                window = windowManager.generateWindow(i, WindowSide.FRONT);
                System.out.println(window.toString());
                counter++;
                window = windowManager.generateWindow(i, WindowSide.REAR);
                System.out.println(window.toString());
                counter++;
            }
        }
        assertEquals(24, counter);
    }*/
}
