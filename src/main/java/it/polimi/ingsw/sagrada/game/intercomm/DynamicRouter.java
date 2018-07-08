package it.polimi.ingsw.sagrada.game.intercomm;



/**
 * The Interface DynamicRouter.
 *
 * @param <T> the generic type
 */
public interface DynamicRouter<T extends Message> {
    
    /**
     * Subscribe channel.
     *
     * @param contentType the content type
     * @param channel the channel
     */
    void subscribeChannel(Class<? extends T> contentType, Channel<? extends T, ? extends Message> channel);
    
    /**
     * Dispatch.
     *
     * @param content the content
     */
    void dispatch(T content);
}