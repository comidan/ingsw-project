package it.polimi.ingsw.sagrada.game.intercomm;

public interface Message {
    Class<? extends Message> getType();
}

