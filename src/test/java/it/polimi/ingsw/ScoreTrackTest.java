


package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.playables.ScoreTrack;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.junit.Test;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Cell;


import java.util.*;

import static org.junit.Assert.*;


public class ScoreTrackTest {


    @Test
    public void TestScoreTrack() {


        ScoreTrack scoreTrack = ScoreTrack.getScoreTrack();
        ObjectiveRule objectiveRule = ObjectiveRule.builder().setColorShadeColorObjective(Colors.RED).build();
        List<ObjectiveRule> objectiveRuleList = new ArrayList<>();
        objectiveRuleList.add(objectiveRule);
        Window window = new Window();
        Cell[][] cellMatrix = window.getCellMatrix();

        int tokenNumber = 3; //TEMPORARY FOR TESTING

        int objectiveScore = 0;
        for (ObjectiveRule objectiveRuleTest : objectiveRuleList) {
            objectiveScore += objectiveRuleTest.getScore();
        }
        int penalityScore = 0;
/*        for (int i = 0; i < cellMatrix.length; i++)
            for (int j = 0; j < cellMatrix[0].length; j++) {
                if (!cellMatrix[i][j].isOccupied())
                    penalityScore -= 1;
            }

        assertEquals(penalityScore + tokenNumber + objectiveScore, scoreTrack.calculateScore(objectiveRuleList, cellMatrix, tokenNumber));
*/

    }

}
