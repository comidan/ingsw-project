package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.client.ClientBase;

import java.rmi.RemoteException;

public interface ClientRMI extends ClientBase {

    void notifyLobby(String lobbyId) throws RemoteException;

    void signUp() throws RemoteException;

    void notifyRemoteClientInterface(String id) throws RemoteException;

    void notifyHeartbeatPort(Integer port) throws RemoteException;
}
