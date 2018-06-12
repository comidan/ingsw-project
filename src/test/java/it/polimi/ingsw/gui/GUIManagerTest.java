package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GUIManagerTest {

    @Test
    public void testGUIManager() {
        double RATIO = 0.76;
        double RELATIVE_DIMENSION = 0.8;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        assertEquals(gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION, GUIManager.getWindowHeight(), 0);
        assertEquals(gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION * RATIO, GUIManager.getWindowWidth(), 0);
        assertEquals(gd.getDisplayMode().getHeight(), GUIManager.getScreenHeight(), 0);
        assertEquals(gd.getDisplayMode().getWidth(), GUIManager.getScreenWidth(), 0);
        int x = new Random().nextInt() % 100;
        assertEquals(x * GUIManager.getWindowHeight() / 100, GUIManager.getHeightPixel(x), 0);
        assertEquals(x * GUIManager.getWindowWidth() / 100, GUIManager.getWidthPixel(x), 0);
        assertEquals(((double)x) * gd.getDisplayMode().getHeight() / 1080, GUIManager.getResizedFont(x), 0);
    }
}
