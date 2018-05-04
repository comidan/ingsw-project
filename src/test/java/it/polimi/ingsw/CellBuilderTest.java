package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.cells.Cell;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CellBuilderTest {

    @Test
    public void testCellBuilder() throws Exception{
        CellRule cellRule = CellRule.builder().setNumberConstraint(1).build();
        assertTrue(cellRule.checkRule(new Dice(1, Color.BLUE)));
        assertTrue(!cellRule.checkRule(new Dice(2, Color.BLUE)));
        assertNull(cellRule.getColorConstraint());
        assertEquals(1, cellRule.getValueConstraint());
        cellRule = CellRule.builder().setColorConstraint(Color.BLUE).build();
        assertTrue(cellRule.checkRule(new Dice(1, Color.BLUE)));
        assertTrue(!cellRule.checkRule(new Dice(2, Color.RED)));
        assertEquals(Color.BLUE, cellRule.getColorConstraint());
        assertEquals(0, cellRule.getValueConstraint());
        cellRule = CellRule.builder().build();
        assertNull(cellRule);

    }
}
