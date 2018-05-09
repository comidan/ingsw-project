package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.RuleController;
import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ObjectiveRuleTest {

    Cell one, two, three, four, five, six;
    Cell red, yellow, green, blue, purple;

    public ObjectiveRuleTest() {
        CellRule cellRule = CellRule.builder().build();
        one = new Cell(cellRule);
        one.setDice(new Dice(1, Colors.RED));
        two = new Cell(cellRule);
        two.setDice(new Dice(2, Colors.YELLOW));
        three = new Cell(cellRule);
        three.setDice(new Dice(3, Colors.GREEN));
        four = new Cell(cellRule);
        four.setDice(new Dice(4, Colors.PURPLE));
        five = new Cell(cellRule);
        five.setDice(new Dice(5, Colors.LIGHT_BLUE));
        six = new Cell(cellRule);
        six.setDice(new Dice(6, Colors.LIGHT_BLUE));
        cellRule = CellRule.builder().build();
        red = new Cell(cellRule);
        red.setDice(new Dice(1, Colors.RED));
        yellow = new Cell(cellRule);
        yellow.setDice(new Dice(1, Colors.YELLOW));
        green = new Cell(cellRule);
        green.setDice(new Dice(1, Colors.GREEN));
        purple = new Cell(cellRule);
        purple.setDice(new Dice(1, Colors.PURPLE));
        blue = new Cell(cellRule);
        blue.setDice(new Dice(1, Colors.LIGHT_BLUE));
    }

    private synchronized int checkRule(ObjectiveRule objectiveRule, Cell[][] cells) {
        RuleController ruleController = new RuleController();
        return ruleController.validateRule(objectiveRule, cells);
    }

    @Test
    public void testColorShadeObjective() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        int diceValue = 1;
        List<Color> colorList = Colors.getColorList();
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.RED).build();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(diceValue, colorList.get(j)));
        }
        assertEquals(diceValue * cells.length, checkRule(objectiveRule, cells));
    }

    @Test
    public void testDifferentColorByRows() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        List<Color> colorList = Colors.getColorList();
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceColorByRowsObjective(5).build();
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(1, colorList.get(j)));
            }
        int score = checkRule(objectiveRule, cells);
        assertEquals(20, score);
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(1, colorList.get(0)));
            }
        score = checkRule(objectiveRule, cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentColorByCols() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        List<Color> colorList = Colors.getColorList();
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceColorByColsObjective(5).build();
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[j][i].setDice(new Dice(1, colorList.get(j)));
            }
        int score = checkRule(objectiveRule, cells);
        assertEquals(25, score);
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[j][i].setDice(new Dice(1, colorList.get(0)));
            }
        score = checkRule(objectiveRule, cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentValueByRow() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        int valueObjective = 3;
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByRowsObjective(valueObjective).build();
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setNumberConstraint(j+1).build());
                cells[i][j].setDice(new Dice(j+1, Colors.RED));
            }
        int score = checkRule(objectiveRule, cells);
        assertEquals(valueObjective * cells.length, score);
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setNumberConstraint(j+1 == valueObjective ? j : j+1).build());
                cells[i][j].setDice(new Dice(j+1 == 5 ? j : j+1, Colors.RED));
            }
        score = checkRule(objectiveRule, cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentValueByCol() throws RuntimeException {
        Cell[][] cells = new Cell[4][5];
        int valueObjective = 3;
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByColsObjective(valueObjective).build();
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setNumberConstraint(j+1).build());
                cells[j][i].setDice(new Dice(j+1, Colors.RED));
            }
        int score = checkRule(objectiveRule, cells);
        assertEquals(valueObjective * cells[0].length, score);
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++) {
                cells[j][i] = new Cell(CellRule.builder().setNumberConstraint(j + 1 == valueObjective ? j : j+1).build());
                cells[j][i].setDice(new Dice(j + 1 == valueObjective ? j : j+1, Colors.RED));
            }
        score = checkRule(objectiveRule, cells);
        assertEquals(0, score);
    }

    @Test
    public void testDiagonalColorScore() throws RuntimeException {

        CellRule cellRule = CellRule.builder().build();
        Cell red = new Cell(cellRule);
        red.setDice(new Dice(1, Colors.RED));
        Cell yellow = new Cell(cellRule);
        yellow.setDice(new Dice(1, Colors.YELLOW));
        Cell green = new Cell(cellRule);
        green.setDice(new Dice(1, Colors.GREEN));
        Cell purple = new Cell(cellRule);
        purple.setDice(new Dice(1, Colors.PURPLE));
        Cell blue = new Cell(cellRule);
        blue.setDice(new Dice(1, Colors.LIGHT_BLUE));
        Cell[][] cells = {{red, yellow, green, purple, blue},
                          {blue, red, yellow, green, purple},
                          {purple, blue, red, yellow, green},
                          {green, purple, blue, red, yellow}};
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setSameDiagonalColorObjective().build();
        assertEquals(18, checkRule(objectiveRule, cells));
        Cell[][] _cells = {{red, yellow, green, purple, blue},
                           {blue, purple, yellow, green, purple},
                           {purple, blue, purple, yellow, green},
                           {green, purple, blue, purple, yellow}};
        assertEquals(21, checkRule(objectiveRule, _cells));
        Cell[][] __cells = {{red, yellow, green, purple, blue},
                            {blue, purple, yellow, green, purple},
                            {purple, blue, yellow, blue, green},
                            {green, purple, blue, purple, yellow}};
        assertEquals(16, checkRule(objectiveRule, __cells));
        Cell[][] ___cells = {{red, red, red, red, red},
                             {blue, blue, blue, blue, blue},
                             {yellow, yellow, yellow, yellow, yellow},
                             {green, green, green, green, green}};
        assertEquals(0, checkRule(objectiveRule, ___cells));

    }

    @Test
    public void testValueCoupleScore() throws RuntimeException {
        CellRule cellRule = CellRule.builder().build();
        Cell one = new Cell(cellRule);
        one.setDice(new Dice(1, Colors.RED));
        Cell two = new Cell(cellRule);
        two.setDice(new Dice(2, Colors.YELLOW));
        Cell three = new Cell(cellRule);
        three.setDice(new Dice(3, Colors.GREEN));
        Cell four = new Cell(cellRule);
        four.setDice(new Dice(4, Colors.PURPLE));
        Cell five = new Cell(cellRule);
        five.setDice(new Dice(5, Colors.LIGHT_BLUE));
        Cell six = new Cell(cellRule);
        six.setDice(new Dice(6, Colors.LIGHT_BLUE));
        Cell[][] cells = {{five, one, three, six, six},
                          {two, three, four, four, one},
                          {six, five, two, one, one},
                          {one, two, five, six, three}};
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setValueCoupleObjective(2, 1, 2).build();
        assertEquals(6, checkRule(objectiveRule, cells));
        Cell[][] _cells = {{one, one, one, one, one},
                           {one, one, one, one, one},
                           {one, one, one, one, one},
                           {one, one, one, one, one}};
        assertEquals(0, checkRule(objectiveRule, _cells));
    }

    @Test
    public void testEveryColorRepeatingScore() {
        CellRule cellRule = CellRule.builder().build();
        Cell red = new Cell(cellRule);
        red.setDice(new Dice(1, Colors.RED));
        Cell yellow = new Cell(cellRule);
        yellow.setDice(new Dice(1, Colors.YELLOW));
        Cell green = new Cell(cellRule);
        green.setDice(new Dice(1, Colors.GREEN));
        Cell purple = new Cell(cellRule);
        purple.setDice(new Dice(1, Colors.PURPLE));
        Cell blue = new Cell(cellRule);
        blue.setDice(new Dice(1, Colors.LIGHT_BLUE));
        Cell[][] cells = {{red, yellow, green, purple, blue},
                          {blue, red, yellow, green, purple},
                          {purple, blue, red, yellow, green},
                          {green, purple, blue, red, yellow}};
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setEveryColorRepeatingObjective(4).build();
        assertEquals(16, checkRule(objectiveRule, cells));
        Cell[][] _cells = {{red, yellow, green, purple, blue},
                           {blue, blue, blue, blue, blue},
                           {blue, blue, blue, blue, blue},
                           {blue, blue, blue, blue, blue}};
        assertEquals(4, checkRule(objectiveRule, _cells));
        Cell[][] __cells = {{blue, blue, blue, blue, blue},
                            {blue, blue, blue, blue, blue},
                            {blue, blue, blue, blue, blue},
                            {blue, blue, blue, blue, blue}};
        assertEquals(0, checkRule(objectiveRule, __cells));
    }

    @Test
    public void testEveryValueRepeatingScore() throws RuntimeException {

        Cell[][] cells = {{five, one, three, six, six},
                          {two, three, four, four, one},
                          {six, five, two, one, one},
                          {one, two, five, six, three}};
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setEveryDiceValueRepeatingObjective(5).build();
        assertEquals(10, checkRule(objectiveRule, cells));
        Cell[][] _cells = {{five, one, three, six, six},
                           {two, two, two, two, two},
                           {two, two, two, two, two},
                           {two, two, two, two, two}};
        assertEquals(0, checkRule(objectiveRule, _cells));
    }
}
