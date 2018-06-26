package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.StackPane;


/**
 * The Class CellView.
 */
public class CellView extends StackPane {

    /** The Constant DICE_IMAGE_ROOT_PATH. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";

    /** The row. */
    private int row;

    /** The col. */
    private int col;

    /** The dice id. */
    private int diceId;

    /** The occupied. */
    private boolean occupied;

    /** The cell constraint. */
    private Image cellConstraint;

    private ImageView imageView;

    private DiceView diceView;

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
        this.occupied = false;
        cellConstraint = new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getConstraintFileName(constraint)), GUIManager.getGameWidthPixel(8.3), GUIManager.getGameHeightPixel(8.4), true, false);
        imageView = new ImageView();
        imageView.setImage(cellConstraint);
        this.getChildren().add(imageView);
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
        getChildren().removeAll(diceView);
        occupied = false;

    }

    /**
     * Sets the image cell.
     *
     * @param diceView the new image cell
     */
    public void setImageCell(DiceView diceView) {
        this.diceView = new DiceView(diceView.getColor(), diceView.getValue(), diceId);
        setDiceId(diceView.getDiceID());
        this.getChildren().add(this.diceView);
        occupied = true;
    }

    public DiceView getDiceView(){
        return diceView;
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
        diceView.setImage(null);
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
    private void setDiceId(int diceId) {
        this.diceId = diceId;
    }
}
