package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.client.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Function;

public class RemoteRMIClient extends UnicastRemoteObject implements Client, Serializable {

    private String identifier;
    private Function disconnect;

    public RemoteRMIClient(String identifier, Function disconnect) throws RemoteException{
        this.identifier = identifier;
        this.disconnect = disconnect;
    }

    @Override
    public void doActions() throws RemoteException {

    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void close() throws RemoteException {

    }

    @Override
    public void disconnect() throws RemoteException {
        disconnect.apply(identifier);
    }

    @Override
    public void setTimer(String time) throws RemoteException {
    }

    @Override
    public void setPlayer(String playerName) throws RemoteException {

    }

    @Override
    public void removePlayer(String playerName) throws RemoteException {

    }
}
