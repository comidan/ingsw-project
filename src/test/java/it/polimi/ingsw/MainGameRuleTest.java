package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cells.Cell;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.MainGameRule;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class MainGameRuleTest {

    @Test
    public void testValidateWindowMainRule() throws Exception{
        Cell[][] cells = new Cell[4][5];
        MainGameRule mainGameRule = new MainGameRule();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
                cells[i][j].setDice(new Dice(1, Colors.RED));
            }
        ErrorType errorType = mainGameRule.validateWindow(cells);
        assertSame(errorType, ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE);
    }

    @Test
    public void testValidateWindowCellRule() throws Exception{
        Cell[][] cells = new Cell[4][5];
        MainGameRule mainGameRule = new MainGameRule();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
        cells[0][0].setDice(new Dice(1, Colors.PURPLE));
        ErrorType errorType = mainGameRule.validateWindow(cells);
        assertSame(errorType, ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED);
    }
}
