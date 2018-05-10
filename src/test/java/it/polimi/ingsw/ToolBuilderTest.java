package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.rules.ToolRule;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ToolBuilderTest {

    @Test
    public void testToolBuilder() {
        ToolRule toolRule = ToolRule.builder().build();
        assertNull(toolRule);
        toolRule = ToolRule.builder().setMoveIgnoringColorRuleFeature().build();
        assertNotNull(toolRule);
        toolRule = ToolRule.builder().setMoveIgnoringValueRuleFeature().build();
        assertNotNull(toolRule);
        toolRule = ToolRule.builder().setIncrementDiceFeature().build();
        assertNotNull(toolRule);
    }
}

