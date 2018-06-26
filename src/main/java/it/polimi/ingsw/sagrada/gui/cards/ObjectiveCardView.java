package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



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
        this.id = id;
        this.setImage(new Image(ObjectiveCardView.class.getResourceAsStream(OBJECTIVE_IMAGE_ROOT_PATH + "publicObjective" + Integer.toString(id) + ".jpg"), GUIManager.getGameWidthPixel(19), GUIManager.getGameHeightPixel(25), true, false));
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
