package it.polimi.ingsw.sagrada.game.rules;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.*;


/**
 * The Class MainGameRule.
 */
public class MainGameRule extends Rule<Cell[][], ErrorType> {

	/** The ignore color set. */
	private Set<Integer> ignoreColorSet;
	
	/** The ignore value set. */
	private Set<Integer> ignoreValueSet;

	private Set<Integer> ignoreFirstRoundDice;

	private Map<Integer, List<Integer>> ignoreCurrentOrthogonalDice;

	private List<Integer> computingWorkToDo;

	/**
	 * Instantiates a new main game rule.
	 */
	MainGameRule() {
		ignoreColorSet = new HashSet<>();
		ignoreValueSet = new HashSet<>();
		ignoreCurrentOrthogonalDice = new HashMap<>();
		computingWorkToDo = new ArrayList<>();
		ignoreFirstRoundDice = new HashSet<>();
	}

	/**
	 * Check rule.
	 *
	 * @param cells windows matrix
	 * @return type of error
	 */
	@Override
	ErrorType checkRule(Cell[][] cells) {
	    computingWorkToDo.forEach(id -> computeWork(cells, id));
	    computingWorkToDo.clear();
		ErrorType error;
		for (int row = 0; row < cells.length; row++)
			for (int col = 0; col < cells[0].length; col++)
				if (cells[row][col].isOccupied()) {
					error = checkDicePositioningRule(cells, row, col);
					if(error != ErrorType.NO_ERROR)
						return error;
					error = checkCurrentCellRule(cells, row, col);
					if(error != ErrorType.NO_ERROR)
						return error;
					error = checkSameOrthogonalValueColor(cells, row, col);
					if(error != ErrorType.NO_ERROR)
						return error;
				}
		return ErrorType.NO_ERROR;
	}

	/**
	 * Checks for dice clearance.
	 *
	 * @param dice the dice
	 * @return true, if successful
	 */
	private boolean hasDiceClearance(Dice dice) {
		return ignoreValueSet.contains(dice.getId()) || ignoreColorSet.contains(dice.getId());
	}

	private boolean hasIndirectDiceClearance(Dice orthogonalDice, Dice ignoredDice) {
	    List<Integer> canBeIgnored = ignoreCurrentOrthogonalDice.get(ignoredDice.getId());
	    return canBeIgnored != null && canBeIgnored.contains(orthogonalDice.getId());
    }

	private boolean hasValueDiceClearance(Dice dice) {
		return ignoreValueSet.contains(dice.getId());
	}

	private boolean hasColorDiceClearance(Dice dice) {
		return ignoreColorSet.contains(dice.getId());
	}

	/**
	 * Check current cell rule.
	 *
	 * @param cells windows matrix
	 * @param row row location
	 * @param col col location
	 * @return type of error
	 */
	private ErrorType checkCurrentCellRule(Cell[][] cells, int row, int col) {
		CellRule cellRule = cells[row][col].getCellRule();
		boolean checkRule = !cellRule.checkRule(cells[row][col].getCurrentDice());
		if(cellRule.getValueConstraint()!=0) {
			if (checkRule && !hasValueDiceClearance(cells[row][col].getCurrentDice()))
				return ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED;
			else
				return ErrorType.NO_ERROR;
		}
		else if(cellRule.getColorConstraint()!=null) {
			if (checkRule && !hasColorDiceClearance(cells[row][col].getCurrentDice()))
				return ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED;
			else
				return ErrorType.NO_ERROR;
		}
		else {
			if(checkRule)
				return ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED;
			else
				return ErrorType.NO_ERROR;
		}
	}

	/**
	 * Check dice positioning rule.
	 *
	 * @param cells the cells
	 * @param row the row
	 * @param col the col
	 * @return the error type
	 */
	private ErrorType checkDicePositioningRule(Cell[][] cells, int row, int col) {
		if(ignoreFirstRoundDice.contains(cells[row][col].getCurrentDice().getId()))
			return ErrorType.NO_ERROR;
		ErrorType errorType1 = checkConsecutiveDicePositioning(cells, row, col);
		ErrorType errorType2 = checkDiagonalDicePositioning(cells, row, col);
		if(errorType1 != ErrorType.NO_ERROR && errorType2 != ErrorType.NO_ERROR)
			return ErrorType.MATRIX_ERROR;
		return ErrorType.NO_ERROR;
	}

	/**
	 * Check diagonal dice positioning.
	 *
	 * @param cells the cells
	 * @param row the row
	 * @param col the col
	 * @return the error type
	 */
	private ErrorType checkDiagonalDicePositioning(Cell[][] cells, int row, int col) {
		if (row < cells.length - 1 && col < cells[row].length - 1 && cells[row + 1][col + 1].isOccupied())
			return ErrorType.NO_ERROR;
		if (row < cells.length - 1 && col > 0 && cells[row + 1][col - 1].isOccupied())
			return ErrorType.NO_ERROR;
		if (row > 0 && col < cells[row].length - 1 && cells[row - 1][col + 1].isOccupied())
			return ErrorType.NO_ERROR;
		if (row > 0 && col > 0 && cells[row - 1][col - 1].isOccupied())
			return ErrorType.NO_ERROR;
		return ErrorType.MATRIX_ERROR;
	}

