package it.polimi.ingsw.sagrada.game.intercomm.message.util;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class ErrorEvent.
 */
public class ErrorEvent implements Message {

    /** The error. */
    private String error;

    /**
     * Instantiates a new error event.
     *
     * @param error the error
     */
    public ErrorEvent(String error) {
        this.error = error;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

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
