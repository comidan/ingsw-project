package it.polimi.ingsw.sagrada.game.cells;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.*;
import java.util.function.Function;

/**
 * 
 */
public class CellBuilder<T extends CellRule> extends Builder {

	private Color colorConstraint;
	private Integer valueConstraint;
	private Function<Dice, Boolean> function;

	@Override
	public CellRule build() {
		if(function == null)
			return null;
		if(valueConstraint != 0)
			return new CellRule(function, valueConstraint);
		return new CellRule(function, colorConstraint);
	}

	/**
	 * @param color - color constraint
	 * @return this CellBuilder with an updated color rule
	 */
	public CellBuilder<T> setColorConstraint(final Color color) {
		function = dice -> color.equals(dice.getColor());
		colorConstraint = color;
		valueConstraint = 0;
		return this;
	}

	/**
	 * @param value - number value constraint
	 * @return this CellBuilder with an updated number value rule
	 */
	public CellBuilder<T> setNumberConstraint(int value) throws Exception{
		if(value < 1 || value > 6)
			throw new Exception("Dice value constraint not allowed");
		function = dice -> value == dice.getValue();
		valueConstraint = value;
		colorConstraint = null;
		return this;
	}

}