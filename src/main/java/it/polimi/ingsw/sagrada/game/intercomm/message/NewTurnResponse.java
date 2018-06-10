package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseVisitor;

public class NewTurnResponse implements Message, ResponseVisitor {

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

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
