package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;
import java.util.function.Function;

/**
 * 
 */
public class ToolBuilder<T extends ToolRule> implements Builder<ToolRule> {

	private Function<DTO, ErrorType> function;

	@Override
	public ToolRule build() {
		if(function == null)
			return null;
		else return new ToolRule(function);
	}


	private ErrorType checkIfNull(Object... objects) {
		for(Object object : objects)
			if(object == null)
				return ErrorType.NULL_DATA;
		return ErrorType.NO_ERROR;
	}

	/**
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setIncrementDiceFeature() {
		function = dto -> {

			Dice dice = dto.getDice();
			if(dice != null) {
				int currentValue = dice.getValue();
				if(currentValue == 6)
					return ErrorType.MAX_DICE_VALUE_EXCEEDED;
				Set<Integer> ignoreDiceValueRule = dto.getIgnoreValueSet();
				if(ignoreDiceValueRule == null)
					return ErrorType.NULL_DATA;
				ignoreDiceValueRule.add(dice.getId());
				dice.setValue(currentValue + 1);
				return ErrorType.NO_ERROR;
			}
			return ErrorType.NULL_DATA;
		};
		return this;
	}

	/**
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setMoveIgnoringColorRuleFeature() {
		
		function = dto -> {
			int newRow = dto.getNewPosition().getRow();
			int newCol = dto.getNewPosition().getCol();
			int oldRow = dto.getCurrentPosition().getRow();
			int oldCol = dto.getCurrentPosition().getCol();
			Set<Integer> ignoreDiceColorRule = dto.getIgnoreColorSet();
			Cell[][] cells = dto.getWindowMatrix();
			ErrorType nullError = checkIfNull(cells, ignoreDiceColorRule);
			if(nullError != ErrorType.NO_ERROR)
				return nullError;
			if(oldRow > cells.length - 1 || oldCol > cells[0].length - 1 || newRow > cells.length - 1 || newCol > cells[0].length - 1)
				return ErrorType.MATRIX_ERROR;
			Dice dice = cells[oldRow][oldCol].getCurrentDice();
			if(dice == null)
				return ErrorType.NULL_DATA;
			if(cells[newRow][newCol].isOccupied())
				return ErrorType.MATRIX_ERROR;
			cells[oldRow][oldCol].removeCurrentDice();
			cells[newRow][newCol].setDice(dice);
			ignoreDiceColorRule.add(dice.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setMoveIgnoringValueRuleFeature() {

		function = dto -> {
			int newRow = dto.getNewPosition().getRow();
			int newCol = dto.getNewPosition().getCol();
			int oldRow = dto.getCurrentPosition().getRow();
			int oldCol = dto.getCurrentPosition().getCol();
			Set<Integer> ignoreDiceValueSet = dto.getIgnoreValueSet();
			Cell[][] cells = dto.getWindowMatrix();
			ErrorType nullError = checkIfNull(cells, ignoreDiceValueSet);
			if(nullError != ErrorType.NO_ERROR)
				return nullError;
			if(oldRow > cells.length - 1 || oldCol > cells[0].length - 1 || newRow > cells.length - 1 || newCol > cells[0].length - 1)
				return ErrorType.MATRIX_ERROR;
			Dice dice = cells[oldRow][oldCol].getCurrentDice();
			if(dice == null)
				return ErrorType.NULL_DATA;
			if(cells[newRow][newCol].isOccupied())
				return ErrorType.MATRIX_ERROR;
			cells[oldRow][oldCol].removeCurrentDice();
			cells[newRow][newCol].setDice(dice);
			ignoreDiceValueSet.add(dice.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	public ToolBuilder setMoveOppositeSideDiceFeature() {
		function = dto -> {
			Dice dice = dto.getDice();
			if(dice == null)
				return ErrorType.NULL_DATA;
			dice.setValue(7 - dice.getValue());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	public ToolBuilder setRollDiceFeature() {
		function = dto -> {
			Dice dice = dto.getDice();
			if(dice == null)
				return ErrorType.NULL_DATA;
			dice.roll();
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	public ToolBuilder setAddNewDiceFeature() {
		function = dto -> {
			int row = dto.getNewPosition().getRow();
			int col = dto.getNewPosition().getCol();
			Dice dice = dto.getDice();
			Cell[][] cells = dto.getWindowMatrix();
			if(checkIfNull(dice, cells) == ErrorType.NULL_DATA)
				return ErrorType.NULL_DATA;
			if(row > cells.length - 1 || col > cells[0].length - 1)
				return ErrorType.MATRIX_ERROR;
			if(cells[row][col].isOccupied())
				return ErrorType.MATRIX_ERROR;
			if (row < cells.length - 1 && cells[row + 1][col].isOccupied())
				return ErrorType.ERROR;
			if (col < cells[row].length - 1 && cells[row][col + 1].isOccupied())
				return ErrorType.ERROR;
			if (row - 1 >= 0 && cells[row - 1][col].isOccupied())
				return ErrorType.ERROR;
			if (col - 1 >= 0 && cells[row][col - 1].isOccupied())
				return ErrorType.ERROR;
			cells[row][col].setDice(dice);
			return ErrorType.NO_ERROR;
		};
		return this;
	}

}