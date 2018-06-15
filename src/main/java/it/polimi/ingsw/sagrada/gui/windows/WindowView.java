package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.components.CellView;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


/**
 * The Class WindowView.
 */
public class WindowView extends GridPane {

    /** The label. */
    private final Label label;
    
    /** The window dices. */
    private final CellView[][] windowDices;

    /**
     * Instantiates a new window view.
     *
     * @param constraints the constraints
     */
    public WindowView(Constraint[][] constraints) {
        label = new Label();
        windowDices = new CellView[4][5];
        for (int i = 0; i < constraints.length; i++)
            for (int j = 0; j < constraints[0].length; j++)
                windowDices[i][j] = new CellView(i, j, constraints[i][j]);
        setGridLinesVisible(true);
        createGrid();
    }

    /**
     * Creates the grid.
     */
    private void createGrid() {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                add(windowDices[i][j], j, i);
    }

    /**
     * Sets the window dice listener.
     *
     * @param cellClickEventHandler the new window dice listener
     */
    public void setWindowDiceListener(EventHandler<MouseEvent> cellClickEventHandler) {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                windowDices[i][j].setCellListener(cellClickEventHandler);
    }


    /**
     * Removes the mistaken dice.
     *
     * @param row the row
     * @param col the col
     */
    public void removeMistakenDice(int row, int col){
        windowDices[row][col].removeMistakenDice();
    }

    /**
     * Sets the click disabled.
     *
     * @param isDisabled the new click disabled
     */
    void setClickDisabled(boolean isDisabled) {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                windowDices[i][j].setDisable(isDisabled);
    }

    /**
     * Sets the dice.
     *
     * @param dice the dice
     * @param position the position
     */
    public void setDice(Dice dice, Position position) {
        windowDices[position.getRow()][position.getCol()].setImageCell(
                new DiceView(Constraint.getColorConstraint(dice.getColor()),
                             Constraint.getValueConstraint(dice.getValue()),
                             dice.getId()));
    }
}