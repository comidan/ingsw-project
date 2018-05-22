package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.security.Security;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.tools.LoginManager;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;
import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.ClientRMI;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.DriverManager;

public class RMIClient extends UnicastRemoteObject implements ClientRMI {

    private CommandParser commandParser;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private static final String ADDRESS = "localhost";

    private ServerRMI server;

    public RMIClient() throws RemoteException {

        commandParser = new CommandParser();
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
    }

    private boolean connect() {
        try {
            server = (ServerRMI) Naming.lookup("rmi://" + ADDRESS + "/ServerRMI");
            return true;
        } catch (RemoteException | NotBoundException | MalformedURLException exc) {
            return false;
        }
    }

    private void establishServerConnection() {
        while (!connect())
            try {
                System.out.println("RMI server at " + ADDRESS + " not responding, retrying in 3 seconds...");
                Thread.sleep(SERVER_WAITING_RESPONSE_TIME);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        login();
    }

    private void login() {
        boolean loginSuccessful = false;

        while (!loginSuccessful) {
            try {
                outVideo.println("username:");
                String username = null;
                username = inKeyboard.readLine();
                outVideo.println("password:");
                String auth = null;
                auth = inKeyboard.readLine();
                loginSuccessful = this.server.login(this, username, Security.generateMD5Hash(auth)).equals(LoginManager.LoginState.AUTH_OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
