package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * The Class ToolBuilder.
 *
 * @param <T> the generic type
 */
public class ToolBuilder<T extends ToolRule> implements Builder<ToolRule> {

	/** The function. */
	private Function<DTO, ErrorType> function;

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.sagrada.game.base.Builder#build()
	 */
	@Override
	public ToolRule build() {
		if(function == null)
			return null;
		else return new ToolRule(function);
	}


	/**
	 * Check if null.
	 *
	 * @param objects the objects
	 * @return the error type
	 */
	private ErrorType checkIfNull(Object... objects) {
		for(Object object : objects)
			if(object == null)
				return ErrorType.NULL_DATA;
		return ErrorType.NO_ERROR;
	}

	/**
	 * Sets the increment dice feature.
	 *
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setIncrementDiceFeature() {
		function = dto -> {

			Dice dice = dto.getDice();
			if(dice != null) {
				int currentValue = dice.getValue();
				if(currentValue == 6)
					return ErrorType.MAX_DICE_VALUE_EXCEEDED;
				dice.setValue(currentValue + 1);
				return ErrorType.NO_ERROR;
			}
			return ErrorType.NULL_DATA;
		};
		return this;
	}

	/**
	 * Sets the move ignoring color rule feature.
	 *
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setMoveIgnoringColorRuleFeature() {
		
		function = dto -> {
			int newRow = dto.getNewPosition().getRow();
			int newCol = dto.getNewPosition().getCol();
			int oldRow = dto.getCurrentPosition().getRow();
			int oldCol = dto.getCurrentPosition().getCol();
			Consumer<Integer> ignoreDiceColorRule = dto.getIgnoreColorSet();
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
			ignoreDiceColorRule.accept(dice.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets the move ignoring value rule feature.
	 *
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setMoveIgnoringValueRuleFeature() {

		function = dto -> {
			int newRow = dto.getNewPosition().getRow();
			int newCol = dto.getNewPosition().getCol();
			int oldRow = dto.getCurrentPosition().getRow();
			int oldCol = dto.getCurrentPosition().getCol();
			Consumer<Integer> ignoreDiceValueSet = dto.getIgnoreValueSet();
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
			ignoreDiceValueSet.accept(dice.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets the move opposite side dice feature.
	 *
	 * @return the tool builder
	 */
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

	/**
	 * Sets the roll dice feature.
	 *
	 * @return the tool builder
	 */
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

