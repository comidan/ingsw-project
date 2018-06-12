package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DiceViewTest {

    @Test
    public void testDiceView() {
        int id = 1;
        DiceView diceView = new DiceView(Constraint.RED, Constraint.THREE, id);
        assertEquals(Constraint.RED, diceView.getColor());
        assertEquals(Constraint.THREE, diceView.getValue());
        assertEquals(id, diceView.getDiceID());
    }
}
