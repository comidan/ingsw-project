package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class DiceValueEvent implements Message, DiceManagerVisitor, ActionVisitor {
    private DiceEvent diceEvent;
    private int value;

    public DiceValueEvent(DiceEvent diceEvent, int value) {
        this.diceEvent = diceEvent;
        this.value = value;
    }

    public DiceEvent getDiceEvent() {
        return diceEvent;
    }

    public int getValue() {
        return value;
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

    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
