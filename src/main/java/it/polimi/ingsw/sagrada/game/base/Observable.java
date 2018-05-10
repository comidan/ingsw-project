package it.polimi.ingsw.sagrada.game.base;

public interface Observable<P> {

    /**
     * @param observer object to update
     * @param dataType type of data
     * @param data data to be updated
     */
    void notify(Observer<P> observer , DataType dataType, P data);

    /**
     * @param dataType type of data
     * @param data data to be updated
     */
    void notifyAll(DataType dataType, P data);

    void setSubscription(Observer<P> observer);
}
