package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class PrivateObjectiveView extends ImageView {

    private static final String OBJECTIVE_IMAGE_ROOT_PATH = "/images/ObjectiveImages/";
    private int id;
    private Resizer resizer;

    public PrivateObjectiveView(int id) {
        this.resizer = new Resizer();
        this.id = id;
        this.setImage(new Image(PrivateObjectiveView.class.getResourceAsStream(OBJECTIVE_IMAGE_ROOT_PATH + "privateObjective" + Integer.toString(id) + ".jpg"), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));

    }

}
