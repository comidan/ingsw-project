package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.cards.CardType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * The Class ObjectiveRule.
 */
public class ObjectiveRule extends Rule<Cell[][], Integer> {

	/** The value. */
	private int value;
	
	/** The card type. */
	private CardType cardType;
	
	/** The objective type. */
	private CardType objectiveType;
	
	/** The function. */
	private Function<Cell[][], Integer> function;
	
	/** The color constraints. */
	private List<Colors> colorConstraints;
	
	/** The value constraints. */
	private List<Integer> valueConstraints;

	/**
	 * Instantiates a new objective rule.
	 *
	 * @param function the function
	 * @param value the value
	 * @param cardType the card type
	 * @param constraints the constraints
	 * @param objectiveType the objective type
	 */
	ObjectiveRule(final Function function, int value, CardType cardType, List constraints, CardType objectiveType) {
		this.function = function;
		this.value = value;
		this.cardType = cardType;
		this.objectiveType = objectiveType;
		if(objectiveType == CardType.OBJECTIVE_COLOR) {
			colorConstraints = constraints;
			valueConstraints = new ArrayList<>();
		}
		else {
			valueConstraints = constraints;
			colorConstraints = new ArrayList<>();
		}
	}

	/**
	 * Builder.
	 *
	 * @return builder object
	 */
	public static ObjectiveBuilder<ObjectiveRule> builder() {
		return new ObjectiveBuilder<>();
	}

	/**
	 * Check rule.
	 *
	 * @param cells - windows to be rule-checked
	 * @return total score
	 */

	@Override
	public Integer checkRule(Cell[][] cells) {
		return function.apply(cells);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public CardType getType() {
		return cardType;
	}

	/**
	 * Gets the objective type.
	 *
	 * @return the objective type
	 */
	public CardType getObjectiveType() {
		return objectiveType;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return value;
	}

	/**
	 * Gets the color constraints.
	 *
	 * @return the color constraints
	 */
	public List<Colors> getColorConstraints() {
		return colorConstraints;
	}

	/**
	 * Gets the value constraints.
	 *
	 * @return the value constraints
	 */
	public List<Integer> getValueConstraints() {
		return valueConstraints;
	}
}