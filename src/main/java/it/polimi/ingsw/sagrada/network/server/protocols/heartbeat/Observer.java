package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;


/**
 * The Interface Observer.
 *
 * @param <T> the generic type
 * @param <D> the generic type
 */
public interface Observer<T, D> {

    /**
     * Update.
     *
     * @param t the t
     * @param d the d
     */
    void update(T t, D d);
}
