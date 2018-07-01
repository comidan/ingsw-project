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
    private Set<Integer> ignoreValueSet;

    public ChangeDiceValueToolMessage(ToolCard toolCard, int diceId, Set<Integer> ignoreValueSet) {
        this.toolCard = toolCard;
        this.diceId = diceId;
        this.ignoreValueSet = ignoreValueSet;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public int getDiceId() {
        return diceId;
    }

    public Set<Integer> getIgnoreValueSet() { return ignoreValueSet; }

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
