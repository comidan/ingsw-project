package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;



/**
 * The Class OpponentDiceMoveResponse.
 */
public class OpponentDiceMoveResponse implements Message, ResponseVisitor {

    /** The id player. */
    private String idPlayer;
    
    /** The dice. */
    private Dice dice;
    
    /** The position. */
    private Position position;

    /**
     * Instantiates a new opponent dice move response.
     *
     * @param idPlayer the id player
     * @param dice the dice
     * @param position the position
     */
    public OpponentDiceMoveResponse(String idPlayer, Dice dice, Position position) {
        this.idPlayer = idPlayer;
        this.dice = dice;
        this.position = position;
    }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * Gets the dice.
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition() {
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
