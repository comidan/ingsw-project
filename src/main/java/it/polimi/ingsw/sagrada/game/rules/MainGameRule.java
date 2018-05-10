package it.polimi.ingsw.sagrada.game.rules;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.*;
import java.util.HashSet;

/**
 * 
 */
public class MainGameRule extends Rule<Cell[][], ErrorType> {

	private HashSet<Integer> ignoreColorSet;
	private HashSet<Integer> ignoreValueSet;

	MainGameRule() {
		ignoreColorSet = new HashSet<>();
		ignoreValueSet = new HashSet<>();
	}

	/**
	 * @param cells window matrix
	 * @return type of error
	 */
	@Override
	ErrorType checkRule(Cell[][] cells) {
		ErrorType error;
		for (int row = 0; row < cells.length; row++)
			for (int col = 0; col < cells[0].length; col++)
				if (cells[row][col].isOccupied()) {
					error = checkCurrentCellRule(cells, row, col);
					if(error != ErrorType.NO_ERROR)
						return error;
					error = checkSameOrthogonalValueColor(cells, row, col);
					if(error != ErrorType.NO_ERROR)
						return error;
				}
		return ErrorType.NO_ERROR;
	}

	private boolean hasDiceClearance(Dice dice) {
		return ignoreValueSet.contains(dice.getId()) || ignoreColorSet.contains(dice.getId());
	}

	/**
	 * @param cells window matrix
	 * @param row row location
	 * @param col col location
	 * @return type of error
	 */
	private ErrorType checkCurrentCellRule(Cell[][] cells, int row, int col) {
		if (!cells[row][col].getCellRule().checkRule(cells[row][col].getCurrentDice()) && !hasDiceClearance(cells[row][col].getCurrentDice()))
			return ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED;
		else
			return ErrorType.NO_ERROR;
	}

	/**
	 * @param cells window matrix
	 * @param row row location
	 * @param col col location
	 * @return type of error
	 */
	private ErrorType checkSameOrthogonalValueColor(Cell[][] cells, int row, int col) {
		if (row < cells.length - 1 && cells[row + 1][col].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row + 1][col])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row + 1][col])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasDiceClearance(cells[row + 1][col].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (col < cells[row].length - 1 && cells[row][col + 1].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row][col + 1])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row][col + 1])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasDiceClearance(cells[row][col + 1].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (row - 1 >= 0 && cells[row - 1][col].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row - 1][col])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row - 1][col])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasDiceClearance(cells[row - 1][col].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (col - 1 >= 0 && cells[row][col - 1].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row][col - 1])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row][col - 1])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasDiceClearance(cells[row][col - 1].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		return ErrorType.NO_ERROR;
	}

	/**
	 * @param cell cell from window
	 * @return dice value if present
	 */
	private int getDiceValue(Cell cell) {
		Dice dice = cell.getCurrentDice();
		if(dice == null)
			return 0;
		return dice.getValue();
	}

	/**
	 * @param cell cell from window
	 * @return dice color if present
	 */
	private Color getDiceColor(Cell cell) {
		Dice dice = cell.getCurrentDice();
		if(dice == null)
			return Color.BLACK;
		return dice.getColor();
	}

	public HashSet<Integer> getIgnoreColorSet() {
		return ignoreColorSet;
	}

	public HashSet<Integer> getIgnoreValueSet() {
		return ignoreValueSet;
	}
}