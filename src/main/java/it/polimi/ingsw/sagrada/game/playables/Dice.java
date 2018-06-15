package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The Class Dice.
 */
public class Dice implements Serializable {

    /** The Constant MAX_VALUE. */
    private static final int MAX_VALUE = 6;
    
    /** The value. */
    private int value;
    
    /** The color. */
    private Colors color;
    
    /** The id. */
    private int id;


    /**
     * Instantiates a new dice.
     *
     * @param id the id
     * @param color the color
     */
    public Dice(int id, Colors color) {
        this.color = color;
        this.id = id;
    }

    /**
     * Gets the color.
     *
     * @return dice color
     */
    public Colors getColor() {
        return this.color;
    }

    /**
     * Gets the value.
     *
     * @return dice number value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Roll.
     */
    public void roll() {
        value = ThreadLocalRandom.current().nextInt(1,MAX_VALUE + 1);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Dice))
            return false;
        Dice dice = (Dice) obj;
        return id == dice.getId();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, value, color);
    }
}