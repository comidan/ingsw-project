package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.CellRule;



/**
 * Cell class contained inside window matrix indicating which rule a cell must follow to have a legitimate dice inside
 */
public class Cell {

	private boolean occupied;

	private CellRule cellRule;

	private Dice dice;


	/**
	 * Instantiates a new cell
	 *
	 * @param cellRule cell rule to be followed
	 */
	public Cell(CellRule cellRule) {
		this.cellRule = cellRule;
		occupied = false;
	}

	/**
	 * Instantiates a new cell
	 */
	public Cell(){
		cellRule = CellRule.builder().build();
		occupied = false;
	}

	/**
	 * Gets the cell rule
	 *
	 * @return this cell's rule
	 */
	public CellRule getCellRule() {
		return cellRule;
	}

	/**
	 * Checks if is occupied.
	 *
	 * @return true if occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Gets the current dice.
	 *
	 * @return current positioned dice
	 */
	public Dice getCurrentDice() {
		return dice;
	}

	/**
	 * Sets the dice
	 *
	 * @param dice new positioned dice
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
		occupied = true;
	}

	/**
	 * Removes the current dice.
	 */
	public void removeCurrentDice() {
		dice = null;
		occupied = false;
	}
}