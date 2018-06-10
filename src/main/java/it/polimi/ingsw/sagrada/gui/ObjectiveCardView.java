package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

class ObjectiveCardView extends ImageView {

    private static final String OBJECTIVE_IMAGE_ROOT_PATH = "src/main/resources/images/ObjectiveImages/";
    private int id;

    ObjectiveCardView(int id) {
        Resizer resizer = new Resizer();
        this.id = id;
        this.setImage(new Image(new File(OBJECTIVE_IMAGE_ROOT_PATH + "publicObjective" + Integer.toString(id) + ".jpg").toURI().toString(), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));
    }

    int getObjectiveId() {
        return id;
    }
}
