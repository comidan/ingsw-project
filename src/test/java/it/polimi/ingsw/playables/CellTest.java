package it.polimi.ingsw.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

    @Test
    public void testCell() {
        CellRule cellRuleColor = CellRule.builder().setColorConstraint(Colors.RED).build();
        Cell cell = new Cell(cellRuleColor);
        Dice dice = new Dice(4, Colors.RED);
        assertEquals(cell.getCellRule(), cellRuleColor);
        assertFalse(cell.isOccupied());
        cell.setDice(dice);
        assertTrue(cell.isOccupied());
        assertEquals(cell.getCurrentDice(), dice);
        cell.removeCurrentDice();
        assertFalse(cell.isOccupied());
    }
}

