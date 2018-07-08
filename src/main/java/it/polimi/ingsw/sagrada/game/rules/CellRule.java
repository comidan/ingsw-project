package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.function.Function;



/**
 * The Class CellRule.
 */
public class CellRule extends Rule<Dice, Boolean> {

	/** The value constraint. */
	private int valueConstraint;
	
	/** The color constraint. */
	private Colors colorConstraint;
	
	/** The function. */
	private Function<Dice, Boolean> function;

	/**
	 * Instantiates a new cell rule.
	 *
	 * @param function - rule checker function
	 * @param colorConstraint - color constraint
	 */
	CellRule(final Function function, Colors colorConstraint) {
		this.function = function;
		this.colorConstraint = colorConstraint;
		valueConstraint = 0;
	}

	/**
	 * Instantiates a new cell rule.
	 *
	 * @param function - rule checker function
	 * @param valueConstraint - value constraint
	 */
	CellRule(final Function function, int valueConstraint) {
		this.function = function;
		this.valueConstraint = valueConstraint;
		colorConstraint = null;
	}

	/**
	 * Builder.
	 *
	 * @return builder object
	 */
	public static CellBuilder<CellRule> builder() {
		return new CellBuilder<>();
	}

	/**
	 * Check rule.
	 *
	 * @param dice - check if dice can be positioned in this current cell
	 * @return true if dice can be positioned in this current cell
	 */
	@Override
	Boolean checkRule(Dice dice) {
		return function.apply(dice);
	}

	/**
	 * Gets the value constraint.
	 *
	 * @return value constraint
	 */
	public int getValueConstraint() {
		return valueConstraint;
	}

	/**
	 * Gets the color constraint.
	 *
	 * @return color constraint
	 */
	public Colors getColorConstraint() {
		return colorConstraint;
	}
}