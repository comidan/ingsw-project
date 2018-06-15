package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.util.List;

/**
 *
 */
public class Window {

    private String name;
    private Cell[][] cellMatrix;
    private List<Token> tokens;
    private int id;
    private WindowSide side;


    public Window(String name, Cell[][] cellMatrix, List<Token> tokens, int id, WindowSide side) {
        this.name = name;
        this.cellMatrix = cellMatrix;
        this.tokens = tokens;
        this.id = id;
        this.side = side;
    }

    /**
     * @return
     */
    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }

    /**
     * @param dice - dice to be added
     * @param x    - windows x-position
     * @param y    - windows y-position
     * @return
     */
    public boolean setCell(Dice dice, int y, int x) {
        if (cellMatrix[y][x].isOccupied()) return false;
        cellMatrix[y][x].setDice(dice);
        return true;
    }

    public void resetCell(int y, int x) {
        cellMatrix[y][x].removeCurrentDice();
    }

    public int getTokenNumber() {
        return tokens.size();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public WindowSide getSide() {
        return side;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

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
