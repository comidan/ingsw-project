package it.polimi.ingsw.sagrada.game.intercomm.message.util;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class MessageEvent.
 */
public class MessageEvent implements Message {

    /** The message. */
    private String message;

    /**
     * Instantiates a new message event.
     *
     * @param message the message
     */
    public MessageEvent(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
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
