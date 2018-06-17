package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import org.junit.Test;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GUIManagerTest {

    /** DecimalFormat usage due to known issue revolving floating point computing approximation described in IEEE 754 */

    @Test
    public void testGUIManager() {
        double RATIO = 0.76;
        double RELATIVE_DIMENSION = 0.8;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getWindowHeight()).replace(',', '.')), 0);
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION * RATIO).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getWindowWidth()).replace(',', '.')), 0);
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(gd.getDisplayMode().getHeight() * 0.9).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getScreenHeight()).replace(',', '.')), 0);
        assertEquals(gd.getDisplayMode().getWidth(), GUIManager.getScreenWidth(), 0);
        int x = new Random().nextInt() % 100;
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(x * GUIManager.getWindowHeight() / 100).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getHeightPixel(x)).replace(',', '.')), 0);
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(x * GUIManager.getWindowWidth() / 100).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getWidthPixel(x)).replace(',', '.')), 0);
        assertEquals(Double.parseDouble(new DecimalFormat("#.00").format(((double)x) * gd.getDisplayMode().getHeight() * 0.9 / 1080).replace(',', '.')), Double.parseDouble(new DecimalFormat("#.00").format(GUIManager.getResizedFont(x)).replace(',', '.')), 0);
    }
}
