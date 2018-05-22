package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.client.Client;

import java.rmi.RemoteException;

public interface ClientRMI extends Client{

    void notifyLobby(String lobbyId) throws RemoteException;

    void signUp() throws RemoteException;

    void notifyHeartbeatPort(Integer port) throws RemoteException;

    void notifyRemoteClientInterface(Client client) throws RemoteException;
}
