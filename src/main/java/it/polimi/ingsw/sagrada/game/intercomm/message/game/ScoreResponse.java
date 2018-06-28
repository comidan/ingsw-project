package it.polimi.ingsw.sagrada.game.intercomm.message.game;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreResponse implements Message, ResponseVisitor {

    private List<Pair<String, Integer>> ranking;

    public ScoreResponse(List<String> players, List<Integer> scores) {
        ranking = new ArrayList<>();
        players.forEach(player -> ranking.add(new Pair<>(player, scores.get(players.indexOf(player)))));
        ranking.sort(Collections.reverseOrder(Comparator.comparing(Pair::getSecondEntry)));
    }

    public ScoreResponse(List<Pair<String, Integer>> ranking) {
        this.ranking = ranking;
    }

    public int getScore(String username) {
        return ranking.stream().filter(pair -> pair.getFirstEntry().equals(username)).mapToInt(Pair::getSecondEntry).toArray()[0];
    }

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
