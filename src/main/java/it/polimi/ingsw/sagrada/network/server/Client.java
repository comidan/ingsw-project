package it.polimi.ingsw.sagrada.network.server;

interface Client extends Runnable {

    void doActions();

    void close();
}