package it.polimi.ingsw.sagrada.game.rules;


import it.polimi.ingsw.sagrada.game.base.Cell;

/**
 * 
 */
public class MainGameRule extends Rule<Cell[][], ErrorType> {

	/**
	 * @param cells window matrix
	 * @return type of error
	 */
	@Override
	ErrorType checkRule(Cell[][] cells) {
		for (int row = 0; row < cells.length; row++)
			for (int col = 0; col < cells[0].length; col++)
				if (cells[row][col].isOccupied()) {
					if (!cells[row][col].getCellRule().checkRule(cells[row][col].getCurrentDice())) {
						return ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED;
					}
					if (row < cells.length - 1)
						if (cells[row + 1][col].isOccupied() && ((cells[row][col].getCurrentDice().getValue() == cells[row + 1][col].getCurrentDice().getValue()) ||
								(cells[row][col].getCurrentDice().getColor().equals(cells[row + 1][col].getCurrentDice().getColor()))))
							return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
					if (col < cells[row].length - 1)
						if (cells[row][col + 1].isOccupied() && ((cells[row][col].getCurrentDice().getValue() == cells[row][col + 1].getCurrentDice().getValue()) ||
						    (cells[row][col + 1].getCurrentDice().getColor().equals(cells[row][col + 1].getCurrentDice().getColor()))))
							return ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE;
				}
		return ErrorType.NO_ERROR;
	}
}