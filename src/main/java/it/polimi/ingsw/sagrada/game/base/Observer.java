package it.polimi.ingsw.sagrada.game.base;

public interface Observer<P> {

    /**
     * @param observable object to update
     * @param data data to be updated
     */
    void notify(Observable<P> observable , P data);

    /**
     * @param data data to be updated
     */
    void notifyAll(P data);

    /**
     * @param observable add to be observable objects
     */
    boolean subscribe(Observable<P> observable);

    /**
     * @param observable remove from observable objects
     */
    boolean unsubscribe(Observable<P> observable);
}