package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.List;


/**
 * The Class DiceRoundTrackReconnectionEvent.
 */
public class DiceRoundTrackReconnectionEvent implements Message, ResponseVisitor {
    
    /** The round track. */
    private List<List<Dice>> roundTrack;
    
    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new dice round track reconnection event.
     *
     * @param roundTrack the round track
     * @param playerId the player id
     */
    public DiceRoundTrackReconnectionEvent(List<List<Dice>> roundTrack, String playerId) {
        this.roundTrack = roundTrack;
        this.playerId = playerId;
    }

    /**
     * Gets the round track.
     *
     * @return the round track
     */
    public List<List<Dice>> getRoundTrack() {
        return roundTrack;
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
