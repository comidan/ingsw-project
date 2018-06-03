package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.gui.GUIManager;
import it.polimi.ingsw.sagrada.gui.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.LoginGuiController;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractMatchLobbyRMI;
import it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient extends UnicastRemoteObject implements ClientRMI, Channel<Message, LoginState> {

    private CommandParser commandParser;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private AbstractMatchLobbyRMI lobby;
    private String identifier;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private static final String ADDRESS = getConfigAddress();
    private Client remoteClient;
    private static final Logger LOGGER = Logger.getLogger(RMIClient.class.getName());
    private static LobbyGuiView lobbyGuiView;
    private LoginGuiController loginGuiController;
    private static List<String> playerLobbyListBackup = new ArrayList<>();

    private AbstractServerRMI server;

    public RMIClient(LoginGuiController loginGuiController) throws RemoteException {
        this.loginGuiController = loginGuiController;
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
        LoginState loginState = null;

        while (loginState != LoginState.AUTH_OK) {
            try {
                identifier = LoginGuiController.getUsername();
                System.out.println("Logging in");
                loginState = server.login(this, identifier, LoginGuiController.getPassword());
                System.out.println(loginState);
                if (loginState == LoginState.AUTH_OK) {
                    loginGuiController.dispatch(loginState); //sendMessage(LoginState.AUTH_OK);
                    try {
                        System.out.println("Acquiring lobby");
                        String lobbyId = server.getMatchLobbyId();
                        lobby = (AbstractMatchLobbyRMI) Naming.lookup("rmi://" + ADDRESS + "/" + lobbyId);
                        System.out.println("Lobby acquired");
                        if (lobby.joinLobby(identifier, this)) {
                            System.out.println("Lobby joined");
                        }
                        else
                            System.out.println("Error");
                    } catch (NotBoundException | MalformedURLException exc) {
                        System.out.println("RMI Error");
                    }
                    executeOrders();
                }
                else {
                    loginGuiController.dispatch(loginState); //sendMessage(loginState);
                }
            } catch (IOException e) {
                System.out.println("RMI server error");
                LOGGER.log(Level.SEVERE, () -> e.getMessage());
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

    private static String getConfigAddress() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(NETWORK_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_address");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> "network config fatal error");
            return "";
        }
    }

    public static void setLobbyView(LobbyGuiView lobbyGuiView) {
        RMIClient.lobbyGuiView = lobbyGuiView;
        for(String username : playerLobbyListBackup)
            lobbyGuiView.setPlayer(username);
        playerLobbyListBackup.clear();
    }

    @Override
    public void notifyLobby(String lobbyId) throws RemoteException {
        try {
            System.out.println("Acquiring lobby");
            lobby = (AbstractMatchLobbyRMI) Naming.lookup("rmi://" + ADDRESS + "/" + lobbyId);
            System.out.println("Lobby acquired");
            if (lobby.joinLobby(identifier, this)) {
                System.out.println("Lobby joined");
            }
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
    public void notifyRemoteClientInterface(String id) throws RemoteException {
        try {
            remoteClient = (Client) Naming.lookup("rmi://" + ADDRESS + "/" + id);
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> exc.getMessage());
        }
    }

    @Override
    public void notifyHeartbeatPort(Integer port) throws RemoteException {
        try {
            heartbeatProtocolManager = new HeartbeatProtocolManager(ADDRESS, port, identifier);
            System.out.println("Heartbeat started");
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, () -> exc.getMessage());
        }
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

    @Override
    public void setTimer(String time) throws RemoteException {
        System.out.println("Setting time");
        if(lobbyGuiView != null)
            lobbyGuiView.setTimer(time);

    }

    @Override
    public void setPlayer(String playerName) throws RemoteException {
        System.out.println("Setting player " + playerName);
        if(lobbyGuiView != null)
            lobbyGuiView.setPlayer(playerName);
        else
            playerLobbyListBackup.add(playerName);
    }

    @Override
    public void removePlayer(String playerName) {
        if(lobbyGuiView != null)
            lobbyGuiView.removePlayer(playerName);
    }

    @Override
    public String getId() throws RemoteException {
        return identifier;
    }

    @Override
    public void sendResponse(Message message) throws RemoteException {
        if(message instanceof WindowResponse) {
            new WindowChoiceGuiController(GUIManager.initWindowChoiceGuiView((WindowResponse)message), this);
        }
    }

    @Override
    public void dispatch(Message message) {

    }

    @Override
    public void sendMessage(LoginState message) {
        LoginGuiController.getDynamicRouter().dispatch(message);
    }
}
