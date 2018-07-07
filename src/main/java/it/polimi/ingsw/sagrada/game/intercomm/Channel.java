package it.polimi.ingsw.sagrada.game.intercomm;


/**
 * The Interface Channel.
 *
 * @param <R> the generic type
 * @param <T> the generic type
 */
public interface Channel<R extends Message, T extends Message> {
    
    /**
     * Dispatch.
     *
     * @param message the message
     */
    void dispatch(R message);
    
    /**
     * Send message.
     *
     * @param message the message
     */
    void sendMessage(T message);
}
