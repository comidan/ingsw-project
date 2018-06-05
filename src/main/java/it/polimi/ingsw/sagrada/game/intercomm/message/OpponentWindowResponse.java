package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpponentWindowResponse implements Message {

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

    public List<String> getPlayers() {
        return players;
    }

    public Integer getPlayerWindowId(String username) {
        return windows.get(username).getFirstEntry();
    }

    public WindowSide getPlayerWindowSide(String username) {
        return windows.get(username).getSecondEntry();
    }

    private class Pair<T, U> {
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
