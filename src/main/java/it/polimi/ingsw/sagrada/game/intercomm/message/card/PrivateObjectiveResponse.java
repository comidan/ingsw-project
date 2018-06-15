package it.polimi.ingsw.sagrada.game.intercomm.message.card;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class PrivateObjectiveResponse.
 */
public class PrivateObjectiveResponse implements Message, ResponseVisitor {

    /** The id objective. */
    private int idObjective;
    
    /** The id player. */
    private String idPlayer;

    /**
     * Instantiates a new private objective response.
     *
     * @param idObjective the id objective
     * @param idPlayer the id player
     */
    public PrivateObjectiveResponse(int idObjective, String idPlayer) {
        this.idObjective = idObjective;
        this.idPlayer = idPlayer;
    }

    /**
     * Gets the id objective.
     *
     * @return the id objective
     */
    public int getIdObjective() {
        return idObjective;
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
