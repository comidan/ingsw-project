package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.client.ClientBase;

import java.rmi.RemoteException;


/**
 * The Interface ClientRMI.
 */
public interface ClientRMI extends ClientBase {

    /**
     * Notify lobby.
     *
     * @param lobbyId the lobby id
     * @throws RemoteException the remote exception
     */
    void notifyLobby(String lobbyId) throws RemoteException;

    /**
     * Sign up.
     *
     * @throws RemoteException the remote exception
     */
    void signUp() throws RemoteException;

    /**
     * Notify remote client interface.
     *
     * @param id the id
     * @throws RemoteException the remote exception
     */
    void notifyRemoteClientInterface(String id) throws RemoteException;

    /**
     * Notify heartbeat port.
     *
     * @param port the port
     * @throws RemoteException the remote exception
     */
    void notifyHeartbeatPort(Integer port) throws RemoteException;
}
