package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.MainGameRule;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MainGameRuleTest {

    private synchronized ErrorType checkRule(MainGameRule mainGameRule, Cell[][] cells) {
        RuleController ruleController = new RuleController();
        return ruleController.validateRule(mainGameRule, cells);
    }

    @Test
    public void testValidateWindowMainRule() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        MainGameRule mainGameRule = new MainGameRule();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
                cells[i][j].setDice(new Dice(1, Colors.RED));
            }
        ErrorType errorType = checkRule(mainGameRule, cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
    }

    @Test
    public void testValidateWindowCellRule() throws RuntimeException{
        Cell[][] cells = new Cell[4][5];
        MainGameRule mainGameRule = new MainGameRule();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
        cells[0][0].setDice(new Dice(1, Colors.PURPLE));
        ErrorType errorType = checkRule(mainGameRule, cells);
        assertSame(ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED, errorType);
    }
}
