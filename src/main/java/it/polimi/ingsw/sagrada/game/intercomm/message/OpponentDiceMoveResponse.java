package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class OpponentDiceMoveResponse implements Message {
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
