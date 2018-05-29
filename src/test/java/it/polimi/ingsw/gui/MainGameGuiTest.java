package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.Constraint;
import it.polimi.ingsw.sagrada.gui.GameView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MainGameGuiTest {

    @Test
    public void testMainGameGui() {
        List<String> players = new ArrayList<>();
        players.add("daniele");
        players.add("test");
        players.add("admin");
        List<Dice> diceList = new ArrayList<>();
        Dice dice = new Dice(1, Colors.LIGHT_BLUE);
        dice.setValue(1);
        diceList.add(dice);
        dice = new Dice(2, Colors.RED);
        dice.setValue(2);
        diceList.add(dice);
        dice = new Dice(3, Colors.GREEN);
        dice.setValue(3);
        diceList.add(dice);
        dice = new Dice(4, Colors.PURPLE);
        dice.setValue(4);
        diceList.add(dice);
        dice = new Dice(5, Colors.YELLOW);
        dice.setValue(5);
        diceList.add(dice);
        DiceResponse diceResponse = new DiceResponse("draft", diceList);
        Constraint[][] constraints = new Constraint[][]{
                {Constraint.WHITE, Constraint.PURPLE, Constraint.RED, Constraint.BLUE, Constraint.YELLOW},
                {Constraint.GREEN, Constraint.WHITE, Constraint.PURPLE, Constraint.YELLOW, Constraint.GREEN},
                {Constraint.WHITE, Constraint.YELLOW, Constraint.BLUE, Constraint.GREEN, Constraint.RED},
                {Constraint.BLUE, Constraint.WHITE, Constraint.RED, Constraint.BLUE, Constraint.WHITE},
        };
        GameView.startGameGUI(players, diceResponse, constraints);
    }
}
