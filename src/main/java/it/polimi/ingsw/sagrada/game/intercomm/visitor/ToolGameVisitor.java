package it.polimi.ingsw.sagrada.game.intercomm.visitor;


/**
 * The Interface ToolGameVisitor.
 */
public interface ToolGameVisitor {
    /**
     * Visit.
     *
     * @param toolGameMessageVisitor the visitor
     */
    void accept(ToolGameMessageVisitor toolGameMessageVisitor);
}
