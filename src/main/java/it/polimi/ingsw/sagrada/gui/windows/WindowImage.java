package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import javafx.scene.image.ImageView;

public class WindowImage extends ImageView {
    private int windowId;
    private WindowSide side;

    public WindowImage(String path, int windowId, WindowSide side) {
        super(path);
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
