package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CellRuleTest {

    private synchronized boolean checkRule(CellRule cellRule, Dice dice) {
        RuleManager ruleManager = new RuleManager();
        return ruleManager.validateRule(cellRule, dice);
    }

    @Test
    public void testCellColorRuleBuilding() {
        CellRule cellRuleColor = CellRule.builder().setColorConstraint(Colors.RED).build();
        try {
            Dice dice = new Dice(5, Colors.RED);
            dice.setValue(5);
            assertTrue(checkRule(cellRuleColor, dice));
        } catch (Exception exc) {
            fail();
        }
    }

    @Test
    public void testCellValueRuleBuilding() {
        CellRule cellRuleValue = null;
        try {
            cellRuleValue = CellRule.builder().setNumberConstraint(5).build();
        } catch (Exception exc) {
            fail();
        }
        try {
            Dice dice = new Dice(5, Colors.RED);
            dice.setValue(5);
            if (cellRuleValue == null) {
                fail();
                return;
            }
            assertTrue(checkRule(cellRuleValue, dice));
        } catch (Exception exc) {
            fail();
        }
    }
}
