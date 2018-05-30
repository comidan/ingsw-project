package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.gui.test.WindowModelInterface;

import java.util.List;

/**
 *
 */
public class Window implements WindowModelInterface {

    private String name;
    private Cell[][] cellMatrix;
    private List<Token> tokens;


    public Window(String name, Cell[][] cellMatrix, List<Token> tokens) {
        this.name = name;
        this.cellMatrix = cellMatrix;
        this.tokens = tokens;
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

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("");

        output.append(name);
        output.append("\n\n");
        for (int i = 0; i < cellMatrix.length; i++) { //row
            for (int j = 0; j < cellMatrix[0].length; j++) { //column
                if (cellMatrix[i][j].getCellRule() != null) {
                    Colors c = cellMatrix[i][j].getCellRule().getColorConstraint();
                    if (c != null) output.append(c.toStringSingleLetter());
                    String s = String.valueOf(cellMatrix[i][j].getCellRule().getValueConstraint());
                    if (!s.equals("0")) output.append(s);
                    output.append("|");
                } else {
                    output.append(" |");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }
}
