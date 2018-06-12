package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.windows.WindowImage;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class WindowImageTest {

    @Test
    public void testWindowImage() {
        String BASE_PATH = "/images/window_images/window";
        InputStream pathFront =  WindowImageTest.class.getResourceAsStream(BASE_PATH + "0Front.jpg");
        WindowImage windowImage = new WindowImage(pathFront, 0, WindowSide.FRONT);
        assertEquals(0, windowImage.getWindowId());
        assertEquals(WindowSide.FRONT, windowImage.getSide());
    }
}
