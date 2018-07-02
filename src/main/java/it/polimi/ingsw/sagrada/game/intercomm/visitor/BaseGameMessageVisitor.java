package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.MoveDiceWindowToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent;

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

    void visit(MoveDiceWindowToolMessage moveDiceWindowToolMessage);
}
