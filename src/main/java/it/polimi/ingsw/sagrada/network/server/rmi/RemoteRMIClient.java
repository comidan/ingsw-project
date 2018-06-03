package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Function;

public class RemoteRMIClient extends UnicastRemoteObject implements Client, Serializable {

    private String identifier;
    private Function disconnect;
    private ClientRMI clientRMI;

    public RemoteRMIClient(String identifier, Function disconnect, ClientRMI clientRMI) throws RemoteException{
        this.identifier = identifier;
        this.disconnect = disconnect;
        this.clientRMI = clientRMI;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void disconnect() throws RemoteException {
        disconnect.apply(identifier);
    }

    @Override
    public String getId() throws RemoteException {
        return identifier;
    }

    @Override
    public void sendResponse(Message message) throws RemoteException {
        clientRMI.sendResponse(message); //CHIAMARE I CORRISPETTIVI METODI DA ClientRMI NON COSI'
    }
}
