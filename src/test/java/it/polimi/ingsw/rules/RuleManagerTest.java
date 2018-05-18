package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class RuleManagerTest {

    @Test
    public void testValidateWindowMainRule() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        RuleManager ruleManager = new RuleManager();
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
                cells[i][j].setDice(new Dice(1, Colors.RED));
            }
        ErrorType errorType = ruleManager.validateWindow(cells);
        assertSame(errorType, ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE);
    }

    @Test
    public void testValidateWindowCellRule() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        RuleManager ruleManager = new RuleManager();
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
        Dice dice = new Dice(1, Colors.PURPLE);
        dice.setValue(1);
        cells[0][0].setDice(dice);
        ErrorType errorType = ruleManager.validateWindow(cells);
        assertSame(errorType, ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED);
    }

    @Test
    public void testValidateDynamicTypeRuleCheck() {
        RuleManager ruleManager = new RuleManager();
        ObjectiveRule objectiveRule = ruleManager.getObjectiveBuilder().setColorShadeColorObjective(Colors.RED).build();
        Cell[][] cells = new Cell[4][5];
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
                cells[i][j].setDice(new Dice(1, Colors.RED));
            }
        ruleManager.validateRule(objectiveRule, cells);
    }
}
