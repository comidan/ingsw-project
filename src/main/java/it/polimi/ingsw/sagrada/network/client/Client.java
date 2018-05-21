package it.polimi.ingsw.sagrada.network.client;

public interface Client extends Runnable {

    int SERVER_WAITING_RESPONSE_TIME = 3000;

    void doActions();

    void sendMessage(String message);

    void close();
}