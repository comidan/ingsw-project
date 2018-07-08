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
 * @author Valentina, Daniele
 */

public class CellView extends StackPane {

    /** The path constant DICE_IMAGE_ROOT_PATH. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";

    /** The row the cell is on. */
    private int row;

    /** The column the cell is on. */
    private int col;

    /**  The id of the dice contained in the cell. */
    private int diceId;

    /**  The flag to show if the cell is occupied. */
    private boolean occupied;

    /** The cell constraint (value or color or blank). */
    private Image cellConstraint;

    /**  The cell image. */
    private ImageView imageView;

    /**  the diceview corresponding to the dice in the cell (if present). */
    private DiceView diceView;

    /**
     * Instantiates a new cell view.
     *
     * @param row the row
     * @param col the column
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
     * Sets the cell listener to handle dice dropped on cell.
     *
     * @param cellDragOver the cell drag over
     * @param cellDragDone the cell drag done
     */
    public void setCellListener(EventHandler<DragEvent> cellDragOver,EventHandler<DragEvent>  cellDragDone){
        setOnDragOver(cellDragOver);
        setOnDragDropped(cellDragDone);

    }

    /**
     * Removes the dice placed mistakenly.
     */
    public void removeMistakenDice(){
        getChildren().removeAll(diceView);
        occupied = false;

    }

    /**
     * Sets the dice image in the cell.
     *
     * @param diceView the new image cell
     */
    public void setImageCell(DiceView diceView) {
        this.diceView = new DiceView(diceView.getColor(), diceView.getValue(), diceView.getDiceID());
        setDiceId(diceView.getDiceID());
        this.getChildren().add(this.diceView);
        occupied = true;
    }

    /**
     * Gets the diceview contained in the cell.
     *
     * @return the diceView
     */
    public DiceView getDiceView(){
        return diceView;
    }
    /**
     * Checks if cell is occupied.
     *
     * @return true, if cell is occupied
     */
    public boolean isOccupied(){
        return occupied;
    }

    /**
     * Gets the cell row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the cell column.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the id of the dice in the cell.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
    }

    /**
     * Sets the id of the dice in the cell.
     *
     * @param diceId the new dice id
     */
    private void setDiceId(int diceId) {
        this.diceId = diceId;
    }
}
