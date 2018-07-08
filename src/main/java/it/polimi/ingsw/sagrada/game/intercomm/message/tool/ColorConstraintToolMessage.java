package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class ColorConstraintToolMessage.
 */
public class ColorConstraintToolMessage implements Message, BaseGameVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;
    
    /** The dice event. */
    private DiceEvent diceEvent;
    
    /** The constraint. */
    private Colors constraint;

    /**
     * Instantiates a new color constraint tool message.
     *
     * @param toolCard the tool card
     * @param diceEvent the dice event
     * @param constraint the constraint
     */
    public ColorConstraintToolMessage(ToolCard toolCard, DiceEvent diceEvent, Colors constraint) {
        this.diceEvent = diceEvent;
        this.constraint = constraint;
        this.toolCard = toolCard;
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
     * Gets the dice event.
     *
     * @return the dice event
     */
    public DiceEvent getDiceEvent() {
        return diceEvent;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor)
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
