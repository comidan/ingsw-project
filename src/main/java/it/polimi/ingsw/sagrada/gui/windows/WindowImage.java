package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class WindowImage extends ImageView {
    private int windowId;
    private WindowSide side;

    public WindowImage(InputStream imageStream, int windowId, WindowSide side) {
        super(new Image(imageStream));
        this.windowId = windowId;
        this.side = side;
    }

    public int getWindowId() {
        return windowId;
    }

    public WindowSide getSide() {
        return side;
    }
}
