package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class WindowView extends GridPane {

    private final Label label;
    private final CellView[][] windowDices;

    public WindowView(Constraint[][] constraints) {
        label = new Label();
        windowDices = new CellView[4][5];
        for (int i = 0; i < constraints.length; i++)
            for (int j = 0; j < constraints[0].length; j++)
                windowDices[i][j] = new CellView(i, j, constraints[i][j]);
        setGridLinesVisible(true);
        createGrid();
    }

    private void createGrid() {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                add(windowDices[i][j], j, i);
    }

    public void setWindowDiceListener(EventHandler<MouseEvent> cellClickEventHandler) {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                windowDices[i][j].setCellListener(cellClickEventHandler);
    }


    public void removeMistakenDice(int row, int col){
        windowDices[row][col].removeMistakenDice();
    }

    void setClickDisabled(boolean isDisabled) {
        for (int i = 0; i < windowDices.length; i++)
            for (int j = 0; j < windowDices[0].length; j++)
                windowDices[i][j].setDisable(isDisabled);
    }

    public void setDice(Dice dice, Position position) {
        windowDices[position.getRow()][position.getCol()].setImageCell(
                new DiceView(Constraint.getColorConstraint(dice.getColor()),
                             Constraint.getValueConstraint(dice.getValue()),
                             dice.getId()));
    }
}