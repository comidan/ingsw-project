package it.polimi.ingsw.sagrada.game.intercomm.message.lobby;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class MatchTimeEvent implements Message {

    private String time;

    public MatchTimeEvent(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
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
