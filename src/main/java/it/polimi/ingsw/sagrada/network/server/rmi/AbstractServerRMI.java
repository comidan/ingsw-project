package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The Interface AbstractServerRMI.
 */
public interface AbstractServerRMI extends Remote {

    /**
     * Login.
     *
     * @param clientRMI the client RMI
     * @param username the username
     * @param hashedPassword the hashed password
     * @return the login state
     * @throws RemoteException the remote exception
     */
    LoginState login(ClientRMI clientRMI, String username, String hashedPassword) throws RemoteException;

    /**
     * Gets the match lobby id.
     *
     * @return the match lobby id
     * @throws RemoteException the remote exception
     */
    String getMatchLobbyId() throws RemoteException;


}
