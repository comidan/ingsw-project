package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CellView extends ImageView {

    private static final String DICE_IMAGE_ROOT_PATH = "src/main/resources/DiceImages/";
    private int row;
    private int col;
    private int diceId;
    private Constraint constraint;

    public CellView(int row, int col, Constraint constraint) {
        this.row = row;
        this.col = col;
        this.constraint = constraint;
        setImage(new Image(new File(DICE_IMAGE_ROOT_PATH + Constraint.getConstraintFileName(constraint)).toURI().toString()));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDiceId() {
        return diceId;
    }

    public void setDiceId(int diceId) {
        this.diceId = diceId;
    }
}
