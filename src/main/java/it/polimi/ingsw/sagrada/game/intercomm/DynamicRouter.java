package it.polimi.ingsw.sagrada.game.intercomm;

public interface DynamicRouter<T extends Message> {
    void subscribeChannel(Class<? extends T> contentType, Channel<? extends T, ? extends Message> channel);
    void dispatch(T content);
}