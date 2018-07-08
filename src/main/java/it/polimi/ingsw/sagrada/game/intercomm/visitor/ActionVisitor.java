package it.polimi.ingsw.sagrada.game.intercomm.visitor;



/**
 * The Interface ActionVisitor.
 */
public interface ActionVisitor {

    /**
     * Accept.
     *
     * @param actionMessageVisitor the action message visitor
     * @return the string
     */
    String accept(ActionMessageVisitor actionMessageVisitor);
}
