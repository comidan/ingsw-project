package it.polimi.ingsw.sagrada.game.intercomm.message.game;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.*;
import java.util.stream.Collectors;


/**
 * The Class ScoreResponse.
 */
public class ScoreResponse implements Message, ResponseVisitor {

    /** The ranking. */
    private List<Pair<String, Integer>> ranking;

    /**
     * Instantiates a new score response.
     *
     * @param players the players
     * @param scores the scores
     */
    public ScoreResponse(List<String> players, List<Integer> scores) {
        ranking = new ArrayList<>();
        players.forEach(player -> ranking.add(new Pair<>(player, scores.get(players.indexOf(player)))));
        ranking.sort(Collections.reverseOrder(Comparator.comparing(Pair::getSecondEntry)));
    }

    /**
     * Instantiates a new score response.
     *
     * @param ranking the ranking
     */
    public ScoreResponse(List<Pair<String, Integer>> ranking) {
        this.ranking = ranking;
    }

    /**
     * Gets the score.
     *
     * @param username the username
     * @return the score
     */
    public int getScore(String username) {
        return ranking.stream().filter(pair -> pair.getFirstEntry().equals(username)).mapToInt(Pair::getSecondEntry).toArray()[0];
    }

    /**
     * Gets the usernames.
     *
     * @return the usernames
     */
    public Set<String> getUsernames() {
        return ranking.stream().map(Pair::getFirstEntry).collect(Collectors.toSet());
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /**
     * Accept.
     *
     * @param messageVisitor the message visitor
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /**
     * Accept.
     *
     * @param responseMessageVisitor the response message visitor
     * @return the string
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
