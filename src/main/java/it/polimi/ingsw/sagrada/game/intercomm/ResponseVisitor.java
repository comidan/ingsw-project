package it.polimi.ingsw.sagrada.game.intercomm;

public interface ResponseVisitor {

    String accept(ResponseMessageVisitor responseMessageVisitor);
}
