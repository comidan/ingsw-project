package it.polimi.ingsw.sagrada.game.intercomm.message.card;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

import java.util.List;


/**
 * The Class PublicObjectiveResponse.
 */
public class PublicObjectiveResponse implements Message, ResponseVisitor {

    /** The id objective. */
    private List<Integer> idObjective;

    /**
     * Instantiates a new public objective response.
     *
     * @param idObjective the id objective
     */
    public PublicObjectiveResponse(List<Integer> idObjective) {
        this.idObjective = idObjective;
    }

    /**
     * Gets the id objective.
     *
     * @return the id objective
     */
    public List<Integer> getIdObjective() {
        return idObjective;
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
