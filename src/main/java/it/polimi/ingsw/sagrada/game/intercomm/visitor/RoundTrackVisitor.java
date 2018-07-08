package it.polimi.ingsw.sagrada.game.intercomm.visitor;


/**
 * The Interface RoundTrackVisitor.
 */
public interface RoundTrackVisitor {
    
    /**
     * Accept.
     *
     * @param roundTrackMessageVisitor the round track message visitor
     */
    void accept(RoundTrackMessageVisitor roundTrackMessageVisitor);
}
