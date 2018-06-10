package it.polimi.ingsw.sagrada.game.intercomm.visitor;

public interface ActionVisitor {

    String accept(ActionMessageVisitor actionMessageVisitor);
}
