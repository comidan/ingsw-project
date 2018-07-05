package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackColorSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
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
     * @param diceDraftSelectionEvent the dice event
     * @return the string
     */
    String visit(DiceDraftSelectionEvent diceDraftSelectionEvent);

    String visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent);

    String visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent);

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
     * @param byteStreamWindowEvent the window image
     * @return the string
     */
    String visit(ByteStreamWindowEvent byteStreamWindowEvent);

    /**
     * Visit.
     *
     * @param message the message
     * @return the string
     */
    String visit(Message message);

    String visit(ToolEvent toolEvent);
}
