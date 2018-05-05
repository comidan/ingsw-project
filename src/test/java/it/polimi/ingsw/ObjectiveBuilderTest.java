package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ObjectiveBuilderTest {

    @Test
    public void testObjectiveBuilder() {
        ObjectiveRule objectiveRule = ObjectiveRule.builder().build();
        assertNull(objectiveRule);
        objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.LIGHT_BLUE).build();
        assertEquals(CardType.PRIVATE, objectiveRule.getType());
        objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByRowsObjective(5).build();
        assertEquals(5, objectiveRule.getScore());
    }
}
