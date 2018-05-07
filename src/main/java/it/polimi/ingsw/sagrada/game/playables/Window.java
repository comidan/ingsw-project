package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Cell;

import java.util.*;

/**
 *
 */
public class Window {

    private Cell[][] cellMatrix;
    private List<Token> tokens;


    /**
     * Default constructor
     */
    public Window() {
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
        // TODO implement here
        return false;
    }

}