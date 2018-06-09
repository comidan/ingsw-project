package it.polimi.ingsw.sagrada.network.client;

import java.rmi.RemoteException;

public interface ClientBase extends Client {

    void setTimer(String time) throws RemoteException;

    void setPlayer(String playerName) throws RemoteException;

    void removePlayer(String playerName) throws RemoteException;
}
