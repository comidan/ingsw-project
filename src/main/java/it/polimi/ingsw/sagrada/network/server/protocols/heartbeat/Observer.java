package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

public interface Observer<T, D> {

    void update(T t, D d);
}
