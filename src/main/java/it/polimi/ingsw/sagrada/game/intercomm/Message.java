package it.polimi.ingsw.sagrada.game.intercomm;

import java.io.Serializable;

public interface Message extends Serializable {

    long serialVersionUID = 3141592653589793L;

    Class<? extends Message> getType();

    void accept(MessageVisitor messageVisitor);
}