package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DraftToBagToolMessage.
 */
public class DraftToBagToolMessage implements Message, BaseGameVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;
    
    /** The player id. */
    private String playerId;
    
    /** The dice id. */
    private int diceId;

    /**
     * Instantiates a new draft to bag tool message.
     *
     * @param toolCard the tool card
     * @param playerId the player id
     * @param diceId the dice id
     */
    public DraftToBagToolMessage(ToolCard toolCard, String playerId, int diceId) {
        this.toolCard = toolCard;
        this.playerId = playerId;
        this.diceId = diceId;
    }

    /**
     * Gets the tool card.
     *
     * @return the tool card
     */
    public ToolCard getToolCard() {
        return toolCard;
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
