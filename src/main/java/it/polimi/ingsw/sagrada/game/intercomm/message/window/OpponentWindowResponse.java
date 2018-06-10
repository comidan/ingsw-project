package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpponentWindowResponse implements Message, ResponseVisitor {

    private List<String> players;
    private Map<String, Pair<Integer, WindowSide>> windows;

    public OpponentWindowResponse(List<String> players, List<Integer> windows, List<WindowSide> sides) {
        this.windows = new HashMap<>();
        this.players = players;
        players.forEach(player -> this.windows.put(player,  new Pair<Integer, WindowSide>(windows.get(players.indexOf(player)), sides.get(player.indexOf(player)))));
    }


    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public List<String> getPlayers() {
        return players;
    }

    public Integer getPlayerWindowId(String username) {
        return windows.get(username).getFirstEntry();
    }

    public WindowSide getPlayerWindowSide(String username) {
        return windows.get(username).getSecondEntry();
    }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }

    private static class Pair<T, U> implements Serializable {
        private final T t;
        private final U u;

        Pair(T t, U u) {
            this.t= t;
            this.u= u;
        }

        T getFirstEntry() {
            return t;
        }

        U getSecondEntry() {
            return u;
        }
    }
}
