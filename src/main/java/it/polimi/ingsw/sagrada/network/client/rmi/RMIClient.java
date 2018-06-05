package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.gui.*;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient extends UnicastRemoteObject implements ClientRMI, Channel<Message, LoginState> {

    private static final int SERVER_WAITING_RESPONSE_TIME = 3000;
    private static final String NETWORK_CONFIG_PATH = "src/main/resources/json/config/network_config.json";
    private static final Logger LOGGER = Logger.getLogger(RMIClient.class.getName());
    private static final String ADDRESS = getConfigAddress();
    private static final String PROTOCOL = "rmi://";

    private static List<String> playerLobbyListBackup = new ArrayList<>();
    private static LobbyGuiView lobbyGuiView;

    private CommandParser commandParser;
    private AbstractMatchLobbyRMI lobby;
    private String username;
    private HeartbeatProtocolManager heartbeatProtocolManager;
    private Client remoteClient;
    private LoginGuiManager loginGuiManager;
    private AbstractServerRMI server;
    private static List<String> playerList;
    private WindowChoiceGuiController windowChoiceGuiController;
    private GameGuiManager gameGuiManager;
    private WindowGameManager windowGameManager;

    public RMIClient(LoginGuiManager loginGuiManager) throws RemoteException {
        this.loginGuiManager = loginGuiManager;
        commandParser = new CommandParser();
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
                username = LoginGuiManager.getUsername();
                System.out.println("Logging in");
                loginState = server.login(this, username, LoginGuiManager.getPassword());
                System.out.println(loginState);
                if (loginState == LoginState.AUTH_OK) {
                    loginGuiManager.dispatch(loginState); //sendMessage(LoginState.AUTH_OK);
                    try {
                        System.out.println("Acquiring lobby");
                        String lobbyId = server.getMatchLobbyId();
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
                    executeOrders();
                }
                else {
                    loginGuiManager.dispatch(loginState); //sendMessage(loginState);
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
        playerList = new ArrayList<>(playerLobbyListBackup);
        playerLobbyListBackup.clear();
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
    public void signUp() throws RemoteException {
        System.out.println("Signing up");
    }

    @Override
    public void notifyRemoteClientInterface(String id) throws RemoteException {
        try {
            remoteClient = (Client) Naming.lookup(PROTOCOL + ADDRESS + "/" + id);
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> exc.getMessage());
        }
    }

    @Override
    public void notifyHeartbeatPort(Integer port) throws RemoteException {
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
        return username;
    }

    @Override
    public void sendResponse(Message message) throws RemoteException {
        if(message instanceof WindowResponse) {
            windowChoiceGuiController = new WindowChoiceGuiController(GUIManager.initWindowChoiceGuiView((WindowResponse)message, lobbyGuiView.getStage()), this);
        }
        else if(message instanceof OpponentWindowResponse) {
            if(windowGameManager == null)
                windowGameManager = new WindowGameManager();
            OpponentWindowResponse windows = (OpponentWindowResponse) message;
            List<String> players = windows.getPlayers();
            for(String player : players)
                windowGameManager.addWindow(windows.getPlayerWindowId(player), windows.getPlayerWindowSide(player));
        }
        else if(message instanceof DiceResponse) {
            if(gameGuiManager == null) {
                windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
                gameGuiManager = new GameGuiManager(GameView.getInstance(username,
                                                                        windowChoiceGuiController.getStage(),
                                                                        playerList,
                                                                        (DiceResponse) message,
                                                                        windowGameManager.getWindows()), this);
            }
            else
                gameGuiManager.setDraft((DiceResponse) message);
        }
        else if(message instanceof BeginTurnEvent) {
            if(gameGuiManager == null) {
                windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
                gameGuiManager = new GameGuiManager(GameView.getInstance(username,
                                                                        windowChoiceGuiController.getStage(),
                                                                        playerList,
                                                                        (DiceResponse) message,
                                                                        windowGameManager.getWindows()), this);
            }

            System.out.println("New round notified");
            gameGuiManager.notifyTurn();
        }
        else if(message instanceof RuleResponse)
            gameGuiManager.notifyMoveResponse((RuleResponse) message);
    }

    @Override
    public void dispatch(Message message) {

    }

    @Override
    public void sendMessage(LoginState message) {
        LoginGuiManager.getDynamicRouter().dispatch(message);
    }
}
