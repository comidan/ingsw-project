package it.polimi.ingsw.sagrada.game.playables;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;


import java.util.*;

/**
 *
 */
public class ScoreTrack {
    private static ScoreTrack scoreTrack;
    private int score;
    private List<ObjectiveCard> objectiveCards;

    private ScoreTrack(List<ObjectiveCard> objectiveCards) {
        score = 0;
        this.objectiveCards=objectiveCards;
    }

    public static ScoreTrack getScoreTrack(List<ObjectiveCard> objectiveCards) {

        if (scoreTrack == null) {
            scoreTrack = new ScoreTrack(objectiveCards);
        }
        return scoreTrack;
    }

    /**
     * @param objective  - objective card to check
     * @param cellMatrix - window
     * @return
     */
    public int calculateScore(List objective, Cell[][] cellMatrix, int tokenNumber) {

        for (Object objectiveRule : objective) {

            score += ((ObjectiveRule) objectiveRule).getScore();

        }

        score += tokenNumber;

        for (int i = 0; i < cellMatrix.length; i++)
            for (int j = 0; j < cellMatrix[0].length; j++) {
                if (!cellMatrix[i][j].isOccupied())
                    score -= 1;
            }

        return score;
    }

}

