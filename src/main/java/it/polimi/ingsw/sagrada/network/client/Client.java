package it.polimi.ingsw.sagrada.network.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    int SERVER_WAITING_RESPONSE_TIME = 3000;

    void doActions() throws RemoteException;

    void sendMessage(String message) throws RemoteException;

    void close() throws RemoteException;

    void disconnect() throws RemoteException;
}