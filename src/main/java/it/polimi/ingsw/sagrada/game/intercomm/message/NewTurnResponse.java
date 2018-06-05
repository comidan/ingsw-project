package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class NewTurnResponse implements Message {

    private int round;

    public NewTurnResponse(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
