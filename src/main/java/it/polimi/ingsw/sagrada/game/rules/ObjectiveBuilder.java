package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * The Class ObjectiveBuilder.
 *
 * @param <T> the generic type
 */
public class ObjectiveBuilder<T extends ObjectiveRule> implements Builder {


	/** The card type. */
	private CardType cardType;

	/** The objective type. */
	private CardType objectiveType;

	/** The value. */
	private Integer value;

	/** The function. */
	private Function<Cell[][], Integer> function;

	/** The constraints. */
	private List constraints;

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.sagrada.game.base.Builder#build()
	 */
	@Override
	public ObjectiveRule build() {
		if(function == null)
			return null;
		return new ObjectiveRule(function, value, cardType, constraints, objectiveType);
	}

	/**
	 * Gets the dice value.
	 *
	 * @param cell cell from windows
	 * @return dice value if present
	 */
	private int getDiceValue(Cell cell) {
		Dice dice = cell.getCurrentDice();
		if(dice == null)
			return 0;
		return dice.getValue();
	}

	/**
	 * Gets the dice color.
	 *
	 * @param cell cell from windows
	 * @return dice color if present
	 */
	private Colors getDiceColor(Cell cell) {
		Dice dice = cell.getCurrentDice();
		if(dice == null)
			return Colors.BLACK;
		return dice.getColor();
	}

	/**
	 * Sets the color shade color objective.
	 *
	 * @param color color constraint
	 * @return this ObjectiveBuilder with an updated objective rule
	 */
	public ObjectiveBuilder<T> setColorShadeColorObjective(Colors color) {
		function = cells -> {
			return Arrays.stream(cells).flatMap(Arrays::stream)
					.filter(Cell::isOccupied)
					.filter(cell -> getDiceColor(cell).equals(color))
					.mapToInt(this::getDiceValue)
					.sum();
		};
		constraints = new ArrayList<Colors>();
		constraints.add(color);
		objectiveType = CardType.OBJECTIVE_COLOR;
		cardType = CardType.PRIVATE;
		value = 1;
		return this;
	}

