package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class RoundTrackToolResponse.
 */
public class RoundTrackToolResponse implements Message, ResponseVisitor {

    /** The dice response. */
    private DiceResponse diceResponse;
    
    /** The round number. */
    private int roundNumber;

    /**
     * Instantiates a new round track tool response.
     *
     * @param diceResponse the dice response
     * @param roundNumber the round number
     */
    public RoundTrackToolResponse(DiceResponse diceResponse, int roundNumber) {
        this.diceResponse = diceResponse;
        this.roundNumber = roundNumber;
    }

    /**
     * Gets the dice response.
     *
     * @return the dice response
     */
    public DiceResponse getDiceResponse() {
        return diceResponse;
    }

    /**
     * Gets the round number.
     *
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
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
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
