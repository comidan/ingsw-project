package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.function.BiConsumer;


/**
 * The Class CompleteSwapDiceToolMessage.
 */
public class CompleteSwapDiceToolMessage implements Message, RoundTrackVisitor {
    
    /** The draft exchange. */
    private BiConsumer<Dice, Dice> draftExchange;
    
    /** The draft dice. */
    private Dice draftDice;
    
    /** The swap dice tool message. */
    private SwapDiceToolMessage swapDiceToolMessage;

    /**
     * Instantiates a new complete swap dice tool message.
     *
     * @param draftExchange the draft exchange
     * @param draftDice the draft dice
     * @param swapDiceToolMessage the swap dice tool message
     */
    public CompleteSwapDiceToolMessage(BiConsumer<Dice, Dice> draftExchange, Dice draftDice, SwapDiceToolMessage swapDiceToolMessage) {
        this.draftExchange = draftExchange;
        this.draftDice = draftDice;
        this.swapDiceToolMessage = swapDiceToolMessage;
    }

    /**
     * Gets the draft exchange.
     *
     * @return the draft exchange
     */
    public BiConsumer<Dice, Dice> getDraftExchange() {
        return draftExchange;
    }

    /**
     * Gets the draft dice.
     *
     * @return the draft dice
     */
    public Dice getDraftDice() {
        return draftDice;
    }

    /**
     * Gets the swap dice tool message.
     *
     * @return the swap dice tool message
     */
    public SwapDiceToolMessage getSwapDiceToolMessage() {
        return swapDiceToolMessage;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor)
     */
    @Override
    public void accept(RoundTrackMessageVisitor roundTrackMessageVisitor) { roundTrackMessageVisitor.visit(this); }
}
