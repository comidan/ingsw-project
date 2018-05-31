package it.polimi.ingsw.sagrada.gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class CellView extends ImageView {

    private static final String DICE_IMAGE_ROOT_PATH = "src/main/resources/images/DiceImages/";
    private int row;
    private int col;
    private int diceId;
    private Constraint constraint;
    private ClickHandler clickHandler;
    private ClickedObject clickedObject; // to initialize!

    public CellView(int row, int col, Constraint constraint, ClickedObject clickedObject) {
        this.row = row;
        this.col = col;
        this.constraint = constraint;
        setImage(new Image(new File(DICE_IMAGE_ROOT_PATH + Constraint.getConstraintFileName(constraint)).toURI().toString(), 60, 60, true, false));
        this.clickHandler = ClickHandler.getDiceButtonController(clickedObject);
    }

    public void setCellListener(){
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setImage(clickHandler.clickCallbackCell());
            }
        });

    }

    public void setImage(DiceView diceView){

        Constraint color = diceView.getColor();
        Constraint value = diceView.getValue();
        setImage(new Image(new File(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(color, value)).toURI().toString(), 60, 60, true, false));
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
