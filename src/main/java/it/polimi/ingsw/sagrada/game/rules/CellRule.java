package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.State;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.Color;
import java.util.function.Function;

/**
 * 
 */
public class CellRule extends Rule<Dice, Boolean> {

	private int valueConstraint;
	private Color colorConstraint;
	private State state;
	private Function<Dice, Boolean> function;

	/**
	 * @param function - rule checker function
	 * @param colorConstraint - color constraint
	 */
	CellRule(final Function function, Color colorConstraint) {
		this.function = function;
		this.colorConstraint = colorConstraint;
		valueConstraint = 0;
	}

	/**
	 * @param function - rule checker function
	 * @param valueConstraint - value constraint
	 */
	CellRule(final Function function, int valueConstraint) {
		this.function = function;
		this.valueConstraint = valueConstraint;
		colorConstraint = null;
	}

	/**
	 * @return builder object
	 */
	public static CellBuilder<CellRule> builder() {
		return new CellBuilder<>();
	}

	/**
	 * @param state - active or inactive
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @param dice - check if dice can be positioned in this current cell
	 * @return true if dice can be positioned in this current cell
	 */
	@Override
	Boolean checkRule(Dice dice) {
		return function.apply(dice);
	}

	/**
	 * @return value cnstraint
	 */
	public int getValueConstraint() {
		return valueConstraint;
	}

	/**
	 * @return color constraint
	 */
	public Color getColorConstraint() {
		return colorConstraint;
	}
}