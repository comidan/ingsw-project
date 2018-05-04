package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.cells.Cell;
import it.polimi.ingsw.sagrada.game.cells.CellRule;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveBuilder;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ObjectiveRuleTest {

    @Test
    public void testColorShadeObjective() throws Exception{
        Cell[][] cells = new Cell[4][5];
        int diceValue = 1;
        List<Color> colorList = new ArrayList<Color>();
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.CYAN);
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Color.RED).build();
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(diceValue, colorList.get(j)));
        }
        assertEquals(diceValue * cells.length, objectiveRule.checkRule(cells));
    }

    @Test
    public void testDifferentColorByRows() throws Exception{
        Cell[][] cells = new Cell[4][5];
        List<Color> colorList = new ArrayList<Color>();
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.CYAN);
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceColorByRowsObjective(5).build();
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(1, colorList.get(j)));
            }
        int score = objectiveRule.checkRule(cells);
        assertEquals(20, score);
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[i][j].setDice(new Dice(1, colorList.get(0)));
            }
        score = objectiveRule.checkRule(cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentColorByCols() throws Exception{
        Cell[][] cells = new Cell[4][5];
        List<Color> colorList = new ArrayList<Color>();
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.CYAN);
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceColorByColsObjective(5).build();
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[j][i].setDice(new Dice(1, colorList.get(j)));
            }
        int score = objectiveRule.checkRule(cells);
        assertEquals(25, score);
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setColorConstraint(colorList.get(j)).build());
                cells[j][i].setDice(new Dice(1, colorList.get(0)));
            }
        score = objectiveRule.checkRule(cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentValueByRow() throws Exception{
        Cell[][] cells = new Cell[4][5];
        int valueObjective = 3;
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByRowsObjective(valueObjective).build();
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setNumberConstraint(j+1).build());
                cells[i][j].setDice(new Dice(j+1, Color.RED));
            }
        int score = objectiveRule.checkRule(cells);
        assertEquals(valueObjective * cells.length, score);
        for(int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell(CellRule.builder().setNumberConstraint(j+1 == valueObjective ? j : j+1).build());
                cells[i][j].setDice(new Dice(j+1 == 5 ? j : j+1, Color.RED));
            }
        score = objectiveRule.checkRule(cells);
        assertEquals(0, score);
    }

    @Test
    public void testDifferentValueByCol() throws Exception{
        Cell[][] cells = new Cell[4][5];
        int valueObjective = 3;
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setDifferentDiceValueByColsObjective(valueObjective).build();
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++){
                cells[j][i] = new Cell(CellRule.builder().setNumberConstraint(j+1).build());
                cells[j][i].setDice(new Dice(j+1, Color.RED));
            }
        int score = objectiveRule.checkRule(cells);
        assertEquals(valueObjective * cells[0].length, score);
        for(int i = 0; i < cells[0].length; i++)
            for (int j = 0; j < cells.length; j++) {
                cells[j][i] = new Cell(CellRule.builder().setNumberConstraint(j + 1 == valueObjective ? j : j+1).build());
                cells[j][i].setDice(new Dice(j + 1 == valueObjective ? j : j+1, Color.RED));
            }
        score = objectiveRule.checkRule(cells);
        assertEquals(0, score);
    }
}
