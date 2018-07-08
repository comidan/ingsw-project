package it.polimi.ingsw.sagrada.game.intercomm;



/**
 * The Interface DynamicRouter.
 * This interface is utilised to sort the various messages within the model
 * For example if A wants to send a message M to B, B must subscribe to
 * the DynamicRouter on message M. Then A sends the message and the DynamicRouter
 * takes care to direct it towards the Channel that signed up themselves to the Router
 *
 * @param <T> the generic type
 */
public interface DynamicRouter<T extends Message> {
    
    /**
     * Subscribe Channel for receiving a message
     *
     * @param contentType the type o the message
     * @param channel the channel that subscribes to contentType
     */
    void subscribeChannel(Class<? extends T> contentType, Channel<? extends T, ? extends Message> channel);
    
    /**
     * Dispatch the message to all Channel that put a subscription on that type of message.
     *
     * @param content the message to be sent
     */
    void dispatch(T content);
}