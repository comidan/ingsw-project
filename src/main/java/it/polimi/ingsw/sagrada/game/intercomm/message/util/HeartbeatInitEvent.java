package it.polimi.ingsw.sagrada.game.intercomm.message.util;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class HeartbeatInitEvent.
 */
public class HeartbeatInitEvent implements Message {

    /** The heartbeat port. */
    private int heartbeatPort;

    /**
     * Instantiates a new heartbeat init event.
     *
     * @param heartbeatPort the heartbeat port
     */
    public HeartbeatInitEvent(int heartbeatPort) {
        this.heartbeatPort = heartbeatPort;
    }

    /**
     * Gets the heartbeat port.
     *
     * @return the heartbeat port
     */
    public int getHeartbeatPort() {
        return heartbeatPort;
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
