package it.polimi.ingsw.sagrada.game.playables;

import java.awt.*;

/**
 * 
 */
public class Dice {

	private int value;
	private Color color;


	public Dice(int value, Color color) throws DiceExcpetion {
        if(value < 1 || value > 6)
            throw new DiceExcpetion("Dice value not allowed");
        this.value = value;
        this.color = color;
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

}