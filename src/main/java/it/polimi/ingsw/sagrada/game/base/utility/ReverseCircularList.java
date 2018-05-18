package it.polimi.ingsw.sagrada.game.base.utility;

import java.util.ArrayList;

public class ReverseCircularList<E> extends ArrayList<E> {
    private int offset = 0;

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }


    /*This method reads the whole array from beginning to the end (even starting from an index different from 0)
    and then from end to the beginning.
    During the first iteration (index/size is even) the array is read from beginning to end, during the second
    (index/size is odd) it is read backwards.
    Offset is used to set the starting index.
    */
    @Override
    public E get(int index) {
        int n = size();
        if (((index / n) % 2) == 0)
            return super.get((index + offset) % n);
        else
            return super.get((index - (2 * (index % n) + 1) + offset) % n);
    }

}