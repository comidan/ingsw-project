package it.polimi.ingsw.sagrada.game.base;

import java.util.ArrayList;

public class ReverseCircularList<E> extends ArrayList<E> {
    private int offset = 0;

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    @Override
    public E get(int index) {
        int n = size();
        if (Math.floor(index / 2.0) % 2 == 0)
            return super.get((index + offset) % n);
        else
            return super.get(((index - (2 * (index % n) + 1) + offset)) % n);
    }

}