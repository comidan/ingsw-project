package it.polimi.ingsw.sagrada.game.base.utility;

import java.io.Serializable;


/**
 * The Class Pair.
 *
 * @param <T> the generic type
 * @param <U> the generic type
 */
public class Pair<T, U> implements Serializable {

    /** The t. */
    private final T t;

    /** The u. */
    private final U u;

    /**
     * Instantiates a new pair.
     *
     * @param t the t
     * @param u the u
     */
    public Pair(T t, U u) {
        this.t= t;
        this.u= u;
    }

    /**
     * Gets the first entry.
     *
     * @return the first entry
     */
    public T getFirstEntry() {
        return t;
    }

    /**
     * Gets the second entry.
     *
     * @return the second entry
     */
    public U getSecondEntry() {
        return u;
    }
}
