package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;

public interface ActionMessageVisitor {

    String visit(DiceEvent diceEvent);

    String visit(WindowEvent windowEvent);

    String visit(EndTurnEvent endTurnEvent);

    String visit(Message message);
}
