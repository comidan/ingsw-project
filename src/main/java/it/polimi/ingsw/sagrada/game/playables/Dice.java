package it.polimi.ingsw.sagrada.game.playables;

import java.awt.*;

/**
 *
 */
public class Dice {

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


}