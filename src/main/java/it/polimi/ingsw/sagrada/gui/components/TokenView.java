package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


/**
 * The Class TokenView.
 */
public class TokenView extends ImageView {
    
    /** The Constant TOKEN_IMAGE_ROOT_PATH. */
    private static final String TOKEN_IMAGE_ROOT_PATH = "/images/";
    
    /** The GUIManager. */
    private GUIManager guiManager;

    /**
     * Instantiates a new token view.
     */
    public TokenView(){
        this.guiManager = new GUIManager();
        this.setImage(new Image(TokenView.class.getResourceAsStream(TOKEN_IMAGE_ROOT_PATH + "token.png"), guiManager.getFullWidthPixel(6.5), guiManager.getFullHeightPixel(6.5), true, false));

    }

}
