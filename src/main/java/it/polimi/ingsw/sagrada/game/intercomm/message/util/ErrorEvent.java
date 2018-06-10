package it.polimi.ingsw.sagrada.game.intercomm.message.util;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class ErrorEvent implements Message {

    private String error;

    public ErrorEvent(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
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
