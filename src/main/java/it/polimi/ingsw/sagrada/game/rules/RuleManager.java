package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * The Class RuleManager.
 */
public class RuleManager {

    /** The Constant mainGameRule. */
    private final MainGameRule mainGameRule = new MainGameRule();

    /**
     * Gets the objective builder.
     *
     * @return the objective builder
     */
    public ObjectiveBuilder getObjectiveBuilder() {
        return new ObjectiveBuilder();
    }

    /**
     * Gets the cell builder.
     *
     * @return the cell builder
     */
    public CellBuilder getCellBuilder() {
        return new CellBuilder();
    }

    /**
     * Validate rule.
     *
     * @param <P> the generic type
     * @param <R> the generic type
     * @param rule - rule to be validated
     * @param data the data
     * @return R
     */
    public <P, R> R validateRule(Rule<P, R> rule, P data) {
        return rule.checkRule(data);
    }

    /**
     * Validate objective rules.
     *
     * @param rules - rules to be validated
     * @param cells the cells
     * @return score
     */
    public int validateObjectiveRules(List<ObjectiveRule> rules, Cell[][] cells) {
        int score = rules.stream().mapToInt(rule -> validateRule(rule, cells)).sum();
        int scoreFix = Arrays.stream(cells).flatMap(Arrays::stream).filter(cell -> !cell.isOccupied()).mapToInt(value -> 1).sum();
        return score - scoreFix;
    }

    /**
     * Validate window.
     *
     * @param cells - game windows
     * @return ErrrorType - cause of validation failure
     */
    public ErrorType validateWindow(Cell[][] cells) {
        return mainGameRule.checkRule(cells);
    }

    /**
     * Set new dice to be value ignored
     */
    public void addIgnoreValue(int diceId) {
        mainGameRule.addIgnoreValue(diceId);
    }

    /**
     * Remove dice from being color ignored
     */
    public void removeIgnoreValue(int diceId) {
        mainGameRule.removeIgnoreValue(diceId);
    }

    /**
     * Remove dice from being value ignored
     */
    public void removeIgnoreColor(int diceId) {
        mainGameRule.removeIgnoreColor(diceId);
    }

    /**
     * Set new dice to be color ignored
     */
    public void addIgnoreColor(int diceId) {
        mainGameRule.addIgnoreColor(diceId);
    }

    public void addIgnoreSequenceDice(int diceId) {
        mainGameRule.addIgnoreSequenceDice(diceId);
    }

    public void exchangeIgnoreSequenceDice(int oldDice, int newDice) {
        mainGameRule.exchangeIgnoreSequenceDice(oldDice, newDice);
    }
}