package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.FirstToolMessage;

public interface DiceManagerMessageVisitor {
    void visit(DiceEvent diceEvent);

    void visit(FirstToolMessage firstToolMessage);
}
