package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ChangeDiceValueToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RollAllDiceToolMessage;

public interface DiceManagerMessageVisitor {
    void visit(DiceEvent diceEvent);

    void visit(ChangeDiceValueToolMessage changeDiceValueToolMessage);

    void visit(RollAllDiceToolMessage rollAllDiceToolMessage);
}
