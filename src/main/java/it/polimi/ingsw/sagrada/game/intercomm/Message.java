package it.polimi.ingsw.sagrada.game.intercomm;

import java.io.Serializable;

public interface Message extends Serializable {
    Class<? extends Message> getType();
}

