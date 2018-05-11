package it.polimi.ingsw.sagrada.game.playables;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Player;
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

    private ScoreTrack() {
        score = 0;

    }

    public static ScoreTrack getScoreTrack() {

        if (scoreTrack == null) {
            scoreTrack = new ScoreTrack();
        }
        return scoreTrack;
    }

    /**
     * @param objective - objective card to check
     * @return score - total score for player
     */
    public int calculateScore(List objective, Player player) {
        int tokenNumber = player.getWindow().getTokenNumber();
        Cell[][] cellMatrix = player.getWindow().getCellMatrix();

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

