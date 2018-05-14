package it.polimi.ingsw.sagrada.game.intercomm;

public interface DynamicRouter<T extends Message> {
    public void subscribeChannel(Class<? extends T> contentType, Channel<? extends T> channel);
    public abstract void dispatch(T content);
}