package it.polimi.ingsw.sagrada.game.intercomm;

public interface Message {
    public Class<? extends Message> getType();
}

