package it.polimi.ingsw.sagrada.network;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Enum LoginState.
 */
public enum LoginState implements Message {
    
    /** The auth ok. */
    AUTH_OK,
    
    /** The auth failed user already logged. */
    AUTH_FAILED_USER_ALREADY_LOGGED,
    
    /** The auth failed user not exist. */
    AUTH_FAILED_USER_NOT_EXIST,
    
    /** The auth wrong password. */
    AUTH_WRONG_PASSWORD,
    
    /** The auth fatal error. */
    AUTH_FATAL_ERROR;

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
