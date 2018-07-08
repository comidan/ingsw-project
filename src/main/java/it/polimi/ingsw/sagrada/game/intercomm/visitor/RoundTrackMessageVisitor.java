package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.CompleteSwapDiceToolMessage;


/**
 * The Interface RoundTrackMessageVisitor.
 */
public interface RoundTrackMessageVisitor {
    
    /**
     * Visit.
     *
     * @param diceEvent the dice event
     */
    void visit(DiceEvent diceEvent);

    /**
     * Visit.
     *
     * @param completeSwapDiceToolMessage the complete swap dice tool message
     */
    void visit(CompleteSwapDiceToolMessage completeSwapDiceToolMessage);
}
