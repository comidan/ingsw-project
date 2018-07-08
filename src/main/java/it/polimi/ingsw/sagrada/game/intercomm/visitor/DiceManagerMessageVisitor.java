package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceValueEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ChangeDiceValueToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.DraftToBagToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RollAllDiceToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.SwapDiceToolMessage;


/**
 * The Interface DiceManagerMessageVisitor.
 */
public interface DiceManagerMessageVisitor {
    
    /**
     * Visit.
     *
     * @param diceEvent the dice event
     */
    void visit(DiceEvent diceEvent);

    /**
     * Visit.
     *
     * @param changeDiceValueToolMessage the change dice value tool message
     */
    void visit(ChangeDiceValueToolMessage changeDiceValueToolMessage);

    /**
     * Visit.
     *
     * @param rollAllDiceToolMessage the roll all dice tool message
     */
    void visit(RollAllDiceToolMessage rollAllDiceToolMessage);

    /**
     * Visit.
     *
     * @param swapDiceToolMessage the swap dice tool message
     */
    void visit(SwapDiceToolMessage swapDiceToolMessage);

    /**
     * Visit.
     *
     * @param diceValueEvent the dice value event
     */
    void visit(DiceValueEvent diceValueEvent);
}
