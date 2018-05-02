package it.polimi.ingsw.sagrada.game.cells;

import it.polimi.ingsw.sagrada.game.base.State;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.Rule;

import java.awt.*;
import java.util.function.Function;

/**
 * 
 */
public class CellRule extends Rule {


	private int valueConstraint;
	private Color colorConstraint;
	private State active;


	/**
	 * @param function
	 */
	private CellRule(final Function function) {
		// TODO implement here
	}


	/**
	 * @param state - active or inactive
	 */
	public void setState(State state) {
		// TODO implement here
	}

	/**
	 * @param dice - check if dice can be positioned in this current cell
	 * @return true if dice can be positioned in this current cell
	 */
	public boolean checkRule(Dice dice) {
		// TODO implement here
		return false;
	}

}