package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

import java.util.List;

public class WindowResponse implements Message {

    private int playerId;
    private List<Integer> ids;

    public WindowResponse(int playerId, List<Integer> ids) {
        this.playerId = playerId;
        this.ids = ids;
    }

    public int getPlayerId() {
        return playerId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
