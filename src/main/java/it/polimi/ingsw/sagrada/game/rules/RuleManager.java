package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class RuleManager {

    private static final MainGameRule mainGameRule = new MainGameRule();

    public ObjectiveBuilder getObjectiveBuilder() {
        return new ObjectiveBuilder();
    }

    public CellBuilder getCellBuilder() {
        return new CellBuilder();
    }

    public Set<Integer> getIgnoreValueSet() {
        return mainGameRule.getIgnoreValueSet();
    }

    public Set<Integer> getIgnoreColorSet() {
        return mainGameRule.getIgnoreColorSet();
    }

    /**
     * @param rule - rule to be validated
     * @return R
     */
    public <P, R> R validateRule(Rule<P, R> rule, P data) {
        return rule.checkRule(data);
    }

    /**
     * @param rules - rules to be validated
     * @return R
     */
    public int validateObjectiveRules(List<ObjectiveRule> rules, Cell[][] cells) {
        int score = rules.stream().mapToInt(rule -> validateRule(rule, cells)).sum();
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++)
                if (!cells[i][j].isOccupied())
                    score--;
        return score;
    }

    /**
     * @param cells - game window
     * @return ErrrorType - cause of validation failure
     */

    public ErrorType validateWindow(Cell[][] cells) {
        return mainGameRule.checkRule(cells);
    }
}