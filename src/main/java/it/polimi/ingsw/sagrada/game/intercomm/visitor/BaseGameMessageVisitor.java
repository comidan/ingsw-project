package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent;


/**
 * The Interface BaseGameMessageVisitor.
 */
public interface BaseGameMessageVisitor {

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(Message message);

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(ByteStreamWindowEvent message);

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(WindowGameManagerEvent message);

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(DiceGameManagerEvent message);

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(EndTurnEvent message);

    /**
     * Visit.
     *
     * @param moveDiceWindowToolMessage the move dice window tool message
     */
    void visit(MoveDiceWindowToolMessage moveDiceWindowToolMessage);

    /**
     * Visit.
     *
     * @param moveDiceToolMessage the move dice tool message
     */
    void visit(MoveDiceToolMessage moveDiceToolMessage);

    /**
     * Visit.
     *
     * @param enableDoubleTurnToolMessage the enable double turn tool message
     */
    void visit(EnableDoubleTurnToolMessage enableDoubleTurnToolMessage);

    /**
     * Visit.
     *
     * @param moveAloneDiceToolMessage the move alone dice tool message
     */
    void visit(MoveAloneDiceToolMessage moveAloneDiceToolMessage);

    /**
     * Visit.
     *
     * @param colorConstraintToolMessage the color constraint tool message
     */
    void visit(ColorConstraintToolMessage colorConstraintToolMessage);

    /**
     * Visit.
     *
     * @param draftToBagToolMessage the draft to bag tool message
     */
    void visit(DraftToBagToolMessage draftToBagToolMessage);
}
