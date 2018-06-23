package it.polimi.ingsw.sagrada.game.intercomm.message.game;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScoreResponse implements Message, ResponseVisitor {

    Map<String, Integer> ranking;

    public ScoreResponse(List<String> players, List<Integer> scores) {
        ranking = new HashMap<>();
        players.forEach(player -> ranking.put(player, scores.get(players.indexOf(player))));
    }

    public ScoreResponse(Map<String, Integer> ranking) {
        this.ranking = ranking;
    }

    public int getScore(String username) {
        return ranking.get(username);
    }

    public Set<String> getUsernames() {
        return ranking.keySet();
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
