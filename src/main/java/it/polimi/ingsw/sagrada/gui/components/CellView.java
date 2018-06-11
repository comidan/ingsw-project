package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.InputStreamReader;

public class CellView extends ImageView {

    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";
    private int row;
    private int col;
    private int diceId;
    private Constraint constraint;
    private boolean occupied;
    private Image cellConstraint;

    public CellView(int row, int col, Constraint constraint) {
        this.row = row;
        this.col = col;
        this.constraint = constraint;
        this.occupied = false;
        cellConstraint = new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getConstraintFileName(constraint)), 60, 60, true, false);
        setImage(cellConstraint);
    }

    public CellView(){
        ImageView cellImage = new ImageView();
        cellImage.resize(50,50 );
    }

    public void setCellListener(EventHandler<MouseEvent> cellClickEventHandler){
        setOnMouseClicked(cellClickEventHandler);

    }

    public void removeMistakenDice(){
        setImage(cellConstraint);
        occupied = false;

    }

    public void setImageCell(DiceView diceView) {
                Constraint color = diceView.getColor();
                Constraint value = diceView.getValue();
                setImage(new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(color, value)), 60, 60, true, false));
                this.occupied = true;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void removeImage(){
        setImage(null);
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
