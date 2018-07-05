package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class ColorConstraintToolMessage implements Message, BaseGameVisitor {
    private ToolCard toolCard;
    private DiceEvent diceEvent;
    private Colors constraint;

    public ColorConstraintToolMessage(ToolCard toolCard, DiceEvent diceEvent, Colors constraint) {
        this.diceEvent = diceEvent;
        this.constraint = constraint;
        this.toolCard = toolCard;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public DiceEvent getDiceEvent() {
        return diceEvent;
    }

    public Colors getConstraint() {
        return constraint;
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
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
