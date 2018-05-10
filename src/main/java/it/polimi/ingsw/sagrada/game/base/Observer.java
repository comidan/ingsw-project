package it.polimi.ingsw.sagrada.game.base;

public interface Observer<P> {

    /**
     * @param dataType type of data
     * @param data data to be updated
     */
    void update(DataType dataType, P data);

    /**
     * @param observable add to be observable objects
     */
    boolean subscribe(Observable<P> observable);

    /**
     * @param observable remove from observable objects
     */
    boolean unsubscribe(Observable<P> observable);
}