package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ObjectiveCardView extends ImageView {

    private static final String TOOL_IMAGE_ROOT_PATH = "src/main/resources/images/ToolImages/";
    private int id;
    private Resizer resizer;

    public ObjectiveCardView(int id) {
        this.resizer = new Resizer();
        this.id = id;
        this.setImage(new Image(new File(TOOL_IMAGE_ROOT_PATH + Integer.toString(id) + ".jpg").toURI().toString(), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));

    }

}
