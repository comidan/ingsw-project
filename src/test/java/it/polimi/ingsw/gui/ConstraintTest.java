package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstraintTest {

    @Test
    public void testConstraint() {
        assertEquals(Constraint.THREE, Constraint.getValueConstraint(3));
        assertEquals(Constraint.RED, Constraint.getColorConstraint(Colors.RED));
        assertEquals("Constraint3.png", Constraint.getConstraintFileName(Constraint.THREE));
        assertEquals("ConstraintR.png", Constraint.getConstraintFileName(Constraint.RED));
        assertEquals("Dice3R.png", Constraint.getDiceFileName(Constraint.RED, Constraint.THREE));

    }
}
