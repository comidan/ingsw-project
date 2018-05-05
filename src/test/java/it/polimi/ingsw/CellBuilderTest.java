package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CellBuilderTest {

    @Test
    public void testCellBuilder() throws RuntimeException {
        CellRule cellRule = CellRule.builder().setNumberConstraint(1).build();
        assertTrue(cellRule.checkRule(new Dice(1, Colors.LIGHT_BLUE)));
        assertTrue(!cellRule.checkRule(new Dice(2, Colors.LIGHT_BLUE)));
        assertNull(cellRule.getColorConstraint());
        assertEquals(1, cellRule.getValueConstraint());
        cellRule = CellRule.builder().setColorConstraint(Colors.LIGHT_BLUE).build();
        assertTrue(cellRule.checkRule(new Dice(1, Colors.LIGHT_BLUE)));
        assertTrue(!cellRule.checkRule(new Dice(2, Colors.RED)));
        assertEquals(Colors.LIGHT_BLUE, cellRule.getColorConstraint());
        assertEquals(0, cellRule.getValueConstraint());
        cellRule = CellRule.builder().build();
        assertTrue(cellRule.checkRule(null)); //no rules => always true

    }
}
