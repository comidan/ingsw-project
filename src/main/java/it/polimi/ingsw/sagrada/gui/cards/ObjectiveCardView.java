package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


/**
 * The Class ObjectiveCardView.
 */
class ObjectiveCardView extends ImageView {

    /** The Constant OBJECTIVE_IMAGE_ROOT_PATH. */
    private static final String OBJECTIVE_IMAGE_ROOT_PATH = "/images/ObjectiveImages/";
    
    /** The id. */
    private int id;

    /**
     * Instantiates a new objective card view.
     *
     * @param id the id
     */
    ObjectiveCardView(int id) {
        Resizer resizer = new Resizer();
        this.id = id;
        this.setImage(new Image(ObjectiveCardView.class.getResourceAsStream(OBJECTIVE_IMAGE_ROOT_PATH + "publicObjective" + Integer.toString(id) + ".jpg"), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));
    }

    /**
     * Gets the objective id.
     *
     * @return the objective id
     */
    int getObjectiveId() {
        return id;
    }
}
