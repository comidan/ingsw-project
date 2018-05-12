package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

public interface Observable<T, D> {

    void notify(T t, D d);
}
