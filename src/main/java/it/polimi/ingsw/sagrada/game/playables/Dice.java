package it.polimi.ingsw.sagrada.game.playables;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Dice {

    private static final int MAX_VALUE = 6;
    private int value;
    private Color color;
    private int id;


    public Dice(int id, Color color) {
        this.color = color;
        this.id = id;
    }

    /**
     * @return dice color
     */
    public Color getColor() {
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
        value = ThreadLocalRandom.current().nextInt(1,MAX_VALUE+1);
    }
}