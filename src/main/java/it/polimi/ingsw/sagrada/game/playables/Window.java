package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;

import java.util.List;

/**
 *  Window model class representing a certain window including its cell/dice matrix
 */
public class Window {

    private String name;
    private Cell[][] cellMatrix;
    private int tokens;
    private int id;
    private WindowSide side;

    /**
     * Instantiates a new window.
     *
     * @param name the name
     * @param cellMatrix the cell matrix
     * @param tokens the tokens
     * @param id the id
     * @param side the side
     */
    public Window(String name, Cell[][] cellMatrix, int tokens, int id, WindowSide side) {
        this.name = name;
        this.cellMatrix = cellMatrix;
        this.tokens = tokens;
        this.id = id;
        this.side = side;
    }

    /**
     * Gets the cell matrix.
     *
     * @return the cell matrix
     */
    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }

    /**
     * Sets the cell.
     *
     * @param dice - dice to be added
     * @param y    - windows y-position
     * @param x    - windows x-position
     * @return true, if successful
     */
    public boolean setCell(Dice dice, int y, int x) {
        if (cellMatrix[y][x].isOccupied()) return false;
        cellMatrix[y][x].setDice(dice);
        return true;
    }

    /**
     * Reset cell.
     *
     * @param y the y
     * @param x the x
     */
    public void resetCell(int y, int x) {
        cellMatrix[y][x].removeCurrentDice();
    }

    public Position getPositionFromId(int diceId) {
        for(int y=0; y<4; y++) {
            for(int x=0; x<5; x++) {
                Dice d = cellMatrix[y][x].getCurrentDice();
                if(d!=null) {
                    if(d.getId() == diceId) {
                        return new Position(y, x);
                    }
                }
            }
        }
        return null;
    }

    public Dice getDicefromId(int diceId) {
        for(int y=0; y<4; y++) {
            for(int x=0; x<5; x++) {
                Dice d = cellMatrix[y][x].getCurrentDice();
                if(d!=null) {
                    if(d.getId() == diceId) {
                        return d;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the token number.
     *
     * @return the token number
     */
    public int getTokenNumber() { return tokens; }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the side.
     *
     * @return the side
     */
    public WindowSide getSide() {
        return side;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
