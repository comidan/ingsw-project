package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

import java.util.Set;

public class ChangeDiceValueToolMessage implements Message, DiceManagerVisitor {

    private ToolCard toolCard;
    private int diceId;

    public ChangeDiceValueToolMessage(ToolCard toolCard, int diceId) {
        this.toolCard = toolCard;
        this.diceId = diceId;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public int getDiceId() {
        return diceId;
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
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) { diceManagerMessageVisitor.visit(this); }
}
