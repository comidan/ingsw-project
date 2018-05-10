package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;

import java.util.HashSet;

/**
 * 
 */
public class RuleController {

	private static final MainGameRule mainGameRule = new MainGameRule();

	public ObjectiveBuilder getObjectiveBuilder() {
		return new ObjectiveBuilder();
	}

	public CellBuilder getCellBuilder() {
		return new CellBuilder();
	}

	public HashSet<Integer> getIgnoreValueSet() {
		return mainGameRule.getIgnoreValueSet();
	}

	public HashSet<Integer> getIgnoreColorSet() {
		return mainGameRule.getIgnoreColorSet();
	}

	/**
	 * @param rule - rule to be validated
	 * @return true if rule is validated
	 */
	public <P, R> R validateRule(Rule<P, R> rule, P data) {
		return rule.checkRule(data);
	}

	/**
	 * @param cells - game window
	 * @return ErrrorType - cause of validation failure
	 */

	public ErrorType validateWindow(Cell[][] cells) {
		return mainGameRule.checkRule(cells);
	}
}