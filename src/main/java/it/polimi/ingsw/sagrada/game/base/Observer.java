package it.polimi.ingsw.sagrada.game.base;

public interface Observer<P> {

    void notify(Observable<P> observable , P data);
    void notifyAll(P data);
    boolean subscribe(Observable<P> observable);
    boolean unsubscribe(Observable<P> observable);
}