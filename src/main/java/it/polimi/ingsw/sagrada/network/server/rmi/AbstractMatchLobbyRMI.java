package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The Interface AbstractMatchLobbyRMI.
 */
public interface AbstractMatchLobbyRMI extends Remote {

    /**
     * Adds the client.
     *
     * @param identifier the identifier
     * @throws RemoteException the remote exception
     */
    void addClient(String identifier) throws RemoteException;

    /**
     * Join lobby.
     *
     * @param token the token
     * @param clientRMI the client RMI
     * @return true, if successful
     * @throws RemoteException the remote exception
     */
    boolean joinLobby(String token, ClientRMI clientRMI) throws RemoteException;
}
