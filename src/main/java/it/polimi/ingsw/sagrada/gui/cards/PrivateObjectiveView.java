package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;



/**
 * The Class PrivateObjectiveView.
 * @author Valentina
 */

public class PrivateObjectiveView extends ImageView {

    /** The path constant OBJECTIVE_IMAGE_ROOT_PATH. */
    private static final String OBJECTIVE_IMAGE_ROOT_PATH = "/images/ObjectiveImages/";
    
    /** The id. */
    private int id;


    /**
     * Instantiates a new private objective view.
     *
     * @param id the private objective card id
     */
    PrivateObjectiveView(int id) {
        this.id = id;
        this.setImage(new Image(PrivateObjectiveView.class.getResourceAsStream(OBJECTIVE_IMAGE_ROOT_PATH + "privateObjective" + Integer.toString(id) + ".jpg"), GUIManager.getGameWidthPixel(19), GUIManager.getGameHeightPixel(25), true, false));

    }

}
