package it.polimi.ingsw.sagrada.game.intercomm.visitor;

public interface RoundTrackVisitor {
    void accept(RoundTrackMessageVisitor roundTrackMessageVisitor);
}
