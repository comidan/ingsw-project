package it.polimi.ingsw.sagrada.game.intercomm.message.card;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.List;

public class PublicObjectiveResponse implements Message, ResponseVisitor {

    private List<Integer> idObjective;

    public PublicObjectiveResponse(List<Integer> idObjective) {
        this.idObjective = idObjective;
    }

    public List<Integer> getIdObjective() {
        return idObjective;
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