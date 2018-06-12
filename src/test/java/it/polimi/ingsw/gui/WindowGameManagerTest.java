package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.windows.WindowGameManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WindowGameManagerTest {

    @Test
    public void testWindowGameManager() {
        WindowGameManager windowGameManager = new WindowGameManager();
        windowGameManager.addWindow(0, WindowSide.FRONT);
        Constraint[][] windowConstraints = {{Constraint.YELLOW, Constraint.BLUE, Constraint.WHITE, Constraint.WHITE, Constraint.ONE},
                                            {Constraint.GREEN, Constraint.WHITE, Constraint.FIVE, Constraint.WHITE, Constraint.FOUR},
                                            {Constraint.THREE, Constraint.WHITE, Constraint.RED, Constraint.WHITE, Constraint.GREEN},
                                            {Constraint.TWO, Constraint.WHITE, Constraint.WHITE, Constraint.BLUE, Constraint.YELLOW}};
        Constraint[][] windowConstraintsCheck = windowGameManager.getWindows().get(0);
        assertEquals(windowConstraints, windowConstraintsCheck);
    }
}
