package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;
import it.polimi.ingsw.sagrada.network.server.tools.LoginManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AbstractServerRMI extends Remote {

    LoginManager.LoginState login(ClientRMI clientRMI, String username, String hashedPassword) throws RemoteException;
}
