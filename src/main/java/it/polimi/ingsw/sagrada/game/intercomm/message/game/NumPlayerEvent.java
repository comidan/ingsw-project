package it.polimi.ingsw.sagrada.game.intercomm.message.game;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;


/**
 * The Class NumPlayerEvent.
 */
public class NumPlayerEvent implements Message {
    
    /** The id player. */
    private int idPlayer;

    /**
     * Instantiates a new num player event.
     *
     * @param idPlayer the id player
     */
    public NumPlayerEvent(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public int getIdPlayer() {
        return idPlayer;
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
