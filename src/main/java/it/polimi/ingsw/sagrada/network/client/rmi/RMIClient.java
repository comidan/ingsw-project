package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.gui.LoginGuiController;
import it.polimi.ingsw.sagrada.gui.LoginGuiView;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI;
import it.polimi.ingsw.sagrada.network.server.tools.LoginManager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientRMI {

    private CommandParser commandParser;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private AbstractMatchLobbyRMI lobby;
    private String identifier;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private static final String ADDRESS = Client.getConfigAddress();
    private Client remoteClient;

    private AbstractServerRMI server;

    public RMIClient() throws RemoteException {

        commandParser = new CommandParser();
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        establishServerConnection();
    }

    private boolean connect() {
        try {
            server = (AbstractServerRMI) Naming.lookup("rmi://" + ADDRESS + "/ServerRMI");
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
        LoginManager.LoginState loginSuccessful = null;

        while (loginSuccessful != LoginManager.LoginState.AUTH_OK) {
            try {
                identifier = LoginGuiController.getUsername();
                loginSuccessful = server.login(this, identifier, LoginGuiController.getPassword());
                System.out.println(loginSuccessful);
                if (loginSuccessful == LoginManager.LoginState.AUTH_OK)
                    executeOrders();
            } catch (IOException e) {
                System.out.println("RMI server error");
            }
        }
    }

    private void executeOrders() throws RemoteException {
        int choice;
        while (true) {
            System.out.println("Choose what you wanna do :\n1. Disconnect from server\n2. Send message to server");
            try {
                choice = Integer.parseInt(inKeyboard.readLine());
            } catch (NumberFormatException | IOException exc) {
                continue;
            }
            switch (choice) {
                case 1:
                    remoteClient.disconnect();
                    heartbeatProtocolManager.kill();
                    System.exit(0);
                    return;
                case 2:
                    System.out.println("Write your message");
                    try {
                        remoteClient.sendMessage(inKeyboard.readLine());
                    } catch (IOException exc) {
                        continue;
                    }
                    break;
                default:
                    System.out.println("No actions available for " + choice);
            }
        }

    }

    @Override
    public void notifyLobby(String lobbyId) throws RemoteException {
        try {
            lobby = (AbstractMatchLobbyRMI) Naming.lookup("rmi://" + ADDRESS + "/" + lobbyId);
            lobby.addClient(identifier);
            if (lobby.joinLobby(identifier, this))
                System.out.println("Lobby joined");
            else
                System.out.println("Error");
        } catch (NotBoundException | MalformedURLException exc) {
            System.out.println("RMI Error");
        }
    }

    @Override
    public void signUp() throws RemoteException {
        System.out.println("Signing up");
    }

    @Override
    public void notifyHeartbeatPort(Integer port) throws RemoteException {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, identifier);
        } catch (IOException exc) {
            System.out.println("RMI server error");
        }
    }

    @Override
    public void notifyRemoteClientInterface(Client client) throws RemoteException {
        this.remoteClient = client;
    }

    @Override
    public void doActions() {

    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void close() {

    }

    @Override
    public void disconnect() throws RemoteException {

    }
}