	/**
	 * Sets the add new dice feature.
	 *
	 * @return the tool builder
	 */
	public ToolBuilder setAddNewDiceFeature() { //missing PlayerIterator feature to notify no turn for the player who chose this tool card
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
			if(!cells[row][col].getCellRule().checkRule(dice)) {
				cells[row][col].removeCurrentDice();
				return ErrorType.ERROR;
			}
			dto.getIgnoreSequenceDice().accept(dice.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets two dices feature
	 *
	 * @return the tool builder
	 */
	public ToolBuilder setAddTwoDiceFeature() {
		function = dto -> {
			int row1 = dto.getNewPosition().getRow();
			int col1 = dto.getNewPosition().getCol();
			int row2 = dto.getSecondNewPosition().getRow();
			int col2 = dto.getSecondNewPosition().getCol();
			Dice dice1 = dto.getDice();
			Dice dice2 = dto.getSecondDice();
			Cell[][] cells = dto.getWindowMatrix();
			if(checkIfNull(dice1, dice2, cells) == ErrorType.NULL_DATA)
				return ErrorType.NULL_DATA;
			RuleManager ruleManager = new RuleManager();
			if(cells[row1][col1].isOccupied())
				return ErrorType.ERROR;
			if(cells[row2][col2].isOccupied())
				return ErrorType.ERROR;
			cells[row1][col1].setDice(dice1);
			cells[row2][col2].setDice(dice2);
			if(ruleManager.validateWindow(cells) != ErrorType.NO_ERROR) {
				cells[row1][col1].removeCurrentDice();
				cells[row2][col2].removeCurrentDice();
				return ErrorType.MATRIX_ERROR;
			}
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets exchange draft - round track dice feature
	 *
	 * @return the tool builder
	 */
	public ToolBuilder setExchangeDraftRoundTrackDiceFeature() {
		function = dto -> {
			Dice diceFromDraft = dto.getDice();
			Dice diceFromRoundTrack = dto.getSecondDice();
			BiConsumer<Dice, Dice> exchangeDraftDice = dto.getExchangeDraftDice();
			BiConsumer<Dice, Dice> exchangeRoundTrackDice = dto.getExchangeRoundTrackDice();
			exchangeDraftDice.accept(diceFromDraft, diceFromRoundTrack);
			exchangeRoundTrackDice.accept(diceFromRoundTrack, diceFromDraft);
			dto.getExchangeIgnoreSequenceDice().accept(diceFromDraft.getId(), diceFromRoundTrack.getId());
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets roll every dice in draft feature
	 *
	 * @return the tool builder
	 */
			public ToolBuilder setRollEveryDraftDice() {
				function = dto -> {
					dto.getRollDraft().run();
					return ErrorType.NO_ERROR;
				};
				return this;
			}

			/**
			 * Sets move from draft to bag and set new dice feature
			 *
			 * @return the tool builder
			 */
			public ToolBuilder setFromDraftToBagFeature(){
				function = dto -> {
					Dice diceFromDraft = dto.getDice();
					Dice diceFromBag = dto.getSecondDice();
			Cell[][] cells = dto.getWindowMatrix();
			if(checkIfNull(diceFromBag, diceFromBag, cells) == ErrorType.NULL_DATA)
				return ErrorType.NULL_DATA;
			diceFromBag.setValue(dto.getImposedDiceValue());
			int row = dto.getNewPosition().getRow();
			int col = dto.getNewPosition().getCol();
			if(cells[row][col].isOccupied())
				return ErrorType.ERROR;
			dto.getMoveDiceFromDraftToBag().accept(diceFromDraft);
			cells[row][col].setDice(diceFromBag);
			RuleManager ruleManager = new RuleManager();
			if(ruleManager.validateWindow(cells) != ErrorType.NO_ERROR) {
				cells[row][col].removeCurrentDice();
				return ErrorType.ERROR;
			}
			return ErrorType.NO_ERROR;
		};
		return this;
	}

	/**
	 * Sets move up to two dice of the same color of one Round Track's dice to new positions
	 *
	 * @return the tool builder
	 */
	public ToolBuilder setMoveSameRoundTrackDiceColorFeature() {
		function = dto -> {
			int row1Old = dto.getCurrentPosition().getRow();
			int col1Old = dto.getCurrentPosition().getCol();
			int row2Old = dto.getSecondCurrentPosition().getRow();
			int col2Old = dto.getSecondCurrentPosition().getCol();
			int row1 = dto.getNewPosition().getRow();
			int col1 = dto.getNewPosition().getCol();
			int row2 = dto.getSecondNewPosition().getRow();
			int col2 = dto.getSecondNewPosition().getCol();
			Dice dice1 = dto.getDice();
			Dice dice2 = dto.getSecondDice();
			Cell[][] cells = dto.getWindowMatrix();
			if(checkIfNull(dice1, dice2, cells) == ErrorType.NULL_DATA)
				return ErrorType.NULL_DATA;
			if(!dice1.getColor().equals(dto.getImposedColor()) || !dice2.getColor().equals(dto.getImposedColor()))
				return ErrorType.ERROR;
			RuleManager ruleManager = new RuleManager();
			if(cells[row1][col1].isOccupied() || cells[row2][col2].isOccupied())
				return ErrorType.ERROR;
			cells[row1Old][col1Old].removeCurrentDice();
			cells[row2Old][col2Old].removeCurrentDice();
			cells[row1][col1].setDice(dice1);
			cells[row2][col2].setDice(dice2);
			if(ruleManager.validateWindow(cells) != ErrorType.NO_ERROR) {
				cells[row1][col1].removeCurrentDice();
				cells[row2][col2].removeCurrentDice();
				return ErrorType.MATRIX_ERROR;
			}
			return ErrorType.NO_ERROR;
		};
		return this;
	}
}