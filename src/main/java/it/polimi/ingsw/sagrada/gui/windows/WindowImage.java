package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;



/**
 * The Class WindowImage.
 */
public class WindowImage extends ImageView {
    
    /** The window id. */
    private int windowId;
    
    /** The side. */
    private WindowSide side;

    /**
     * Instantiates a new window image.
     *
     * @param imageStream the image stream
     * @param windowId the window id
     * @param side the side
     */
    public WindowImage(InputStream imageStream, int windowId, WindowSide side) {
        super(new Image(imageStream));
        this.windowId = windowId;
        this.side = side;
    }

    /**
     * Gets the window id.
     *
     * @return the window id
     */
    public int getWindowId() {
        return windowId;
    }

    /**
     * Gets the side.
     *
     * @return the side
     */
    public WindowSide getSide() {
        return side;
    }
}
