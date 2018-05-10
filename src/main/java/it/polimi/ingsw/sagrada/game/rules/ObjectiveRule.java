package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.base.Cell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 
 */
public class ObjectiveRule extends Rule<Cell[][], Integer> {

	private int value;
	private CardType cardType;
	private CardType objectiveType;
	private Function<Cell[][], Integer> function;
	private List<Color> colorConstraints = null;
	private List<Integer> valueConstraints = null;

	/**
	 * @param function
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
	 * @return builder object
	 */
	public static ObjectiveBuilder<ObjectiveRule> builder() {
		return new ObjectiveBuilder<>();
	}

	/**
	 * @param cells - window to be rule-checked
	 * @return total score
	 */

	@Override
	Integer checkRule(Cell[][] cells) {
		return function.apply(cells);
	}

	public CardType getType() {
		return cardType;
	}

	public CardType getObjectiveType() {
		return objectiveType;
	}

	public int getScore() {
		return value;
	}

	public List<Color> getColorConstraints() {
		return colorConstraints;
	}

	public List<Integer> getValueConstraints() {
		return valueConstraints;
	}
}