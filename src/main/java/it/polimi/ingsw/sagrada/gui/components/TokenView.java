package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class TokenView extends ImageView {
    private static final String TOKEN_IMAGE_ROOT_PATH = "/images/";
    private Resizer resizer;

    public TokenView(){
        this.resizer = new Resizer();
        this.setImage(new Image(TokenView.class.getResourceAsStream(TOKEN_IMAGE_ROOT_PATH + "token.png"), resizer.getWidthPixel(5), resizer.getHeightPixel(5), true, false));

    }

}