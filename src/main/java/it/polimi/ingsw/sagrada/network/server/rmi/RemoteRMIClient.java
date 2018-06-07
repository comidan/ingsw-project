package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

public class RemoteRMIClient extends UnicastRemoteObject implements Client, Serializable {

    private String identifier;
    private Function disconnect;
    private ClientRMI clientRMI;
    private Consumer<Message> sendToModel;
    private Future<Message> a;

    public RemoteRMIClient(String identifier, Function disconnect, ClientRMI clientRMI, Consumer<Message> sendToModel) throws RemoteException{
        this.identifier = identifier;
        this.disconnect = disconnect;
        this.clientRMI = clientRMI;
        this.sendToModel = sendToModel;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void sendRemoteMessage(Message message) throws RemoteException {
        new Thread(() -> sendToModel.accept(message)).start();
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
