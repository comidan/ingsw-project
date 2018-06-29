package it.polimi.ingsw.rules;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.DiceManager;
import it.polimi.ingsw.sagrada.game.playables.RoundTrack;
import it.polimi.ingsw.sagrada.game.rules.*;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
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
        Dice diceFive = new Dice(5, Colors.BLUE);
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
        Dice diceFive = new Dice(5, Colors.BLUE);
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

    @Test
    public void testMoveOppositeSideDiceFeature() {
        Dice dice = new Dice(42, Colors.YELLOW);
        ToolRule toolRule = ToolRule.builder().setMoveOppositeSideDiceFeature().build();
        DTO dto = new DTO();
        dto.setDice(dice);
        for(int i = 1; i < 7; i++) {
            dice.setValue(i);
            toolRule.checkRule(dto);
            assertEquals(dice.getValue(), 7 - i);
        }
    }

    @Test
    public void testAddNewDiceFeature() {
        CellRule cellRule = CellRule.builder().build();
        Cell one = new Cell(cellRule);
        Dice diceOne = new Dice(1, Colors.RED);
        diceOne.setValue(1);
        one.setDice(diceOne);
        Cell two = new Cell(cellRule);
        Dice diceTwo = new Dice(2, Colors.YELLOW);
        diceOne.setValue(2);
        Cell three = new Cell(cellRule);
        Dice diceThree = new Dice(3, Colors.GREEN);
        diceThree.setValue(3);
        Cell four = new Cell(cellRule);
        Dice diceFour = new Dice(39, Colors.PURPLE);
        diceFour.setValue(4);
        Cell five = new Cell(cellRule);
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
        diceSix.setValue(6);
        six.setDice(diceSix);
        Cell[][] cells = {{five, two, three, four, three},
                          {one, three, four, five, four},
                          {six, five, two, three, two},
                          {one, two, five, four, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        ToolRule toolRule = ToolRule.builder().setAddNewDiceFeature().build();
        DTO dto = new DTO();
        dto.setDice(diceThree);
        dto.setWindowMatrix(cells);
        dto.setNewPosition(new Position(2,3));
        dto.setIgnoreSequenceDice(ruleManager::addIgnoreSequenceDice);
        assertTrue(!three.isOccupied());
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        assertTrue(three.isOccupied());
    }

    @Test
    public void testTwoDiceFeature() {
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
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
        diceSix.setValue(6);
        six.setDice(diceSix);
        CellRule cellRuleColor = CellRule.builder().setNumberConstraint(6).build();
        Cell empty = new Cell(cellRuleColor);
        Cell[][] cells =  { {five, one, three, empty, three},
                            {two, three, four, five, one},
                            {empty, five, two, one, two},
                            {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        DTO dto = new DTO();
        dto.setDice(diceSix);
        dto.setSecondDice(diceSix);
        dto.setNewPosition(new Position(0, 3));
        dto.setSecondNewPosition(new Position(2, 0));
        dto.setWindowMatrix(cells);
        ToolRule toolRule = ToolRule.builder().setAddTwoDiceFeature().build();
        toolRule.checkRule(dto);
        errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        cellRuleColor = CellRule.builder().setNumberConstraint(4).build();
        empty = new Cell(cellRuleColor);
        Cell[][] _cells =  { {five, one, three, empty, three},
                {two, three, four, five, one},
                {empty, five, two, one, two},
                {one, two, five, empty, three}};
        dto = new DTO();
        dto.setDice(diceSix);
        dto.setSecondDice(diceSix);
        dto.setNewPosition(new Position(0, 3));
        dto.setSecondNewPosition(new Position(2, 0));
        dto.setWindowMatrix(_cells);
        errorType = toolRule.checkRule(dto);
        assertSame(ErrorType.MATRIX_ERROR, errorType);
    }

    @Test
    public void testExchangeDraftRoundTrackDiceFeature() {
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
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
        diceSix.setValue(6);
        six.setDice(diceSix);
        Dice diceSix2 = new Dice(7, Colors.RED);
        diceSix2.setValue(6);
        CellRule cellRuleColor = CellRule.builder().setNumberConstraint(6).build();
        Cell empty = new Cell(cellRuleColor);
        Cell[][] cells =  { {five, one, three, empty, three},
                {two, three, four, five, one},
                {empty, five, two, one, two},
                {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        RoundTrack roundTrack = new RoundTrack(new MessageDispatcher());
        DiceManager diceManager = new DiceManager(2, null, new MessageDispatcher());
        diceManager.bagToDraft();
        Dice dice = diceManager.getDraft().get(0);
        roundTrack.addDice(Arrays.asList(diceSix2), 1);
        BiConsumer<Dice, Dice> exchangeDraft = diceManager::exchangeDice;
        BiConsumer<Dice, Dice> exchangeRoundTrack = roundTrack::exchangeDice;
        DTO dto = new DTO();
        dto.setDice(dice);
        dto.setSecondDice(diceSix2);
        dto.setExchangeDraftDice(exchangeDraft);
        dto.setExchangeRoundTrackDice(exchangeRoundTrack);
        ToolRule toolRule = ToolRule.builder().setExchangeDraftRoundTrackDiceFeature().build();
        toolRule.checkRule(dto);
        assertTrue(diceManager.getDraft().contains(diceSix2));
        assertEquals(roundTrack.getDiceFromRound(dice.getColor(), 1), dice);
    }

    @Test
    public void testDiceFromDraftToBag() {
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
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.BLUE);
        diceSix.setValue(6);
        six.setDice(diceSix);
        CellRule cellRuleColor = CellRule.builder().setNumberConstraint(6).build();
        Cell empty = new Cell(cellRuleColor);
        Cell[][] cells =  { {five, one, three, empty, three},
                {two, three, four, five, one},
                {empty, five, two, one, two},
                {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        DiceManager diceManager = new DiceManager(2, null, new MessageDispatcher());
        diceManager.bagToDraft();
        Dice dice = diceManager.getDraft().get(0);
        Consumer<Dice> fromDraftToBag = diceManager::moveDiceFromDraftToBag;
        DTO dto = new DTO();
        dto.setMoveDiceFromDraftToBag(fromDraftToBag);
        dto.setDice(dice);
        dto.setSecondDice(diceSix);
        dto.setImposedDiceValue(diceSix.getValue());
        dto.setNewPosition(new Position(0, 3));
        dto.setWindowMatrix(cells);
        ToolRule toolRule = ToolRule.builder().setFromDraftToBagFeature().build();
        toolRule.checkRule(dto);
        assertTrue(!diceManager.getDraft().contains(dice));
        assertEquals(cells[0][3].getCurrentDice(), diceSix);
    }

    @Test
    public void testMoveSameRoundTrackDiceColor() {
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
        Dice diceFive = new Dice(5, Colors.BLACK);
        diceFive.setValue(5);
        five.setDice(diceFive);
        Cell six = new Cell(cellRule);
        Dice diceSix = new Dice(6, Colors.YELLOW);
        diceSix.setValue(6);
        six.setDice(diceSix);
        CellRule cellRuleColor = CellRule.builder().build();
        Cell empty = new Cell(cellRuleColor);
        Cell[][] cells =  { {five, one, three, empty, three},
                            {two, three, four, five, one},
                            {empty, five, two, one, two},
                            {one, two, five, empty, three}};
        ErrorType errorType = checkRule(cells);
        assertSame(ErrorType.NO_ERROR, errorType);
        RoundTrack roundTrack = new RoundTrack(new MessageDispatcher());
        roundTrack.addDice(Arrays.asList(diceSix), 1);
        DTO dto = new DTO();
        dto.setDice(diceTwo);
        dto.setSecondDice(diceTwo);
        dto.setNewPosition(new Position(0, 3));
        dto.setSecondNewPosition(new Position(2, 0));
        dto.setCurrentPosition(new Position(1, 0));
        dto.setSecondCurrentPosition(new Position(2, 2));
        dto.setImposedColor(diceSix.getColor());
        dto.setWindowMatrix(cells);
        ToolRule toolRule = ToolRule.builder().setMoveSameRoundTrackDiceColorFeature().build();
        toolRule.checkRule(dto);
        assertEquals(cells[0][3].getCurrentDice(), diceTwo);
        assertEquals(cells[2][0].getCurrentDice(), diceTwo);
    }
}
