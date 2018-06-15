package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;


/**
 * The Interface Observable.
 *
 * @param <T> the generic type
 * @param <D> the generic type
 */
public interface Observable<T, D> {

    /**
     * Notify.
     *
     * @param t the t
     * @param d the d
     */
    void notify(T t, D d);
}
