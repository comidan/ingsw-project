package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DicePrev extends ImageView{

    private static final String DICE_PREVIEW_ROOT_PATH = "/images/DiceImages/";

    private int value;

    DicePrev(int value, Colors color){
        this.value = value;
        setImage(new Image(DicePrev.class.getResourceAsStream(
                DICE_PREVIEW_ROOT_PATH + Constraint.getDiceFileName(Constraint.getColorConstraint(color), Constraint.getValueConstraint(value))),
                50,
                50,
                true,
                false));
    }

    public int getValue(){
        return value;
    }


}
