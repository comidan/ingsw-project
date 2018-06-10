package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseVisitor;

import java.util.List;

public class ToolCardResponse implements Message, ResponseVisitor {

    private List<Integer> ids;

    public ToolCardResponse(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
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
