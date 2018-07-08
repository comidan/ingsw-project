package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.network.client.rmi.RMIClient;
import it.polimi.ingsw.sagrada.network.client.socket.SocketClient;

import java.io.IOException;
import java.rmi.RemoteException;



/**
 * The Class ClientManager.
 */
public class ClientManager {

    /**
     * Gets the socket client.
     *
     * @return the socket client
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static SocketClient getSocketClient() throws IOException {
        return new SocketClient();
    }

    /**
     * Gets the RMI client.
     *
     * @return the RMI client
     * @throws RemoteException the remote exception
     */
    public static RMIClient getRMIClient() throws RemoteException {
        return new RMIClient();
    }
}
