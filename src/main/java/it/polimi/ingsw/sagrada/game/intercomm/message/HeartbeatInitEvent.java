package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class HeartbeatInitEvent implements Message {

    private int heartbeatPort;

    public HeartbeatInitEvent(int heartbeatPort) {
        this.heartbeatPort = heartbeatPort;
    }

    public int getHeartbeatPort() {
        return heartbeatPort;
    }


    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
