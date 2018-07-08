package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackColorSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;


/**
 * The Interface ToolGameMessageVisitor.
 */
public interface ToolGameMessageVisitor {
    
    /**
     * Visit.
     *
     * @param endTurnEvent the end turn event
     */
    void visit(EndTurnEvent endTurnEvent);

    /**
     * Visit.
     *
     * @param toolEvent the tool event
     */
    void visit(ToolEvent toolEvent);

    /**
     * Visit.
     *
     * @param diceDraftSelectionEvent the dice draft selection event
     */
    void visit(DiceDraftSelectionEvent diceDraftSelectionEvent);

    /**
     * Visit.
     *
     * @param diceRoundTrackSelectionEvent the dice round track selection event
     */
    void visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent);

    /**
     * Visit.
     *
     * @param diceEvent the dice event
     */
    void visit(DiceEvent diceEvent);

    /**
     * Visit.
     *
     * @param diceRoundTrackColorSelectionEvent the dice round track color selection event
     */
    void visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent);
}
