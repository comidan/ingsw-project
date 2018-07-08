package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;


/**
 * The Class ColorBagToolResponse.
 */
public class ColorBagToolResponse implements Message, ResponseVisitor {
    
    /** The player id. */
    private String playerId;
    
    /** The color. */
    private Colors color;
    
    /** The dice id. */
    private int diceId;

    /**
     * Instantiates a new color bag tool response.
     *
     * @param playerId the player id
     * @param color the color
     * @param diceId the dice id
     */
    public ColorBagToolResponse(String playerId, Colors color, int diceId) {
        this.playerId = playerId;
        this.color = color;
        this.diceId = diceId;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Colors getColor() {
        return color;
    }

    /**
     * Gets the dice id.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
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
