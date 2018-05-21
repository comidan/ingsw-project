package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;
import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientRMI {

    private static final String ADDRESS = "localhost";

    private ServerRMI server;

    public RMIClient() throws RemoteException {

    }

    private boolean connect() {
        try {
            server = (ServerRMI) Naming.lookup("rmi://" + ADDRESS + "/ServerRMI");
            return true;
        }
        catch (RemoteException|NotBoundException|MalformedURLException exc) {
            return false;
        }
    }

    private void establishServerConnection()  {
        while(!connect())
            try {
                System.out.println("RMI server at " + ADDRESS +" not responding, retrying in 3 seconds...");
                Thread.sleep(SERVER_WAITING_RESPONSE_TIME);
            }
            catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    private void login() {

    }

    @Override
    public void notifyLobby(MatchLobby matchLobby) {
        
    }

    @Override
    public void signUp() {

    }

    @Override
    public void notifyHeartbeatPort(Integer port) {

    }

    @Override
    public void doActions() {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void close() {

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
