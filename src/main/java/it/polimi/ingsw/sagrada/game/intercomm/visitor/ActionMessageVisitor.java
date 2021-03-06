package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
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

    /**
     * Visit.
     *
     * @param diceRoundTrackSelectionEvent the dice round track selection event
     * @return the string
     */
    String visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent);

    /**
     * Visit.
     *
     * @param diceRoundTrackColorSelectionEvent the dice round track color selection event
     * @return the string
     */
    String visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent);

    /**
     * Visit.
     *
     * @param diceValueEvent the dice value event
     * @return the string
     */
    String visit(DiceValueEvent diceValueEvent);

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

    /**
     * Visit.
     *
     * @param toolEvent the tool event
     * @return the string
     */
    String visit(ToolEvent toolEvent);
}
