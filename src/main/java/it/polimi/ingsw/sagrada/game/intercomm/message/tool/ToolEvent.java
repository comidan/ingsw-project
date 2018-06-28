package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class ToolEvent implements Message, ActionVisitor {
    private String playerId;
    private int toolId;

    public ToolEvent(String playerId, int toolId) {
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
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) { return actionMessageVisitor.visit(this); }
}
