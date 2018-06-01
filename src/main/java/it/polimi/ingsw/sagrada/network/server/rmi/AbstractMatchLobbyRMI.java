package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AbstractMatchLobbyRMI extends Remote {

    void addClient(String identifier) throws RemoteException;

    boolean joinLobby(String token, ClientRMI clientRMI) throws RemoteException;
}
