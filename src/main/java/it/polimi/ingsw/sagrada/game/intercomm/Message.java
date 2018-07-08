package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

import java.io.Serializable;



/**
 * The Interface Message.
 */
public interface Message extends Serializable {

    /** The serial version UID. */
    long serialVersionUID = 3141592653589793L;

    /**
     * Gets the type.
     *
     * @return the type
     */
    Class<? extends Message> getType();

    /**
     * Accept.
     *
     * @param messageVisitor the message visitor
     */
    void accept(MessageVisitor messageVisitor);
}