	/**
	 * Check consecutive dice positioning.
	 *
	 * @param cells the cells
	 * @param row the row
	 * @param col the col
	 * @return the error type
	 */
	private ErrorType checkConsecutiveDicePositioning(Cell[][] cells, int row, int col) {
		if(isEmpty(cells) && (row == 0 || col == 0 || row == cells.length - 1 || col == cells[0].length - 1)) {
			ignoreFirstRoundDice.add(cells[row][col].getCurrentDice().getId());
			return ErrorType.NO_ERROR;
		}
		if (row < cells.length - 1 && cells[row + 1][col].isOccupied())
			return ErrorType.NO_ERROR;
		if (col < cells[row].length - 1 && cells[row][col + 1].isOccupied())
			return ErrorType.NO_ERROR;
		if (row - 1 >= 0 && cells[row - 1][col].isOccupied())
			return ErrorType.NO_ERROR;
		if (col - 1 >= 0 && cells[row][col - 1].isOccupied())
			return ErrorType.NO_ERROR;
		return ErrorType.MATRIX_ERROR;
	}

	/**
	 * Check same orthogonal value color.
	 *
	 * @param cells windows matrix
	 * @param row row location
	 * @param col col location
	 * @return type of error
	 */
	private ErrorType checkSameOrthogonalValueColor(Cell[][] cells, int row, int col) {
		if (row < cells.length - 1 && cells[row + 1][col].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row + 1][col])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row + 1][col])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasIndirectDiceClearance(cells[row][col].getCurrentDice(), cells[row + 1][col].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (col < cells[row].length - 1 && cells[row][col + 1].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row][col + 1])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row][col + 1])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasIndirectDiceClearance(cells[row][col].getCurrentDice(), cells[row][col + 1].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (row - 1 >= 0 && cells[row - 1][col].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row - 1][col])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row - 1][col])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasIndirectDiceClearance(cells[row][col].getCurrentDice(), cells[row - 1][col].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		if (col - 1 >= 0 && cells[row][col - 1].isOccupied() && ((getDiceValue(cells[row][col]) == getDiceValue(cells[row][col - 1])) ||
				(getDiceColor(cells[row][col]).equals(getDiceColor(cells[row][col - 1])))) && !(hasDiceClearance(cells[row][col].getCurrentDice()) || hasIndirectDiceClearance(cells[row][col].getCurrentDice(), cells[row][col - 1].getCurrentDice())))
			return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
		return ErrorType.NO_ERROR;
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
	 * Set new dice to be color ignored
	 */
    void addIgnoreColor(int diceId) {
        ignoreColorSet.add(diceId);
        computingWorkToDo.add(diceId);
    }

    void addIgnoreValue(int diceId) {
        ignoreValueSet.add(diceId);
        computingWorkToDo.add(diceId);
    }

    /**
     * Remove dice from being color ignored
     */
    void removeIgnoreValue(int diceId) {
        ignoreValueSet.remove(diceId);
    }

    /**
     * Remove dice from being value ignored
     */
    void removeIgnoreColor(int diceId) {
        ignoreColorSet.remove(diceId);
    }

	private void computeWork(Cell[][] cells, int toDo) {
		int x = 0;
		int y = 0;
		boolean stop = false;
		for(int row = 0; x < cells.length && !stop; row++)
			for(int col = 0; col < cells[row].length && !stop; col++)
				if(cells[row][col].isOccupied() && cells[row][col].getCurrentDice().getId() == toDo) {
					x = row;
					y = col;
					stop = true;
					break;
				}
		List<Integer> toIgnore = new ArrayList<>();
		if(x - 1 >= 0 && cells[x - 1][y].isOccupied())
			toIgnore.add(cells[x - 1][y].getCurrentDice().getId());
		if(x < cells.length - 1 && cells[x + 1][y].isOccupied())
			toIgnore.add(cells[x + 1][y].getCurrentDice().getId());
		if(y - 1 >= 0 && cells[x][y - 1].isOccupied())
			toIgnore.add(cells[x][y - 1].getCurrentDice().getId());
		if(y < cells[0].length - 1 && cells[x][y - 1].isOccupied())
			toIgnore.add(cells[x][y + 1].getCurrentDice().getId());
		if (x < cells.length - 1 && y < cells[x].length - 1 && cells[x + 1][x + 1].isOccupied())
			toIgnore.add(cells[x + 1][y + 1].getCurrentDice().getId());
		if (x < cells.length - 1 && y > 0 && cells[x + 1][y - 1].isOccupied())
			toIgnore.add(cells[x + 1][y - 1].getCurrentDice().getId());
		if (x > 0 && y < cells[x].length - 1 && cells[x - 1][y + 1].isOccupied())
			toIgnore.add(cells[x - 1][y + 1].getCurrentDice().getId());
		if (x > 0 && y > 0 && cells[x - 1][y - 1].isOccupied())
			toIgnore.add(cells[x - 1][y - 1].getCurrentDice().getId());
		ignoreCurrentOrthogonalDice.put(toDo, toIgnore);
	}

	private boolean isEmpty(Cell[][] cells) {
		return Arrays.stream(cells).flatMap(Arrays::stream).filter(Cell::isOccupied).count() - 1 == 0;
	}

	void addIgnoreSequenceDice(int diceId) {
		ignoreFirstRoundDice.add(diceId);
	}

	void exchangeIgnoreSequenceDice(int oldDice, int newDice) {
		if(ignoreFirstRoundDice.remove(oldDice))
			ignoreFirstRoundDice.add(newDice);
	}
}