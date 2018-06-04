package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Dice {

    private static final int MAX_VALUE = 6;
    private int value;
    private Colors color;
    private int id;


    public Dice(int id, Colors color) {
        this.color = color;
        this.id = id;
    }

    /**
     * @return dice color
     */
    public Colors getColor() {
        return this.color;
    }

    /**
     * @return dice number value
     */
    public int getValue() {
        return this.value;
    }

    public int getId() {
        return this.id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void roll() {
        value = ThreadLocalRandom.current().nextInt(1,MAX_VALUE + 1);
    }
}