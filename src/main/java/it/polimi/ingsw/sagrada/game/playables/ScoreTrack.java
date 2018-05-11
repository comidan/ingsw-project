package it.polimi.ingsw.sagrada.game.playables;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;


import java.util.*;

/**
 *
 */
public class ScoreTrack {
    private static ScoreTrack scoreTrack;
    private List<ObjectiveCard> objectiveCards;

    private ScoreTrack(List<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    public static ScoreTrack getScoreTrack(List<ObjectiveCard> objectiveCards) {

        if (scoreTrack == null) {
            scoreTrack = new ScoreTrack(objectiveCards);
        }
        return scoreTrack;
    }

    /**
     * @return score - total score for player
     */
    public int calculateScore(Player player) {
        int score = 0;
        int tokenNumber = player.getWindow().getTokenNumber();
        Cell[][] cellMatrix = player.getWindow().getCellMatrix();
        List<ObjectiveCard> objectives = objectiveCards;
        objectives.add(player.getPrivateObjectiveCard());

        for (ObjectiveCard objectiveCard : objectives) {

            score += objectiveCard.getRule().getScore();

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

