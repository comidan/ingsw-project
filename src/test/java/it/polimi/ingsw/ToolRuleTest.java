package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.DTO;
import it.polimi.ingsw.sagrada.game.base.Position;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertSame;

public class ToolRuleTest {

    final RuleController ruleController = new RuleController();

    private synchronized ErrorType checkRule(Cell[][] cells) {
        return ruleController.validateWindow(cells);
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
        dto.setIgnoreValueSet(ruleController.getIgnoreValueSet());
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }

    @Test
    public void testMoveIgnoringColorRuleFeature() {
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
        Dice diceFour = new Dice(10, Colors.PURPLE);
        diceFour.setValue(4);
        four.setDice(diceFour);
        Cell five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.LIGHT_BLUE);
        diceFive.setValue(5);
        five.setDice(diceFive);
        CellRule cellRulecolor = CellRule.builder().setColorConstraint(Colors.RED).build();
        Cell empty = new Cell(cellRulecolor);
        Cell[][] cells = {{five, one, three, empty, three},
                          {two, three, four, five, one},
                          {empty, five, two, one, two},
                          {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setMoveIgnoringColorRuleFeature().build();
        DTO dto = new DTO();
        dto.setIgnoreColorSet(ruleController.getIgnoreColorSet());
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
        Dice diceFour = new Dice(13, Colors.PURPLE);
        diceFour.setValue(4);
        four.setDice(diceFour);
        Cell five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.LIGHT_BLUE);
        diceFive.setValue(5);
        five.setDice(diceFive);
        CellRule cellRulecolor = CellRule.builder().setNumberConstraint(6).build();
        Cell empty = new Cell(cellRulecolor);
        Cell[][] cells = {{five, one, three, empty, three},
                          {two, three, four, five, one},
                          {empty, five, two, one, two},
                          {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setMoveIgnoringValueRuleFeature().build();
        DTO dto = new DTO();
        dto.setIgnoreValueSet(ruleController.getIgnoreValueSet());
        dto.setCurrentPosition(new Position(1, 2));
        dto.setNewPosition(new Position(0, 3));
        dto.setWindowMatrix(cells);
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
    }
}
