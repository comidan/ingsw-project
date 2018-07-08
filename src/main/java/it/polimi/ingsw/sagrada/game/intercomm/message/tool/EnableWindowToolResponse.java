package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class EnableWindowToolResponse.
 */
public class EnableWindowToolResponse implements Message, ResponseVisitor {
    
    /** The tool id. */
    private int toolId;
    
    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new enable window tool response.
     *
     * @param playerId the player id
     * @param toolId the tool id
     */
    public EnableWindowToolResponse(String playerId, int toolId) {
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
