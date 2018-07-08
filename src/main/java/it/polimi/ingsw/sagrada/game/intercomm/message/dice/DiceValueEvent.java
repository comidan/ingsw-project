package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DiceValueEvent.
 */
public class DiceValueEvent implements Message, DiceManagerVisitor, ActionVisitor {
    
    /** The dice event. */
    private DiceEvent diceEvent;
    
    /** The value. */
    private int value;

    /**
     * Instantiates a new dice value event.
     *
     * @param diceEvent the dice event
     * @param value the value
     */
    public DiceValueEvent(DiceEvent diceEvent, int value) {
        this.diceEvent = diceEvent;
        this.value = value;
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
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
