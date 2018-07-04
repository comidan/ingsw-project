package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class SwapDiceToolMessage implements Message, DiceManagerVisitor {
    private ToolCard toolCard;
    private int draftDiceId;
    private int roundTrackDiceId;
    private int roundNumber;

    public SwapDiceToolMessage(ToolCard toolCard, int draftDiceId, int roundTrackDiceId, int roundNumber) {
        this.toolCard = toolCard;
        this.draftDiceId = draftDiceId;
        this.roundTrackDiceId = roundTrackDiceId;
        this.roundNumber = roundNumber;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public int getDraftDiceId() {
        return draftDiceId;
    }

    public int getRoundTrackDiceId() {
        return roundTrackDiceId;
    }

    public int getRoundNumber() {
        return roundNumber;
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
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) {
        diceManagerMessageVisitor.visit(this);
    }
}
