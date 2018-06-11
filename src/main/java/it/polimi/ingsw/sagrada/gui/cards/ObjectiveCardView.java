package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

class ObjectiveCardView extends ImageView {

    private static final String OBJECTIVE_IMAGE_ROOT_PATH = "/images/ObjectiveImages/";
    private int id;

    ObjectiveCardView(int id) {
        Resizer resizer = new Resizer();
        this.id = id;
        this.setImage(new Image(ObjectiveCardView.class.getResourceAsStream(OBJECTIVE_IMAGE_ROOT_PATH + "publicObjective" + Integer.toString(id) + ".jpg"), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));
    }

    int getObjectiveId() {
        return id;
    }
}
