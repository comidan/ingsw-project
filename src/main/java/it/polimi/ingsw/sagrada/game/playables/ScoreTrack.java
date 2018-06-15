package it.polimi.ingsw.sagrada.game.playables;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;


import java.util.*;


/**
 * The Class ScoreTrack.
 */
public class ScoreTrack {
    
    /** The score track. */
    private static ScoreTrack scoreTrack;
    
    /** The objective cards. */
    private List<ObjectiveCard> objectiveCards;

    /**
     * Instantiates a new score track.
     *
     * @param objectiveCards the objective cards
     */
    private ScoreTrack(List<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    /**
     * Gets the score track.
     *
     * @param objectiveCards the objective cards
     * @return the score track
     */
    public static ScoreTrack getScoreTrack(List<ObjectiveCard> objectiveCards) {

        if (scoreTrack == null) {
            scoreTrack = new ScoreTrack(objectiveCards);
        }
        return scoreTrack;
    }

    /**
     * Destroy.
     */
    public static void destroy() {
        scoreTrack = null;
    }

    /**
     * Calculate score.
     *
     * @param player the player
     * @return score - total score for player
     */
    public int calculateScore(Player player) {
        int score;
        int tokenNumber = player.getWindow().getTokenNumber();
        Cell[][] cellMatrix = player.getWindow().getCellMatrix();
        List<ObjectiveCard> objectives = objectiveCards;
        List<ObjectiveRule> objectiveRules = new ArrayList<>();
        objectives.add(player.getPrivateObjectiveCard());
        objectives.forEach(objective -> objectiveRules.add(objective.getRule()));
        RuleManager ruleManager = new RuleManager();
        score = ruleManager.validateObjectiveRules(objectiveRules, cellMatrix);
        score += tokenNumber;
        return score;
    }
}

