package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class ToolEvent.
 */
public class ToolEvent implements Message, ActionVisitor, ToolGameVisitor {
    
    /** The player id. */
    private String playerId;
    
    /** The tool id. */
    private int toolId;

    /**
     * Instantiates a new tool event.
     *
     * @param playerId the player id
     * @param toolId the tool id
     */
    public ToolEvent(String playerId, int toolId) {
        this.playerId = playerId;
        this.toolId = toolId;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Gets the tool id.
     *
     * @return the tool id
     */
    public int getToolId() {
        return toolId;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) { return actionMessageVisitor.visit(this); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor)
     */
    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) { toolGameMessageVisitor.visit(this); }
}
