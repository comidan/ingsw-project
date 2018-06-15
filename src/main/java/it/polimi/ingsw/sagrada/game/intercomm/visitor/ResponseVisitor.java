package it.polimi.ingsw.sagrada.game.intercomm.visitor;


/**
 * The Interface ResponseVisitor.
 */
public interface ResponseVisitor {

    /**
     * Accept.
     *
     * @param responseMessageVisitor the response message visitor
     * @return the string
     */
    String accept(ResponseMessageVisitor responseMessageVisitor);
}
