package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;

public interface ActionMessageVisitor {

    String visit(DiceEvent diceEvent);

    String visit(WindowEvent windowEvent);

    String visit(EndTurnEvent endTurnEvent);

    String visit(Message message);
}
