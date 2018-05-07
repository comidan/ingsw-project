package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CellBuilderTest {

    private synchronized boolean checkRule(CellRule cellRule, Dice dice) {
        RuleController ruleController = new RuleController();
        return ruleController.validateRule(cellRule, dice);
    }

    @Test
    public void testCellBuilder() throws RuntimeException {
        CellRule cellRule = CellRule.builder().setNumberConstraint(1).build();
        assertTrue(checkRule(cellRule, new Dice(1, Colors.LIGHT_BLUE)));
        assertTrue(!checkRule(cellRule, new Dice(2, Colors.LIGHT_BLUE)));
        assertNull(cellRule.getColorConstraint());
        assertEquals(1, cellRule.getValueConstraint());
        cellRule = CellRule.builder().setColorConstraint(Colors.LIGHT_BLUE).build();
        assertTrue(checkRule(cellRule, new Dice(1, Colors.LIGHT_BLUE)));
        assertTrue(!checkRule(cellRule, new Dice(2, Colors.RED)));
        assertEquals(Colors.LIGHT_BLUE, cellRule.getColorConstraint());
        assertEquals(0, cellRule.getValueConstraint());
        cellRule = CellRule.builder().build();
        assertTrue(checkRule(cellRule, null)); //no rules => always true

    }
}
