package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.MainGameRule;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import java.awt.*;

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
    public void testValidateWindowRuleBruteColor() {
        MainGameRule mainGameRule = new MainGameRule();
        CellRule cellRule = CellRule.builder().build();
        Cell red = new Cell(cellRule);
        red.setDice(new Dice(1, Colors.RED));
        Cell yellow = new Cell(cellRule);
        yellow.setDice(new Dice(2, Colors.YELLOW));
        Cell green = new Cell(cellRule);
        green.setDice(new Dice(3, Colors.GREEN));
        Cell purple = new Cell(cellRule);
        purple.setDice(new Dice(4, Colors.PURPLE));
        Cell blue = new Cell(cellRule);
        blue.setDice(new Dice(5, Colors.LIGHT_BLUE));
        Cell[][] _cells = {{red, yellow, green, purple, blue},
                {blue, red, yellow, green, purple},
                {purple, blue, red, yellow, green},
                {green, purple, blue, red, yellow}};
        ErrorType errorType = checkRule(mainGameRule, _cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        Cell[][] __cells = {{red, yellow, green, purple, blue},
                {blue, red, yellow, green, purple},
                {purple, blue, red, yellow, purple},
                {green, purple, blue, red, yellow}};
        errorType = checkRule(mainGameRule, __cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
    }

    @Test
    public void testValidateWindowRuelBruteValue() {
        CellRule cellRule = CellRule.builder().build();
        MainGameRule mainGameRule = new MainGameRule();
        Cell one = new Cell(cellRule);
        one.setDice(new Dice(1, Colors.RED));
        Cell two = new Cell(cellRule);
        two.setDice(new Dice(2, Colors.YELLOW));
        Cell three = new Cell(cellRule);
        three.setDice(new Dice(3, Colors.GREEN));
        Cell four = new Cell(cellRule);
        four.setDice(new Dice(4, Colors.PURPLE));
        Cell five = new Cell(cellRule);
        five.setDice(new Dice(5, Color.BLACK));
        Cell six = new Cell(cellRule);
        six.setDice(new Dice(6, Colors.LIGHT_BLUE));
        Cell[][] cells = {{five, one, three, six, six},
                          {two, three, four, four, one},
                          {six, five, two, one, one},
                          {one, two, five, six, three}};
        ErrorType errorType = checkRule(mainGameRule, cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] _cells = {{five, one, three, six, two},
                           {two, three, four, five, one},
                           {six, five, two, one, three},
                           {one, two, five, six, three}};
        errorType = checkRule(mainGameRule, _cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] __cells = {{five, one, three, six, two},
                            {two, three, five, four, one},
                            {six, four, two, one, three},
                            {one, two, three, six, four}};
        errorType = checkRule(mainGameRule, __cells);
        assertSame(ErrorType.NO_ERROR, errorType);
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
