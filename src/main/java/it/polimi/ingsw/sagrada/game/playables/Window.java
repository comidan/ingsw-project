


package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;

import java.awt.*;
import java.util.List;

/**
 *
 */
public class Window {

    private Cell[][] cellMatrix;
    private List<Token> tokens;


    public Window() {

        List<Color> colorList = Colors.getColorList();
        this.cellMatrix = new Cell[4][5];
        for (int i = 0; i < cellMatrix.length - 2; i++) {
            for (int j = 0; j < cellMatrix[0].length; j++) {
                cellMatrix[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
            } //temporary colors for testing
        }

    }

    /**
     * @return
     */
    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }

    /**
     * @param dice - dice to be added
     * @param x    - window x-position
     * @param y    - window y-position
     * @return
     */
    public boolean setCell(Dice dice, int x, int y) {
        if (cellMatrix[x][y].isOccupied()) return false;
        cellMatrix[x][y].setDice(dice);
        return true;
    }

    public int getTokenNumber() {
        return tokens.size();
    }


}
