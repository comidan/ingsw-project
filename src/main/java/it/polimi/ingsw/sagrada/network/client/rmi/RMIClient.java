package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.gui.login.LoginGuiAdapter;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandManager;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient extends UnicastRemoteObject implements ClientRMI, Channel<Message, LoginState> {

    private static final int SERVER_WAITING_RESPONSE_TIME = 3000;
    private static final String NETWORK_CONFIG_PATH = "/json/config/network_config.json";
    private static final Logger LOGGER = Logger.getLogger(RMIClient.class.getName());
    private static final String ADDRESS = getConfigAddress();
    private static final String PROTOCOL = "rmi://";

    private AbstractMatchLobbyRMI lobby;
    private String username;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private Client remoteClient;
    private AbstractServerRMI server;

    public RMIClient() throws RemoteException {
        establishServerConnection();
    }

    private boolean connect() {
        try {
            server = (AbstractServerRMI) Naming.lookup(PROTOCOL + ADDRESS + "/ServerRMI");
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
        LoginState loginState = null;

        while (loginState != LoginState.AUTH_OK) {
            try {
                username = LoginGuiAdapter.getUsername();
                System.out.println("Logging in");
                loginState = server.login(this, username, LoginGuiAdapter.getPassword());
                System.out.println(loginState);
                if (loginState == LoginState.AUTH_OK) {
                    sendMessage(loginState); //sendMessage(LoginState.AUTH_OK);
                    try {
                        System.out.println("Acquiring lobby");
                        String lobbyId = server.getMatchLobbyId();
                        lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
                        System.out.println("Lobby acquired");
                        if (lobby.joinLobby(username, this)) {
                            CommandManager.setClientData(username, this);
                            System.out.println("Lobby joined");
                        }
                        else
                            System.out.println("Error");
                    } catch (NotBoundException | MalformedURLException exc) {
                        LOGGER.log(Level.SEVERE, exc::getMessage);
                    }
                }
                else
                    sendMessage(loginState);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
    }

    private void executeOrders() throws RemoteException {
        int choice;
        while (true) {
            System.out.println("Choose what you wanna do :\n1. Disconnect from server\n2. Send message to server");
            try(Scanner scanner = new Scanner(System.in)) {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exc) {
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
                    try(Scanner scanner = new Scanner(System.in)) {
                        remoteClient.sendMessage(scanner.nextLine());
                    } catch (IOException exc) {
                        continue;
                    }
                    break;
                default:
                    System.out.println("No actions available for " + choice);
            }
        }
    }

    private static String getConfigAddress() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new InputStreamReader(RMIClient.class.getResourceAsStream(NETWORK_CONFIG_PATH)));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_address");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> "network config fatal error");
            return "";
        }
    }

    @Override
    public void notifyLobby(String lobbyId) throws RemoteException {
        try {
            System.out.println("Acquiring lobby");
            lobby = (AbstractMatchLobbyRMI) Naming.lookup(PROTOCOL + ADDRESS + "/" + lobbyId);
            System.out.println("Lobby acquired");
            if (lobby.joinLobby(username, this)) {
                System.out.println("Lobby joined");
            }
            else
                System.out.println("Error");
        } catch (NotBoundException | MalformedURLException exc) {
            System.out.println("RMI Error");
        }
    }

    @Override
    public void signUp() {
        System.out.println("Signing up");
    }

    @Override
    public void notifyRemoteClientInterface(String id) {
        try {
            remoteClient = (Client) Naming.lookup(PROTOCOL + ADDRESS + "/" + id);
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    @Override
    public void notifyHeartbeatPort(Integer port) {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, username);
            System.out.println("Heartbeat started");
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void sendRemoteMessage(Message message) throws RemoteException {
        remoteClient.sendRemoteMessage(message);
    }

    @Override
    public void close() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void setTimer(String time) {
        CommandManager.setTimer(time);

    }

    @Override
    public void startHeartbeat(int port) {

    }

    @Override
    public void setPlayer(String playerName) {
        CommandManager.setPlayer(playerName);
    }

    @Override
    public void removePlayer(String playerName) {
        CommandManager.removePlayer(playerName);
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public void sendResponse(Message message) {
        CommandManager.executePayload(message);
    }

    @Override
    public void dispatch(Message message) {

    }

    @Override
    public void sendMessage(LoginState message) {
        LoginGuiAdapter.getDynamicRouter().dispatch(message);
    }
}
