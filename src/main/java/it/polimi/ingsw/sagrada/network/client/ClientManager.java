package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.gui.LoginGuiController;
import it.polimi.ingsw.sagrada.network.client.rmi.RMIClient;
import it.polimi.ingsw.sagrada.network.client.socket.SocketClient;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientManager {

    public static SocketClient getSocketClient() throws IOException {
        return new SocketClient();
    }

    public static RMIClient getRMIClient(LoginGuiController loginGuiController) throws RemoteException {
        return new RMIClient(loginGuiController);
    }
}
