package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class TokenView extends ImageView {
    private static final String TOKEN_IMAGE_ROOT_PATH = "src/main/resources/images/";
    private Resizer resizer;

    public TokenView(){
        this.resizer = new Resizer();
        this.setImage(new Image(new File(TOKEN_IMAGE_ROOT_PATH + "token" + ".png").toURI().toString(), resizer.getWidthPixel(5), resizer.getHeightPixel(5), true, false));

    }

}
