package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The Class TokenView.
 */
public class TokenView extends ImageView {
    
    /** The path constant TOKEN_IMAGE_ROOT_PATH. */
    private static final String TOKEN_IMAGE_ROOT_PATH = "/images/gameGuiImages/";

    /**
     * Instantiates a new token view.
     */
    public TokenView(){
        this.setImage(new Image(TokenView.class.getResourceAsStream(TOKEN_IMAGE_ROOT_PATH + "token.png"), GUIManager.getGameWidthPixel(6), GUIManager.getGameHeightPixel(6), true, false));

    }

}
