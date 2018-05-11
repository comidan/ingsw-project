package it.polimi.ingsw.sagrada.game.playables;


import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.RuleController;


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

    public static void destroy() {
        scoreTrack = null;
    }

    /**
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
        RuleController ruleController = new RuleController();
        score = ruleController.validateObjectiveRules(objectiveRules, cellMatrix);
        score += tokenNumber;
        return score;
    }
}

