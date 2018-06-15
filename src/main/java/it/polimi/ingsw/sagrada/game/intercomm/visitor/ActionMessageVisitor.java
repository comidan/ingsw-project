package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;


/**
 * The Interface ActionMessageVisitor.
 */
public interface ActionMessageVisitor {

    /**
     * Visit.
     *
     * @param diceEvent the dice event
     * @return the string
     */
    String visit(DiceEvent diceEvent);

    /**
     * Visit.
     *
     * @param windowEvent the window event
     * @return the string
     */
    String visit(WindowEvent windowEvent);

    /**
     * Visit.
     *
     * @param endTurnEvent the end turn event
     * @return the string
     */
    String visit(EndTurnEvent endTurnEvent);

    /**
     * Visit.
     *
     * @param message the message
     * @return the string
     */
    String visit(Message message);
}
