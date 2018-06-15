package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.InputStreamReader;


/**
 * The Class CellView.
 */
public class CellView extends ImageView {

    /** The Constant DICE_IMAGE_ROOT_PATH. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";
    
    /** The row. */
    private int row;
    
    /** The col. */
    private int col;
    
    /** The dice id. */
    private int diceId;
    
    /** The constraint. */
    private Constraint constraint;
    
    /** The occupied. */
    private boolean occupied;
    
    /** The cell constraint. */
    private Image cellConstraint;

    /**
     * Instantiates a new cell view.
     *
     * @param row the row
     * @param col the col
     * @param constraint the constraint
     */
    public CellView(int row, int col, Constraint constraint) {
        this.row = row;
        this.col = col;
        this.constraint = constraint;
        this.occupied = false;
        cellConstraint = new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getConstraintFileName(constraint)), 60, 60, true, false);
        setImage(cellConstraint);
    }

    /**
     * Instantiates a new cell view.
     */
    public CellView(){
        ImageView cellImage = new ImageView();
        cellImage.resize(50,50 );
    }

    /**
     * Sets the cell listener.
     *
     * @param cellDragOver, cellDragDone the new cell listener
     */
    public void setCellListener(EventHandler<DragEvent> cellDragOver,EventHandler<DragEvent>  cellDragDone){
        setOnDragOver(cellDragOver);
        setOnDragDropped(cellDragDone);

    }

    /**
     * Removes the mistaken dice.
     */
    public void removeMistakenDice(){
        setImage(cellConstraint);
        occupied = false;

    }

    /**
     * Sets the image cell.
     *
     * @param diceView the new image cell
     */
    public void setImageCell(DiceView diceView) {
                Constraint color = diceView.getColor();
                Constraint value = diceView.getValue();
                setImage(new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(color, value)), 60, 60, true, false));
                occupied = true;
                diceId = diceView.getDiceID();
    }

    /**
     * Checks if is occupied.
     *
     * @return true, if is occupied
     */
    public boolean isOccupied(){
        return occupied;
    }

    /**
     * Removes the image.
     */
    public void removeImage(){
        setImage(null);
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the col.
     *
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the dice id.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
    }

    /**
     * Sets the dice id.
     *
     * @param diceId the new dice id
     */
    public void setDiceId(int diceId) {
        this.diceId = diceId;
    }
}
