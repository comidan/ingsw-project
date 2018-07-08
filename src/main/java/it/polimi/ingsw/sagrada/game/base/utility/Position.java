package it.polimi.ingsw.sagrada.game.base.utility;

import java.io.Serializable;
import java.util.Objects;



/**
 * Position class containing coordinates data based on the window matrix coordinate system.
 */
public class Position implements Serializable {

    /** The row. */
    private int row;

    /** The col. */
    private int col;

    /**
     * Instantiates a new position.
     *
     * @param row the row
     * @param col the col
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
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

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position))
            return false;
        Position position = (Position) obj;
        return row == position.getRow() && col == position.getCol();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}