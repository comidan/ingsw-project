package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

public class EnableWindowToolResponse implements Message, ResponseVisitor {
    private int toolId;
    private String playerId;

    public EnableWindowToolResponse(String playerId, int toolId) {
        this.playerId = playerId;
        this.toolId = toolId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getToolId() {
        return toolId;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
