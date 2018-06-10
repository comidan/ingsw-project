package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.List;

public class WindowResponse implements Message, ResponseVisitor {

    private String playerId;
    private List<Integer> ids;

    public WindowResponse(String playerId, List<Integer> ids) {
        this.playerId = playerId;
        this.ids = ids;
    }

    public String getPlayerId() {
        return playerId;
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
