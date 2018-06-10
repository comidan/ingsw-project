package it.polimi.ingsw.sagrada.game.intercomm.visitor;

public interface ResponseVisitor {

    String accept(ResponseMessageVisitor responseMessageVisitor);
}
