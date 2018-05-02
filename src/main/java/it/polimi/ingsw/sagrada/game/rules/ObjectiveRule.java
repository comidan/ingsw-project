package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.cells.Cell;

import java.util.function.Function;

/**
 * 
 */
public class ObjectiveRule extends Rule {

	private int value;

	/**
	 * @param function
	 */
	private ObjectiveRule(final Function function) {
		// TODO implement here
	}

	/**
	 * @param cells - window to be rule-checked
	 * @return
	 */
	public int checkRule(Cell[][] cells) {
		// TODO implement here
		return 0;
	}

}