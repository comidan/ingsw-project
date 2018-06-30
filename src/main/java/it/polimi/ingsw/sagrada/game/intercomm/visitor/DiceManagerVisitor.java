package it.polimi.ingsw.sagrada.game.intercomm.visitor;

public interface DiceManagerVisitor {
    /**
     * Visit.
     *
     * @param diceManagerMessageVisitor the visitor
     */
    void accept(DiceManagerMessageVisitor diceManagerMessageVisitor);
}
