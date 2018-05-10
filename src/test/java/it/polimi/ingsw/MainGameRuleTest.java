package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertSame;

public class MainGameRuleTest {

    private synchronized ErrorType checkRule(Cell[][] cells) {
        RuleController ruleController = new RuleController();
        return ruleController.validateWindow(cells);
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
        Dice dicePurple = new Dice(4, Colors.PURPLE);
        dicePurple.setValue(4);
        purple.setDice(dicePurple);
        Cell blue = new Cell(cellRule);
        Dice diceBlue = new Dice(5, Colors.LIGHT_BLUE);
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
        Cell one = new Cell(cellRule);
        Dice diceOne = new Dice(1, Colors.RED);
        diceOne.setValue(1);
        one.setDice(diceOne);
        Cell two = new Cell(cellRule);
        Dice diceTwo = new Dice(2, Colors.YELLOW);
        diceOne.setValue(2);
        two.setDice(diceTwo);
        Cell three = new Cell(cellRule);
        Dice diceThree = new Dice(3, Colors.GREEN);
        diceThree.setValue(3);
        three.setDice(diceThree);
        Cell four = new Cell(cellRule);
        Dice diceFour = new Dice(4, Colors.PURPLE);
        diceFour.setValue(4);
        four.setDice(diceFour);
        Cell five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Color.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.LIGHT_BLUE);
        diceSix.setValue(6);
        six.setDice(diceSix);
        Cell[][] cells = {{five, one, three, six, six},
                {two, three, four, four, one},
                {six, five, two, one, one},
                {one, two, five, six, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] _cells = {{five, one, three, six, two},
                {two, three, four, five, one},
                {six, five, two, one, three},
                {one, two, five, six, three}};
        errorType = checkRule(_cells);
        assertSame(ErrorType.ERRNO_SAME_ORTOGONAL_COLOR_VALUE, errorType);
        Cell[][] __cells = {{five, one, three, six, two},
                {two, three, five, four, one},
                {six, four, two, one, three},
                {one, two, three, six, four}};
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
}
