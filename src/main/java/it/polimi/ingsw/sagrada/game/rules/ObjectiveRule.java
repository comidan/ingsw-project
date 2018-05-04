package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cells.Cell;

import java.util.function.Function;

/**
 * 
 */
public class ObjectiveRule extends Rule {

	private int value;
	private CardType cardType;
	private Function<Cell[][], Integer> function;

	/**
	 * @param function
	 */
	ObjectiveRule(final Function function, int value, CardType cardType) {
		this.function = function;
		this.value = value;
		this.cardType = cardType;
	}

	/**
	 * @return builder object
	 */
	public static ObjectiveBuilder<ObjectiveRule> builder() {
		return new ObjectiveBuilder<ObjectiveRule>();
	}

	/**
	 * @param cells - window to be rule-checked
	 * @return total score
	 */
	public int checkRule(Cell[][] cells) {
		return function.apply(cells);
	}

	public CardType getType() {
		return cardType;
	}

	public int getScore() {
		return value;
	}
}