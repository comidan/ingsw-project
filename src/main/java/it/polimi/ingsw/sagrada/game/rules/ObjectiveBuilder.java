package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cells.Cell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	 * @param color color constraint
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

    /**
     * @param objectiveScore rule score
     * @return this cell's rule
     */
	public ObjectiveBuilder<T> setDifferentDiceColorByRowsObjective(int objectiveScore) {
		function = cells -> {
				int score = 0;
				int differentDiceByColor = 0;
				Color diceColor;
				HashSet<Color> set = new HashSet<>();
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

    /**
     * @param objectiveScore rule score
     * @return this cell's rule
     */
	public ObjectiveBuilder<T> setDifferentDiceColorByColsObjective(int objectiveScore) {
		function = cells -> {

				int score = 0;
				int differentDiceByColor = 0;
				Color diceColor;
				HashSet<Color> set = new HashSet<>();
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

    /**
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

    /**
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

    /**
     * @param diagonalColorList diagonal to compute
     * @return computed score
     */
	private int computeDiagonalPartialScore(List<Color> diagonalColorList) {
        int counter = 0;
        Color diceColor = null;
        int tmpScore = 0;
        int partialScore = 0;
        for(Color color : diagonalColorList) {
            if(diceColor == null) {
                diceColor = color;
                tmpScore = 1;
            }
            else if(color.equals(diceColor)) {
                tmpScore++;
            }
            if(tmpScore > 1 && (!color.equals(diceColor) || (color.equals(diceColor) && counter == diagonalColorList.size() - 1))) {
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
     * @param cells window matrix
     * @return total diagonals score
     */
	private int getDiagonalColorScore(Cell[][] cells) {
		int rowStart = cells.length;
		int colStart = 0;
		int score = 0;
		int diagonalCounter = 0;
		Color diceColor;
		List<List<Color>> diagonalTrace = new ArrayList<>();
        int numberOfDiagonals = cells.length + cells[0].length + 1;
		for(int i = 0; i < numberOfDiagonals; i++)
			diagonalTrace.add(new ArrayList<>());
		while(diagonalCounter < numberOfDiagonals) {
			for(int row = rowStart, col = colStart; row < cells.length && col < cells[0].length; row++, col++)
				if (!(row == cells.length - 1 && col == 0) && !(row == 0 && col == cells[0].length - 1)) {
			        if(cells[row][col].isOccupied())
					    diceColor = cells[row][col].getCurrentDice().getColor();
			        else
			            diceColor = Color.BLACK;
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
     * @param cells window matrix
     * @return total anti diagonals score
     */
	private int getAntiDiagonalColorScore(Cell[][] cells) {
		int rowStart = cells.length - 1;
		int colStart = cells.length - 1;
		int score = 0;
		int diagonalCounter = 0;
		Color diceColor;
		List<List<Color>> diagonalTrace = new ArrayList<>();
		int numberOfDiagonals = cells.length + cells[0].length + 1;
		for(int i = 0; i < numberOfDiagonals; i++)
			diagonalTrace.add(new ArrayList<>());
		while(diagonalCounter < numberOfDiagonals) {
			for(int row = rowStart, col = colStart; row >= 0 && row < cells.length && col < cells[0].length; row--, col++) {
                if (!(row == 0 && col == 0) && !(row == cells.length - 1 && col == cells[0].length - 1)) {
                    if(cells[row][col].isOccupied())
                        diceColor = cells[row][col].getCurrentDice().getColor();
                    else
                        diceColor = Color.BLACK;
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
     * @return this cell's rule
     */
	public ObjectiveBuilder<T> setSameDiagonalColorObjective() {

		function = cells -> getDiagonalColorScore(cells) + getAntiDiagonalColorScore(cells);
		cardType = CardType.PUBLIC;
		value = 1;
		return this;
	}

}