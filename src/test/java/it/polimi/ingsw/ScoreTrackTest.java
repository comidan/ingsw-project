


package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.base.*;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.playables.*;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.ToolBuilder;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.*;


public class ScoreTrackTest {


    @Test
    public void TestScoreTrack() {
        GameController gameController = GameController.getGameController();
        DiceController diceController = DiceController.getDiceController(2);
        ToolManager toolManager = new ToolManager();
        Player playerOne = new Player(gameController, toolManager, diceController);
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.RED).build();
        ObjectiveCard card = new ObjectiveCard(1, "test", objectiveRule);
        playerOne.setPrivateObjectiveCard(card);
        WindowParser windowParser = new WindowParser();
        Window window = windowParser.generateWindow(0, WindowSide.FRONT);
        playerOne.setWindow(window);
        int tokenNumber = playerOne.getWindow().getTokenNumber();
        List<ObjectiveRule> objectiveRuleList = new ArrayList<>();
        objectiveRuleList.add(((ObjectiveRule) playerOne.getPrivateObjectiveCard().getRule()));
        Cell[][] cellMatrix = playerOne.getWindow().getCellMatrix();
        ScoreTrack scoreTrack = ScoreTrack.getScoreTrack();

        int objectiveScore = 0;
        for (ObjectiveRule objectiveRuleTest : objectiveRuleList) {
            objectiveScore += objectiveRuleTest.getScore();
        }
        int penalityScore = 0;
        for (int i = 0; i < cellMatrix.length; i++)
            for (int j = 0; j < cellMatrix[0].length; j++) {
                if (!cellMatrix[i][j].isOccupied())
                    penalityScore -= 1;
            }

        assertEquals(penalityScore + tokenNumber + objectiveScore, scoreTrack.calculateScore(objectiveRuleList, playerOne));


    }

}
