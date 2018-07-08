package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class EnableDoubleTurnToolMessage.
 */
public class EnableDoubleTurnToolMessage implements Message, BaseGameVisitor {
    
    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new enable double turn tool message.
     *
     * @param playerId the player id
     */
    public EnableDoubleTurnToolMessage(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
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
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor)
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}