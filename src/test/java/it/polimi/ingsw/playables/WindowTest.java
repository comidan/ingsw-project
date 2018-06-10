package it.polimi.ingsw.playables;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.WindowManager;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WindowTest {

    @Test
    public void testWindowCreation() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Mottola"));
        players.add(new Player("IngConti"));
        players.add(new Player("Izzo"));
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
        List<Player> player = new ArrayList<>();
        player.add(new Player(0));
        player.add(new Player(1));
        player.add(new Player(2));
        DynamicRouter dynamicRouter = new MessageDispatcher();
        GameManager gameManager = new GameManager(player, dynamicRouter);

        WindowManager windowManager = new WindowManager(gameManager.getDispatchReference(), dynamicRouter);
        Window windows;
        int counter = 0;

        while (windowManager.isWindowsLeft()) {
            List<Integer> id = windowManager.dealWindowId(0);
            for (int i : id) {
                windows = windowManager.generateWindow(i, WindowSide.FRONT);
                System.out.println(windows.toString());
                counter++;
                windows = windowManager.generateWindow(i, WindowSide.REAR);
                System.out.println(windows.toString());
                counter++;
            }
        }
        assertEquals(24, counter);
    }*/
}
