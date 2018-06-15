package it.polimi.ingsw.sagrada.game.intercomm.message.game;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class BeginTurnEvent.
 */
public class BeginTurnEvent implements Message, ResponseVisitor {

    /** The id player. */
    private String idPlayer;

    /**
     * Instantiates a new begin turn event.
     *
     * @param idPlayer the id player
     */
    public BeginTurnEvent(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
