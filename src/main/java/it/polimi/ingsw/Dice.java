package it.polimi.ingsw;

import java.awt.*;

public class Dice {

    private int value;
    private Color color;

    public Dice(int value, Color color) throws Exception {
        if(value < 1 || value > 6)
            throw new Exception("Dice value not allowed");
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }
}
