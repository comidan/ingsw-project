package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CellBuilderTest {

    private synchronized boolean checkRule(CellRule cellRule, Dice dice) {
        RuleManager ruleManager = new RuleManager();
        return ruleManager.validateRule(cellRule, dice);
    }

    @Test
    public void testCellBuilder() throws RuntimeException {
        CellRule cellRule = CellRule.builder().setNumberConstraint(1).build();
        Dice diceOne = new Dice(1, Colors.LIGHT_BLUE);
        diceOne.setValue(1);
        assertTrue(checkRule(cellRule, diceOne));
        Dice diceTwo = new Dice(2, Colors.LIGHT_BLUE);
        diceTwo.setValue(2);
        assertTrue(!checkRule(cellRule, diceTwo));
        assertNull(cellRule.getColorConstraint());
        assertEquals(1, cellRule.getValueConstraint());
        cellRule = CellRule.builder().setColorConstraint(Colors.LIGHT_BLUE).build();
        assertTrue(checkRule(cellRule, diceOne));
        Dice diceThree = new Dice(3, Colors.RED);
        diceThree.setValue(2);
        assertTrue(!checkRule(cellRule, new Dice(2, Colors.RED)));
        assertEquals(Colors.LIGHT_BLUE, cellRule.getColorConstraint());
        assertEquals(0, cellRule.getValueConstraint());
        cellRule = CellRule.builder().build();
        assertTrue(checkRule(cellRule, null)); //no rules => always true

    }
}
