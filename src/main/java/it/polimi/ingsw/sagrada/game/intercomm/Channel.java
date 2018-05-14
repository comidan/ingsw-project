package it.polimi.ingsw.sagrada.game.intercomm;

public interface Channel<T extends Message> {
    void dispatch(T message);
}
