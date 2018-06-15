package it.polimi.ingsw.sagrada.network.client;

import java.rmi.RemoteException;


/**
 * The Interface ClientBase.
 */
public interface ClientBase extends Client {

    /**
     * Sets the timer.
     *
     * @param time the new timer
     * @throws RemoteException the remote exception
     */
    void setTimer(String time) throws RemoteException;

    /**
     * Sets the player.
     *
     * @param playerName the new player
     * @throws RemoteException the remote exception
     */
    void setPlayer(String playerName) throws RemoteException;

    /**
     * Removes the player.
     *
     * @param playerName the player name
     * @throws RemoteException the remote exception
     */
    void removePlayer(String playerName) throws RemoteException;
}
