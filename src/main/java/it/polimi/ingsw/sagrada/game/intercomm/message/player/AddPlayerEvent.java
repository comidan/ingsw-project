package it.polimi.ingsw.sagrada.game.intercomm.message.player;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class AddPlayerEvent.
 */
public class AddPlayerEvent implements Message {

    /** The username. */
    private String username;

    /** The position. */
    private int position;

    /**
     * Instantiates a new adds the player event.
     *
     * @param username the username
     * @param position the position
     */
    public AddPlayerEvent(String username, int position) {
        this.username = username;
        this.position = position;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
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
