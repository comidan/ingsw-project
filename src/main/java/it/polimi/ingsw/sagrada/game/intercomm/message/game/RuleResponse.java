package it.polimi.ingsw.sagrada.game.intercomm.message.game;


import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;



/**
 * The Class RuleResponse.
 */
public class RuleResponse implements Message, ResponseVisitor {

    /** The valid move. */
    private boolean validMove;
    
    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new rule response.
     *
     * @param playerId the player id
     * @param validMove the valid move
     */
    public RuleResponse(String playerId, boolean validMove) {
        this.validMove = validMove;
        this.playerId = playerId;
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

    /**
     * Checks if is move valid.
     *
     * @return true, if is move valid
     */
    public boolean isMoveValid() {
        return validMove;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
