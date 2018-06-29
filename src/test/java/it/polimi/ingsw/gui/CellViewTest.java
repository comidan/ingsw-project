package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.gui.components.CellView;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellViewTest {

    public void testCellView() {
        CellView cellView = new CellView(0, 0, Constraint.THREE);
        assertFalse(cellView.isOccupied());
        cellView.setImageCell(new DiceView(Constraint.RED, Constraint.THREE, 1));
        assertTrue(cellView.isOccupied());
        assertEquals(0, cellView.getCol());
        assertEquals(0, cellView.getRow());
        assertEquals(1, cellView.getDiceId());

    }
}
