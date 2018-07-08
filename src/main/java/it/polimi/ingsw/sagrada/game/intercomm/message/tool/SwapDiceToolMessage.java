package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class SwapDiceToolMessage.
 */
public class SwapDiceToolMessage implements Message, DiceManagerVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;
    
    /** The draft dice id. */
    private int draftDiceId;
    
    /** The round track dice id. */
    private int roundTrackDiceId;
    
    /** The round number. */
    private int roundNumber;

    /**
     * Instantiates a new swap dice tool message.
     *
     * @param toolCard the tool card
     * @param draftDiceId the draft dice id
     * @param roundTrackDiceId the round track dice id
     * @param roundNumber the round number
     */
    public SwapDiceToolMessage(ToolCard toolCard, int draftDiceId, int roundTrackDiceId, int roundNumber) {
        this.toolCard = toolCard;
        this.draftDiceId = draftDiceId;
        this.roundTrackDiceId = roundTrackDiceId;
        this.roundNumber = roundNumber;
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
     * Gets the draft dice id.
     *
     * @return the draft dice id
     */
    public int getDraftDiceId() {
        return draftDiceId;
    }

    /**
     * Gets the round track dice id.
     *
     * @return the round track dice id
     */
    public int getRoundTrackDiceId() {
        return roundTrackDiceId;
    }

    /**
     * Gets the round number.
     *
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor)
     */
    @Override
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) {
        diceManagerMessageVisitor.visit(this);
    }
}
