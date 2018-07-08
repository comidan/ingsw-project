package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class ToolResponse.
 */
public class ToolResponse implements Message, ResponseVisitor {
    
    /** The can buy. */
    private boolean canBuy;
    
    /** The id player. */
    private String idPlayer;
    
    /** The token spent. */
    private int tokenSpent;

    /** The tool id. */
    private int toolId;

    /**
     * Instantiates a new tool response.
     *
     * @param canBuy the can buy
     * @param idPlayer the id player
     * @param tokenSpent the token spent
     * @param toolId the tool id
     */
    public ToolResponse(boolean canBuy, String idPlayer, int tokenSpent, int toolId) {
        this.canBuy = canBuy;
        this.idPlayer = idPlayer;
        this.tokenSpent = tokenSpent;
        this.toolId = toolId;
    }

    /**
     * Checks if is can buy.
     *
     * @return true, if is can buy
     */
    public boolean isCanBuy() {
        return canBuy;
    }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * Gets the token spent.
     *
     * @return the token spent
     */
    public int getTokenSpent() { return tokenSpent; }

    /**
     * Gets the tool id.
     *
     * @return the tool id
     */
    public int getToolId() { return toolId; }

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
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
