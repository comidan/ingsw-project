package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.*;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.*;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ScoreTrackTest {


    @Test
    public void testScoreTrack() {
        Player playerOne = new Player(0);
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.RED).build();
        ObjectiveCard card = new ObjectiveCard(1, "test", objectiveRule);
        ObjectiveRule objectiveRulePublic = ObjectiveRule.builder().setValueCoupleObjective(2, 5, 6).build();
        ObjectiveCard cardPublic = new ObjectiveCard(2, "testPublic", objectiveRulePublic);
        playerOne.setPrivateObjectiveCard(card);
        WindowManager windowManager = new WindowManager();
        Window window = windowManager.generateWindow(0, WindowSide.FRONT);
        playerOne.setWindow(window);
        List<ObjectiveRule> objectiveRuleList = new ArrayList<>();
        objectiveRuleList.add(playerOne.getPrivateObjectiveCard().getRule());
        List<ObjectiveCard> objectivesPublic = new ArrayList<>();
        objectivesPublic.add(cardPublic);
        ScoreTrack.destroy();
        ScoreTrack scoreTrack = ScoreTrack.getScoreTrack(objectivesPublic);
        Dice dice = new Dice(23, Colors.RED);
        window.setCell(dice, 2, 2);
        int score = scoreTrack.calculateScore(playerOne);
        assertEquals(-15, score);
    }
}
