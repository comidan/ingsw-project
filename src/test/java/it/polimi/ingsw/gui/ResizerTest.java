package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ResizerTest {

    public void testResizer() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Resizer resizer = new Resizer();
        assertEquals(gd.getDisplayMode().getHeight(), resizer.getWindowHeight(), 0);
        assertEquals(gd.getDisplayMode().getWidth(), resizer.getWindowWidth(), 0);
        int x = new Random().nextInt() % 100;
        assertEquals(((double)x) * resizer.getWindowHeight() / 100, resizer.getHeightPixel(x), 0);
        assertEquals(((double)x) * resizer.getWindowWidth() / 100, resizer.getWidthPixel(x), 0);
    }
}