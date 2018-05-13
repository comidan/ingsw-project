package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.DTO;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.HashSet;
import java.util.function.Function;

/**
 * 
 */
public class ToolBuilder<T extends ToolRule> implements Builder<ToolRule> {

	private Function<DTO, ErrorType> function;

	public ToolBuilder() {

	}

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
				HashSet<Integer> ignoreDiceValueRule = dto.getIgnoreValueSet();
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
			HashSet<Integer> ignoreDiceColorRule = dto.getIgnoreColorSet();
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
			HashSet<Integer> ignoreDiceValueSet = dto.getIgnoreValueSet();
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

}