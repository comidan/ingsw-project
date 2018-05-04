package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.cells.Cell;
import it.polimi.ingsw.sagrada.game.cells.CellBuilder;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;

/**
 * 
 */
public class RuleController {

	private final ObjectiveBuilder objectiveBuilder = new ObjectiveBuilder();
	private final CellBuilder cellBuilder = new CellBuilder();
	private final MainGameRule mainGameRule = new MainGameRule();

	public ObjectiveBuilder getObjectiveBuilder() {
		return objectiveBuilder;
	}

	public CellBuilder getCellBuilder() {
		return cellBuilder;
	}



	/**
	 * @param rule - rule to be validated
	 * @return boolean
	 */
	public boolean validateRule(Rule rule) {
		// TODO implement here
		return false;
	}

	public boolean validateCellRule(CellRule cellRule, Dice dice) {
		return cellRule.checkRule(dice);
	}

	public int validateObjectiveRule(ObjectiveRule objectiveRule, Cell[][] cells) {
		return objectiveRule.checkRule(cells);
	}

	/**
	 * @param cells - game window
	 * @return ErrrorType - cause of validation failure
	 */

	public ErrorType validateWindow(Cell[][] cells) {
		return mainGameRule.validateWindow(cells);
	}
}