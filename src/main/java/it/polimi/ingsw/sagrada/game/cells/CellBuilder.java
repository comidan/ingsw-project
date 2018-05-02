package it.polimi.ingsw.sagrada.game.cells;

import it.polimi.ingsw.sagrada.game.base.Builder;

import java.awt.*;
import java.util.function.Function;

/**
 * 
 */
public class CellBuilder<T> extends Builder {

	/**
	 * @param builder - building object's constructor
	 */
	public CellBuilder(final Function builder) {
		// TODO implement here
	}

	/**
	 * @param color - color constraint
	 * @return this CellBuilder with an updated color rule
	 */
	public CellBuilder<T> setColorConstraint(Color color) {
		// TODO implement here
		return null;
	}

	/**
	 * @param value - number value constraint
	 * @return this CellBuilder with an updated number value rule
	 */
	public CellBuilder<T> setNumberConstraint(int value) {
		// TODO implement here
		return null;
	}

}