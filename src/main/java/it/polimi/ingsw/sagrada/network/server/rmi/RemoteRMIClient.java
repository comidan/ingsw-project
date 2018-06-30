package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * The Class RemoteRMIClient.
 */
public class RemoteRMIClient extends UnicastRemoteObject implements Client, Serializable {

    /** The identifier. */
    private String identifier;
    
    /** The disconnect. */
    private Function disconnect;
    
    /** The client RMI. */
    private ClientRMI clientRMI;
    
    /** The send to model. */
    private Consumer<Message> sendToModel;

    /**
     * Instantiates a new remote RMI client.
     *
     * @param identifier the identifier
     * @param disconnect the disconnect
     * @param clientRMI the client RMI
     * @param sendToModel the send to model
     * @throws RemoteException the remote exception
     */
    public RemoteRMIClient(String identifier, Function disconnect, ClientRMI clientRMI, Consumer<Message> sendToModel) throws RemoteException{
        this.identifier = identifier;
        this.disconnect = disconnect;
        this.clientRMI = clientRMI;
        this.sendToModel = sendToModel;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#startHeartbeat(int)
     */
    @Override
    public void startHeartbeat(int port) throws RemoteException {
        throw new UnsupportedOperationException("Method not supported");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#close()
     */
    @Override
    public void close() throws RemoteException {
        throw new UnsupportedOperationException("Method not supported");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendRemoteMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendRemoteMessage(Message message) throws RemoteException {
        new Thread(() -> sendToModel.accept(message)).start();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#disconnect()
     */
    @Override
    public void disconnect() throws RemoteException {
        disconnect.apply(identifier);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#getId()
     */
    @Override
    public String getId() throws RemoteException {
        return identifier;
    }

    @Override
    public boolean isInFastRecovery() throws RemoteException {
        return false;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendResponse(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendResponse(Message message) throws RemoteException {
        clientRMI.sendResponse(message); //CHIAMARE I CORRISPETTIVI METODI DA ClientRMI NON COSI'
    }
}
