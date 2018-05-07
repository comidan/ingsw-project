package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.CellRule;


/**
 * 
 */
public class Cell {

	private boolean occupied;
	private CellRule cellRule;
	private Dice dice;


	public Cell(CellRule cellRule) {
		this.cellRule = cellRule;
	}

	/**
	 * @return this cell's rule
	 */
	public CellRule getCellRule() {
		return cellRule;
	}

	/**
	 * @return true if occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * @return current positioned dice
	 */
	public Dice getCurrentDice() {
		return dice;
	}

	/**
	 * @param dice new positioned dice
	 */

	public void setDice(Dice dice) {
		this.dice = dice;   //no check to allow user errors
		occupied = true;
	}

	public void removeCurrentDice() {
		dice = null;
		occupied = false;
	}
}