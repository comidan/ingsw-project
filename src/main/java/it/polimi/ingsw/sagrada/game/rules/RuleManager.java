package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;



/**
 * The Class RuleManager.
 */
public class RuleManager {

    /** The Constant mainGameRule. */
    private final MainGameRule mainGameRule = new MainGameRule();

    /** The set index. */
    private Boolean[] setIndex;

    /** The map index. */
    private List<Integer> mapIndex;

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
     * Set new dice to be value ignored.
     *
     * @param diceId the dice id
     */
    public void addIgnoreValue(int diceId) {
        mainGameRule.addIgnoreValue(diceId);
    }

    /**
     * Remove dice from being color ignored.
     *
     * @param diceId the dice id
     */
    public void removeIgnoreValue(int diceId) {
        mainGameRule.removeIgnoreValue(diceId);
    }

    /**
     * Remove dice from being value ignored.
     *
     * @param diceId the dice id
     */
    public void removeIgnoreColor(int diceId) {
        mainGameRule.removeIgnoreColor(diceId);
    }

    /**
     * Set new dice to be color ignored.
     *
     * @param diceId the dice id
     */
    public void addIgnoreColor(int diceId) {
        mainGameRule.addIgnoreColor(diceId);
    }

    /**
     * Adds the ignore sequence dice.
     *
     * @param diceId the dice id
     */
    public void addIgnoreSequenceDice(int diceId) {
        mainGameRule.addIgnoreSequenceDice(diceId);
    }

    /**
     * Exchange ignore sequence dice.
     *
     * @param oldDice the old dice
     * @param newDice the new dice
     */
    public void exchangeIgnoreSequenceDice(int oldDice, int newDice) {
        mainGameRule.exchangeIgnoreSequenceDice(oldDice, newDice);
    }

    /**
     * Removes the dice from set.
     *
     * @param id the id
     */
    public void removeDiceFromSet(int id) {
        setIndex = mainGameRule.removeDiceFromSet(id);
        mapIndex = mainGameRule.removeDiceFromMap(id);
    }

    /**
     * Revert.
     *
     * @param id the id
     */
    public void revert(int id) {
        IntStream.range(0, setIndex.length).filter(index -> setIndex[index]).forEach(index -> {
            switch (index) {
                case 0: mainGameRule.addIgnoreValue(id); break;
                case 1: mainGameRule.addIgnoreColor(id); break;
                case 2: mainGameRule.addIgnoreSequenceDice(id); break;
            }
        });

        if(mapIndex!=null) mainGameRule.addignoreCurrentOrthogonalDice(id, mapIndex);

        IntStream.range(0, setIndex.length).forEach(index -> setIndex[index]=false);
        mapIndex = null;
    }
}