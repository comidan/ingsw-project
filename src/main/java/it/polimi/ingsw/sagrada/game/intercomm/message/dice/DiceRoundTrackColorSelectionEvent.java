package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DiceRoundTrackColorSelectionEvent.
 */
public class DiceRoundTrackColorSelectionEvent implements Message, ToolGameVisitor, ActionVisitor {
    
    /** The player id. */
    private String playerId;
    
    /** The constraint. */
    private Colors constraint;

    /**
     * Instantiates a new dice round track color selection event.
     *
     * @param playerId the player id
     * @param constraint the constraint
     */
    public DiceRoundTrackColorSelectionEvent(String playerId, Colors constraint) {
        this.playerId = playerId;
        this.constraint = constraint;
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
     * Gets the constraint.
     *
     * @return the constraint
     */
    public Colors getConstraint() {
        return constraint;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor)
     */
    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) {
        toolGameMessageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
