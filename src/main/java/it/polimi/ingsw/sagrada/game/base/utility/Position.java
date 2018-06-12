package it.polimi.ingsw.sagrada.game.base.utility;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position))
            return false;
        Position position = (Position) obj;
        return row == position.getRow() && col == position.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}