package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void sendMessage(String message) throws RemoteException;

    void sendRemoteMessage(Message message) throws RemoteException;

    void disconnect() throws RemoteException;

    void sendResponse(Message message) throws RemoteException;

    String getId() throws RemoteException;
}