package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MainGameRuleTest {

    private synchronized ErrorType checkRule(Cell[][] cells) {
        RuleManager ruleManager = new RuleManager();
        return ruleManager.validateWindow(cells);
    }

    @Test
    public void testValidateWindowMainRule() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
                cells[i][j].setDice(new Dice(1, Colors.RED));
            }
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
    }

    @Test
    public void testValidateWindowRuleBruteColor() {
        CellRule cellRule = CellRule.builder().build();
        Cell red = new Cell(cellRule);
        Dice diceRed = new Dice(1, Colors.RED);
        diceRed.setValue(1);
        red.setDice(diceRed);
        Cell yellow = new Cell(cellRule);
        Dice diceYellow = new Dice(2, Colors.YELLOW);
        diceYellow.setValue(2);
        yellow.setDice(diceYellow);
        Cell green = new Cell(cellRule);
        Dice diceGreen = new Dice(3, Colors.GREEN);
        diceRed.setValue(3);
        green.setDice(diceGreen);
        Cell purple = new Cell(cellRule);
        Dice dicePurple = new Dice(7, Colors.PURPLE);
        dicePurple.setValue(4);
        purple.setDice(dicePurple);
        Cell blue = new Cell(cellRule);
        Dice diceBlue = new Dice(5, Colors.BLUE);
        diceBlue.setValue(5);
        blue.setDice(diceBlue);
        Cell[][] _cells = {{red, yellow, green, purple, blue},
                {blue, red, yellow, green, purple},
                {purple, blue, red, yellow, green},
                {green, purple, blue, red, yellow}};
        ErrorType errorType = checkRule(_cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        Cell[][] __cells = {{red, yellow, green, purple, blue},
                {blue, red, yellow, green, purple},
                {purple, blue, red, yellow, purple},
                {green, purple, blue, red, yellow}};
        errorType = checkRule(__cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
    }

    @Test
    public void testValidateWindowRuleBruteValue() {
        CellRule cellRule = CellRule.builder().build();
        Cell ___one = new Cell(cellRule);
        Dice diceOne = new Dice(1, Colors.RED);
        diceOne.setValue(1);
        ___one.setDice(diceOne);
        Cell ___two = new Cell(cellRule);
        Dice diceTwo = new Dice(2, Colors.YELLOW);
        diceOne.setValue(2);
        ___two.setDice(diceTwo);
        Cell ___tnree = new Cell(cellRule);
        Dice diceThree = new Dice(3, Colors.GREEN);
        diceThree.setValue(3);
        ___tnree.setDice(diceThree);
        Cell ___four = new Cell(cellRule);
        Dice diceFour = new Dice(7, Colors.PURPLE);
        diceFour.setValue(4);
        ___four.setDice(diceFour);
        Cell ___five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        ___five.setDice(diceFive);
        Cell ___six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
        diceSix.setValue(6);
        ___six.setDice(diceSix);
        Cell[][] cells = {{___five, ___one, ___tnree, ___six, ___six},
                {___two, ___tnree, ___four, ___four, ___one},
                {___six, ___five, ___two, ___one, ___one},
                {___one, ___two, ___five, ___six, ___tnree}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] _cells = {{___five, ___one, ___tnree, ___six, ___two},
                {___two, ___tnree, ___four, ___five, ___one},
                {___six, ___five, ___two, ___one, ___tnree},
                {___one, ___two, ___five, ___six, ___tnree}};
        errorType = checkRule(_cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] __cells = {{___five, ___one, ___tnree, ___six, ___two},
                {___two, ___tnree, ___five, ___four, ___one},
                {___six, ___four, ___two, ___one, ___tnree},
                {___one, ___two, ___tnree, ___six, ___four}};
        errorType = checkRule(__cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }

    @Test
    public void testValidateWindowCellRule() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
        Dice dice = new Dice(1, Colors.PURPLE);
        dice.setValue(1);
        cells[0][0].setDice(dice);
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.ERRNO_CELL_RULE_NOT_VALIDATED, errorType);
    }

    @Test
    public void testValidateWindowConsecutiveDice() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.RED).build());
        Dice dice = new Dice(1, Colors.RED);
        dice.setValue(1);
        cells[2][2].setDice(dice);
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.MATRIX_ERROR, errorType);
    }
}