	/**
	 * Sets the different dice color by rows objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setDifferentDiceColorByRowsObjective(int objectiveScore) {
		function = cells -> {
			int score = 0;
			int differentDiceByColor = 0;
			Colors diceColor;
			HashSet<Colors> set = new HashSet<>();
			for (int row = 0; row < cells.length; row++) {
				for (int col = 0; col < cells[0].length; col++) {
					if (!cells[row][col].isOccupied())
						continue;
					diceColor = getDiceColor(cells[row][col]);
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
		objectiveType = CardType.OBJECTIVE_COLOR;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the different dice color by cols objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setDifferentDiceColorByColsObjective(int objectiveScore) {
		function = cells -> {

			int score = 0;
			int differentDiceByColor = 0;
			Colors diceColor;
			HashSet<Colors> set = new HashSet<>();
			for (int col = 0; col < cells[0].length; col++) {
				for (int row = 0; row < cells.length; row++) {
					if (!cells[row][col].isOccupied())
						continue;
					diceColor = getDiceColor(cells[row][col]);
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
		objectiveType = CardType.OBJECTIVE_COLOR;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the different dice value by cols objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setDifferentDiceValueByColsObjective(int objectiveScore) {
		function = cells -> {

			int score = 0;
			int differentDiceByValue = 0;
			int diceValue;
			HashSet<Integer> set = new HashSet<>();
			for (int col = 0; col < cells[0].length; col++) {
				for (int row = 0; row < cells.length; row++) {
					if (!cells[row][col].isOccupied())
						continue;
					diceValue = getDiceValue(cells[row][col]);
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
		objectiveType = CardType.OBJECTIVE_VALUE;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the different dice value by rows objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setDifferentDiceValueByRowsObjective(int objectiveScore) {
		function = cells -> {

			int score = 0;
			int differentDiceByValue = 0;
			int diceValue;
			HashSet<Integer> set = new HashSet<>();
			for (int row = 0; row < cells.length; row++) {
				for (int col = 0; col < cells[0].length; col++) {
					if (!cells[row][col].isOccupied())
						continue;
					diceValue = getDiceValue(cells[row][col]);
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
		objectiveType = CardType.OBJECTIVE_VALUE;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the value couple objective.
	 *
	 * @param objectiveScore rule score
	 * @param firstValue first pair value constraint
	 * @param secondValue second pair value constraint
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setValueCoupleObjective(int objectiveScore, int firstValue, int secondValue) {
		function = cells -> {
			AtomicInteger firstValueMatch = new AtomicInteger(0);
			AtomicInteger secondValueMatch = new AtomicInteger(0);
			Arrays.stream(cells).flatMap(Arrays::stream)
								.filter(Cell::isOccupied)
								.forEach(cell -> {
									if(getDiceValue(cell) == firstValue)
										firstValueMatch.incrementAndGet();
									else if(getDiceValue(cell) == secondValue)
										secondValueMatch.incrementAndGet();
								});
			return firstValueMatch.get() <= secondValueMatch.get() ? firstValueMatch.get() * objectiveScore : secondValueMatch.get() * objectiveScore;
		};
		constraints = Arrays.asList(firstValue, secondValue);
		objectiveType = CardType.OBJECTIVE_VALUE;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the every color repeating objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setEveryColorRepeatingObjective(int objectiveScore) {
		function = cells -> {
			final AtomicInteger match = new AtomicInteger(0);
			HashSet<Colors> colorsMatch = new HashSet<>();
			List<Colors> reusable = new ArrayList<>();
			List<Colors> colorList = Colors.getColorList();
			Arrays.stream(cells).flatMap(Arrays::stream).forEach(cell -> {
				if (cell.isOccupied() && !colorsMatch.contains(getDiceColor(cell)))
					colorsMatch.add(getDiceColor(cell));
				else if(colorsMatch.contains(getDiceColor(cell)))
					reusable.add(getDiceColor(cell));
				if(colorsMatch.size() == colorList.size()) {
					match.incrementAndGet();
					colorsMatch.clear();
					reusable.stream().filter(reusable::contains)
							.filter(color -> !colorsMatch.contains(color))
							.map(colorsMatch::add)
							.forEach(reusable::remove);
				}
			});
			return match.get() * objectiveScore;
		};
		constraints = Colors.getColorList();
		objectiveType = CardType.OBJECTIVE_COLOR;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Sets the every dice value repeating objective.
	 *
	 * @param objectiveScore rule score
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setEveryDiceValueRepeatingObjective(int objectiveScore) {
		function = cells -> {
			final AtomicInteger match = new AtomicInteger(0);
			HashSet<Integer> valuesMatch = new HashSet<>();
			List<Integer> reusable = new ArrayList<>();
			Arrays.stream(cells).flatMap(Arrays::stream).forEach(cell -> {
				if (cell.isOccupied() && !valuesMatch.contains(getDiceValue(cell)))
					valuesMatch.add(getDiceValue(cell));
				else if(valuesMatch.contains(getDiceValue(cell)))
					reusable.add(getDiceValue(cell));
				if(valuesMatch.size() == 6) {
					match.incrementAndGet();
					valuesMatch.clear();
					reusable.stream().filter(reusable::contains)
							.filter(i -> !valuesMatch.contains(i))
							.map(valuesMatch::add)
							.forEach(reusable::remove);
				}
			});
			return match.get() * objectiveScore;
		};
		constraints = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6}).boxed().collect(Collectors.toList());
		objectiveType = CardType.OBJECTIVE_VALUE;
		cardType = CardType.PUBLIC;
		value = objectiveScore;
		return this;
	}

	/**
	 * Compute diagonal partial score.
	 *
	 * @param diagonalColorList diagonal to compute
	 * @return computed score
	 */
	private int computeDiagonalPartialScore(List<Colors> diagonalColorList) {
		int counter = 0;
		Colors diceColor = null;
		int tmpScore = 0;
		int partialScore = 0;
		for(Colors color : diagonalColorList) {
			if(diceColor == null) {
				diceColor = color;
				tmpScore = 1;
			}
			else if(color.equals(diceColor)) {
				tmpScore++;
			}
			if(tmpScore > 1 && (!color.equals(diceColor) || counter == diagonalColorList.size() - 1)) {
				diceColor = color;
				partialScore += tmpScore;
				tmpScore = 1;
			}
			else if(!color.equals(diceColor)) {
				tmpScore = 1;
				diceColor = color;
			}
			counter++;
		}

		return partialScore;
	}

