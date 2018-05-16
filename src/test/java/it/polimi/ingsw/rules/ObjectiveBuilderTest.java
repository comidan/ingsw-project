package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ObjectiveBuilderTest {

    @Test
    public void testObjectiveBuilder() {
        ObjectiveRule objectiveRule = ObjectiveRule.builder().build();
        assertNull(objectiveRule);
        objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.LIGHT_BLUE).build();
        assertEquals(CardType.PRIVATE, objectiveRule.getType());
        objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByRowsObjective(1).build();
        assertEquals(CardType.PUBLIC, objectiveRule.getType());
        objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByColsObjective(1).build();
        assertEquals(CardType.OBJECTIVE_VALUE, objectiveRule.getObjectiveType());
        objectiveRule = ObjectiveRule.builder().setDifferentDiceColorByColsObjective(1).build();
        assertEquals(CardType.OBJECTIVE_COLOR, objectiveRule.getObjectiveType());
        objectiveRule = ObjectiveRule.builder().setValueCoupleObjective(1, 1, 2).build();
        assertEquals(CardType.OBJECTIVE_VALUE, objectiveRule.getObjectiveType());
        assertEquals(0, objectiveRule.getColorConstraints().size());
        assertEquals(2, objectiveRule.getValueConstraints().size());
        assertEquals(1, objectiveRule.getValueConstraints().get(0).intValue());
        assertEquals(2, objectiveRule.getValueConstraints().get(1).intValue());
        objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByRowsObjective(5).build();
        assertEquals(5, objectiveRule.getScore());
        objectiveRule = ObjectiveRule.builder().setEveryDiceValueRepeatingObjective(2).build();
        assertEquals(6, objectiveRule.getValueConstraints().size());
    }
}
