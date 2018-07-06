package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Class DicePrev.
 *
 * @author Valentina
 */

public class DicePrev extends ImageView{

    private static final String DICE_PREVIEW_ROOT_PATH = "/images/DicePreviews/";

    /**
     * the value corresponding to the image
     */
    int value;

    /**
     * Instantiates a new Dice Preview.
     *
     * @param value
     */
    DicePrev(int value){
        this.value = value;
        setImage(new Image(DiceView.class.getResourceAsStream(DICE_PREVIEW_ROOT_PATH + Integer.toString(value) + ".jpg"), 50, 50, true, false));
        System.out.println(Integer.toString(value));
    }

    /**
     * gets the value corresponding to the image
     *
     * @return value
     */
    public int getValue(){
        return value;
    }


}
