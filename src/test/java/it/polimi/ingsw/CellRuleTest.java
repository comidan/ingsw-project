package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CellRuleTest {

    @Test
    public void testCellColorRuleBuilding() {
        CellRule cellRuleColor = CellRule.builder().setColorConstraint(Colors.RED).build();
        try {
            Dice dice = new Dice(5, Colors.RED);
            assertTrue(cellRuleColor.checkRule(dice));
        }
        catch (Exception exc) {
            fail();
        }
    }

    @Test
    public void testCellValueRuleBuilding() {
        CellRule cellRuleValue = null;
        try {
            cellRuleValue = CellRule.builder().setNumberConstraint(5).build();
        }
        catch (Exception exc) {
            fail();
        }
        try {
            Dice dice = new Dice(5, Colors.RED);
            if(cellRuleValue == null) {
                fail();
                return;
            }
            assertTrue(cellRuleValue.checkRule(dice));
        }
        catch (Exception exc) {
            fail();
        }
    }
}
