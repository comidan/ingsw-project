package it.polimi.ingsw.sagrada.game.intercomm.message.lobby;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class MatchTimeEvent.
 */
public class MatchTimeEvent implements Message {

    /** The time. */
    private String time;

    /**
     * Instantiates a new match time event.
     *
     * @param time the time
     */
    public MatchTimeEvent(String time) {
        this.time = time;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
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
