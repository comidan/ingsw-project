package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertSame;

public class ToolRuleTest {

    private final RuleManager ruleManager = new RuleManager();

    private synchronized ErrorType checkRule(Cell[][] cells) {
        return ruleManager.validateWindow(cells);
    }

    @Test
    public void testChangeDiceValue() {
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
        Cell[][] cells = {{five, one, three, six, three},
                {two, three, four, five, one},
                {six, five, two, one, two},
                {one, two, five, six, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setIncrementDiceFeature().build();
        DTO dto = new DTO();
        dto.setDice(diceFour);
        dto.setIgnoreValueSet(ruleManager.getIgnoreValueSet());
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }

    @Test
    public void testMoveIgnoringColorRuleFeature() {
        CellRule cellRule = CellRule.builder().build();
        Cell _one = new Cell(cellRule);
        Dice diceOne = new Dice(1, Colors.RED);
        diceOne.setValue(1);
        _one.setDice(diceOne);
        Cell _two = new Cell(cellRule);
        Dice diceTwo = new Dice(2, Colors.YELLOW);
        diceOne.setValue(2);
        _two.setDice(diceTwo);
        Cell _three = new Cell(cellRule);
        Dice diceThree = new Dice(3, Colors.GREEN);
        diceThree.setValue(3);
        _three.setDice(diceThree);
        Cell _four = new Cell(cellRule);
        Dice diceFour = new Dice(10, Colors.PURPLE);
        diceFour.setValue(4);
        _four.setDice(diceFour);
        Cell _five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.LIGHT_BLUE);
        diceFive.setValue(5);
        _five.setDice(diceFive);
        CellRule cellRulecolor = CellRule.builder().setColorConstraint(Colors.RED).build();
        Cell empty = new Cell(cellRulecolor);
        Cell[][] cells = {{_five, _one, _three, empty, _three},
                {_two, _three, _four, _five, _one},
                {empty, _five, _two, _one, _two},
                {_one, _two, _five, empty, _three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setMoveIgnoringColorRuleFeature().build();
        DTO dto = new DTO();
        dto.setIgnoreColorSet(ruleManager.getIgnoreColorSet());
        dto.setCurrentPosition(new Position(1, 2));
        dto.setNewPosition(new Position(0, 3));
        dto.setWindowMatrix(cells);
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }

    @Test
    public void testMoveIgnoringValueRuleFeature() {
        CellRule cellRule = CellRule.builder().build();
        Cell __one = new Cell(cellRule);
        Dice diceOne = new Dice(1, Colors.RED);
        diceOne.setValue(1);
        __one.setDice(diceOne);
        Cell __two = new Cell(cellRule);
        Dice diceTwo = new Dice(2, Colors.YELLOW);
        diceOne.setValue(2);
        __two.setDice(diceTwo);
        Cell __three = new Cell(cellRule);
        Dice diceThree = new Dice(3, Colors.GREEN);
        diceThree.setValue(3);
        __three.setDice(diceThree);
        Cell __four = new Cell(cellRule);
        Dice diceFour = new Dice(13, Colors.PURPLE);
        diceFour.setValue(4);
        __four.setDice(diceFour);
        Cell __five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.LIGHT_BLUE);
        diceFive.setValue(5);
        __five.setDice(diceFive);
        CellRule cellRulecolor = CellRule.builder().setNumberConstraint(6).build();
        Cell empty = new Cell(cellRulecolor);
        Cell[][] cells = {{__five, __one, __three, empty, __three},
                {__two, __three, __four, __five, __one},
                {empty, __five, __two, __one, __two},
                {__one, __two, __five, empty, __three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setMoveIgnoringValueRuleFeature().build();
        DTO dto = new DTO();
        dto.setIgnoreValueSet(ruleManager.getIgnoreValueSet());
        dto.setCurrentPosition(new Position(1, 2));
        dto.setNewPosition(new Position(0, 3));
        dto.setWindowMatrix(cells);
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }
}
