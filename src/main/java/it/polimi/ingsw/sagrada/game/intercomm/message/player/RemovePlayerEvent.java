package it.polimi.ingsw.sagrada.game.intercomm.message.player;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class RemovePlayerEvent.
 */
public class RemovePlayerEvent implements Message {

    /** The username. */
    private String username;

    /**
     * Instantiates a new removes the player event.
     *
     * @param username the username
     */
    public RemovePlayerEvent(String username) {
        this.username = username;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
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
