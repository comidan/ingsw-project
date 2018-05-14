package it.polimi.ingsw.sagrada.game.intercomm;

public interface Channel<T extends Message> {
    public void dispatch(T message);
}