	/**
	 * Gets the diagonal color score.
	 *
	 * @param cells windows matrix
	 * @return total diagonals score
	 */
	private int getDiagonalColorScore(Cell[][] cells) {
		int rowStart = cells.length;
		int colStart = 0;
		int score = 0;
		int diagonalCounter = 0;
		Colors diceColor;
		int numberOfDiagonals = cells.length + cells[0].length + 1;
		Function<Integer, List> constructor = ArrayList::new;
		List<List<Colors>> diagonalTrace = initializeEmptyNestedList(numberOfDiagonals, constructor);
		while(diagonalCounter < numberOfDiagonals) {
			for(int row = rowStart, col = colStart; row < cells.length && col < cells[0].length; row++, col++)
				if (!(row == cells.length - 1 && col == 0) && !(row == 0 && col == cells[0].length - 1)) {
					if(cells[row][col].isOccupied())
						diceColor = getDiceColor(cells[row][col]);
					else
						diceColor = Colors.BLACK;
					diagonalTrace.get(diagonalCounter).add(diceColor);
				}
			score += computeDiagonalPartialScore(diagonalTrace.get(diagonalCounter));
			diagonalTrace.get(diagonalCounter).clear();
			if(rowStart > 0)
				rowStart--;
			else
				colStart++;
			diagonalCounter++;
		}
		return score;
	}

	/**
	 * Initialize empty nested list.
	 *
	 * @param inserts list dimension
	 * @param function nested type constructor
	 * @return initialized nested list
	 */
	private List initializeEmptyNestedList(int inserts, Function function) {
		List list = new ArrayList<>();
		IntStream.range(0, inserts).forEach(v -> list.add(function.apply(0)));
		return list;
	}

	/**
	 * Gets the anti diagonal color score.
	 *
	 * @param cells windows matrix
	 * @return total anti diagonals score
	 */
	private int getAntiDiagonalColorScore(Cell[][] cells) {
		int rowStart = cells.length - 1;
		int colStart = cells.length - 1;
		int score = 0;
		int diagonalCounter = 0;
		Colors diceColor;
		int numberOfDiagonals = cells.length + cells[0].length + 1;
		Function<Integer, List> constructor = ArrayList::new;
		List<List<Colors>> diagonalTrace = initializeEmptyNestedList(numberOfDiagonals, constructor);
		while(diagonalCounter < numberOfDiagonals) {
			for(int row = rowStart, col = colStart; row >= 0 && row < cells.length && col < cells[0].length; row--, col++) {
				if (!(row == 0 && col == 0) && !(row == cells.length - 1 && col == cells[0].length - 1)) {
					if(cells[row][col].isOccupied())
						diceColor = getDiceColor(cells[row][col]);
					else
						diceColor = Colors.BLACK;
					diagonalTrace.get(diagonalCounter).add(diceColor);
				}
			}
			score += computeDiagonalPartialScore(diagonalTrace.get(diagonalCounter));
			diagonalTrace.get(diagonalCounter).clear();
			if(colStart > 0)
				colStart--;
			else
				rowStart--;
			diagonalCounter++;
		}
		return score;
	}

	/**
	 * Sets the same diagonal color objective.
	 *
	 * @return this cell's rule
	 */
	public ObjectiveBuilder<T> setSameDiagonalColorObjective() {

		function = cells -> getDiagonalColorScore(cells) + getAntiDiagonalColorScore(cells);
		cardType = CardType.PUBLIC;
		value = 1;
		return this;
	}

}