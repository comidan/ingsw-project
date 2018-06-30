package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The Interface Client.
 */
public interface Client extends Remote {

    /**
     * Start heartbeat.
     *
     * @param port the port
     * @throws RemoteException the remote exception
     */
    void startHeartbeat(int port) throws RemoteException;

    /**
     * Close.
     *
     * @throws RemoteException the remote exception
     */
    void close() throws RemoteException;

    /**
     * Send message.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
    void sendMessage(String message) throws RemoteException;

    /**
     * Send remote message.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
    void sendRemoteMessage(Message message) throws RemoteException;

    /**
     * Disconnect.
     *
     * @throws RemoteException the remote exception
     */
    void disconnect() throws RemoteException;

    /**
     * Send response.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
    void sendResponse(Message message) throws RemoteException;

    /**
     * Gets the id.
     *
     * @return the id
     * @throws RemoteException the remote exception
     */
    String getId() throws RemoteException;

    boolean isInFastRecovery() throws RemoteException;
}