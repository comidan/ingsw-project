package it.polimi.ingsw.sagrada.game.base;

public interface Observable<P> {

    /**
     * @param data data to be updated
     */
    void update(P data);
}
