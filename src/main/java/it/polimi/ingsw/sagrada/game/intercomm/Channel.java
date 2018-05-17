package it.polimi.ingsw.sagrada.game.intercomm;

public interface Channel<R extends Message, T extends Message> {
    void dispatch(R message);
    void sendMessage(T message);
}
