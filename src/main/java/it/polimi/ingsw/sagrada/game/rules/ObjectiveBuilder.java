package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cells.Cell;

import java.awt.Color;
import java.util.HashSet;
import java.util.function.Function;

/**
 * 
 */
public class ObjectiveBuilder<T extends ObjectiveRule> extends Builder {


	private CardType cardType;
	private Integer value;
	private Function<Cell[][], Integer> function;

	@Override
	public ObjectiveRule build() {
		if(function == null)
			return null;
		return new ObjectiveRule(function, value, cardType);
	}

	/**
	 * @return this ObjectiveBuilder with an updated objective rule
	 */
	public ObjectiveBuilder<T> setColorShadeColorObjective(Color color) {
		function = cells -> {
				int score = 0;
				for(int row = 0; row < cells.length; row++)
					for(int col = 0; col < cells[0].length; col++)
						if(cells[row][col].isOccupied() && cells[row][col].getCurrentDice().getColor().equals(color))
							score += cells[row][col].getCurrentDice().getValue();
				return score;
		};
		cardType = CardType.PRIVATE;
		value = 1;
		return this;
	}

	public ObjectiveBuilder<T> setDifferentDiceColorByRowsObjective(int objectiveScore) {
		function = cells -> {
				int score = 0, differentDiceByColor = 0;
				Color diceColor;
				HashSet<Color> set = new HashSet<Color>();
				for (int row = 0; row < cells.length; row++) {
					for (int col = 0; col < cells[0].length; col++) {
						if (!cells[row][col].isOccupied())
							continue;
						diceColor = cells[row][col].getCurrentDice().getColor();
						if (!set.contains(diceColor)) {
							set.add(diceColor);
							differentDiceByColor++;
						}
					}
					if (differentDiceByColor == cells[0].length)
						score += objectiveScore;
					differentDiceByColor = 0;
					set.clear();
				}
				return score;
		};
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	public ObjectiveBuilder<T> setDifferentDiceColorByColsObjective(int objectiveScore) {
		function = cells -> {

				int score = 0, differentDiceByColor = 0;
				Color diceColor;
				HashSet<Color> set = new HashSet<Color>();
				for (int col = 0; col < cells[0].length; col++) {
					for (int row = 0; row < cells.length; row++) {
						if (!cells[row][col].isOccupied())
							continue;
						diceColor = cells[row][col].getCurrentDice().getColor();
						if (!set.contains(diceColor)) {
							set.add(diceColor);
							differentDiceByColor++;
						}
					}
					if (differentDiceByColor == cells.length)
						score += objectiveScore;
					differentDiceByColor = 0;
					set.clear();
				}
				return score;
		};
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	public ObjectiveBuilder<T> setDifferentDiceValueByColsObjective(int objectiveScore) {
		function = cells -> {

				int score = 0, differentDiceByValue = 0;
				int diceValue;
				HashSet<Integer> set = new HashSet<Integer>();
				for (int col = 0; col < cells[0].length; col++) {
					for (int row = 0; row < cells.length; row++) {
						if (!cells[row][col].isOccupied())
							continue;
						diceValue = cells[row][col].getCurrentDice().getValue();
						if (!set.contains(diceValue)) {
							set.add(diceValue);
							differentDiceByValue++;
						}
					}
					if (differentDiceByValue == cells.length)
						score += objectiveScore;
					differentDiceByValue = 0;
					set.clear();
				}
				return score;
		};
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	public ObjectiveBuilder<T> setDifferentDiceValueByRowsObjective(int objectiveScore) {
		function = cells -> {

				int score = 0, differentDiceByValue = 0;
				int diceValue;
				HashSet<Integer> set = new HashSet<Integer>();
				for (int row = 0; row < cells.length; row++) {
					for (int col = 0; col < cells[0].length; col++) {
						if (!cells[row][col].isOccupied())
							continue;
						diceValue = cells[row][col].getCurrentDice().getValue();
						if (!set.contains(diceValue)) {
							set.add(diceValue);
							differentDiceByValue++;
						}
					}
					if (differentDiceByValue == cells[0].length)
						score += objectiveScore;
					differentDiceByValue = 0;
					set.clear();
				}
				return score;
		};
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

}