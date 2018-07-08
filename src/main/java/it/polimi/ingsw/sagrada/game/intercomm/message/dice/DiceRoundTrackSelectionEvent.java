package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DiceRoundTrackSelectionEvent.
 */
public class DiceRoundTrackSelectionEvent implements Message, ActionVisitor, ToolGameVisitor {
    
    /** The player id. */
    private String playerId;
    
    /** The dice id. */
    private int diceId;
    
    /** The turn. */
    private int turn;

    /**
     * Instantiates a new dice round track selection event.
     *
     * @param playerId the player id
     * @param diceId the dice id
     * @param turn the turn
     */
    public DiceRoundTrackSelectionEvent(String playerId, int diceId, int turn) {
        this.playerId = playerId;
        this.diceId = diceId;
        this.turn = turn;
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
     * Gets the dice id.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
    }

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public int getTurn() {
        return turn;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor)
     */
    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) {
        toolGameMessageVisitor.visit(this);
    }
}
