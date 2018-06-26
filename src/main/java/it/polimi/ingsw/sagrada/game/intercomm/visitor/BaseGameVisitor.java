package it.polimi.ingsw.sagrada.game.intercomm.visitor;

public interface BaseGameVisitor {

    /**
     * Visit.
     *
     * @param baseGameMessageVisitor the visitor
     */
    void accept(BaseGameMessageVisitor baseGameMessageVisitor);
}
