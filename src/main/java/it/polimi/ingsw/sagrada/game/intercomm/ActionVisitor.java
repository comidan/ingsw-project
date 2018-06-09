package it.polimi.ingsw.sagrada.game.intercomm;

public interface ActionVisitor {

    String accept(ActionMessageVisitor actionMessageVisitor);
}
