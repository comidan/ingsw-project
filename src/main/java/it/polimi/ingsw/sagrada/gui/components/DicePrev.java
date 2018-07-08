package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The Class DicePrev.
 */
public class DicePrev extends ImageView{

    /** The Constant DICE_PREVIEW_ROOT_PATH. */
    private static final String DICE_PREVIEW_ROOT_PATH = "/images/DiceImages/";

    /** The value. */
    private int value;

    /**
     * Instantiates a new dice prev.
     *
     * @param value the value
     * @param color the color
     */
    DicePrev(int value, Colors color){
        this.value = value;
        setImage(new Image(DicePrev.class.getResourceAsStream(
                DICE_PREVIEW_ROOT_PATH + Constraint.getDiceFileName(Constraint.getColorConstraint(color), Constraint.getValueConstraint(value))),
                50,
                50,
                true,
                false));
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue(){
        return value;
    }


}
