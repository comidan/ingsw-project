package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.function.BiConsumer;

public class CompleteSwapDiceToolMessage implements Message, RoundTrackVisitor {
    private BiConsumer<Dice, Dice> draftExchange;
    private Dice draftDice;
    private SwapDiceToolMessage swapDiceToolMessage;

    public CompleteSwapDiceToolMessage(BiConsumer<Dice, Dice> draftExchange, Dice draftDice, SwapDiceToolMessage swapDiceToolMessage) {
        this.draftExchange = draftExchange;
        this.draftDice = draftDice;
        this.swapDiceToolMessage = swapDiceToolMessage;
    }

    public BiConsumer<Dice, Dice> getDraftExchange() {
        return draftExchange;
    }

    public Dice getDraftDice() {
        return draftDice;
    }

    public SwapDiceToolMessage getSwapDiceToolMessage() {
        return swapDiceToolMessage;
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
    public void accept(RoundTrackMessageVisitor roundTrackMessageVisitor) { roundTrackMessageVisitor.visit(this); }
}
