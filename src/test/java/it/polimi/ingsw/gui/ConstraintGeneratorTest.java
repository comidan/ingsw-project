package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.Constraint;
import it.polimi.ingsw.sagrada.gui.ConstraintGenerator;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConstraintGeneratorTest {
    Constraint[][] c = {
            {Constraint.ONE, Constraint.WHITE, Constraint.THREE, Constraint.BLUE, Constraint.WHITE},
            {Constraint.WHITE, Constraint.TWO, Constraint.BLUE, Constraint.WHITE, Constraint.WHITE},
            {Constraint.SIX, Constraint.BLUE, Constraint.WHITE, Constraint.FOUR, Constraint.WHITE},
            {Constraint.BLUE, Constraint.FIVE, Constraint.TWO, Constraint.WHITE, Constraint.ONE}
    };

    @Test
    public void constraintTest() {
        ConstraintGenerator constraintGenerator = new ConstraintGenerator();
        Constraint[][] constraints = constraintGenerator.getConstraintMatrix(3, WindowSide.FRONT);
        for(int j=0; j<4; j++) {
            for(int i=0; i<5; i++) {
                assertEquals(c[j][i], constraints[j][i]);
                System.out.print(constraints[j][i].toString());
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
