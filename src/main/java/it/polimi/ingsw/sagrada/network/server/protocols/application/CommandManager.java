package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.*;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessage;
import it.polimi.ingsw.sagrada.network.client.socket.SocketClient;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private SocketClient socketClient;
    private WindowChoiceGuiController windowChoiceGuiController;
    private GameGuiManager gameGuiManager;
    private WindowGameManager windowGameManager;
    private static LobbyGuiView lobbyGuiView;
    private static List<String> playerList;
    private static List<String> playerLobbyListBackup;
    private GameView gameView;
    private String username;

    public CommandManager(SocketClient socketClient, String username) {
        this.socketClient = socketClient;
        this.username = username;
        playerList = new ArrayList<>();
        playerLobbyListBackup = new ArrayList<>();
    }

    public void executePayload(String json) throws RemoteException, IOException {
        System.out.println("Receiving json...");
        Message message = JsonMessage.parseJsonData(json);
        System.out.println(message.getType().getName());
        if (message instanceof HeartbeatInitEvent)
            socketClient.startHeartbeat(((HeartbeatInitEvent)message).getHeartbeatPort());
        else if(message instanceof MatchTimeEvent)
            socketClient.setTimer(((MatchTimeEvent)message).getTime());
        else if(message instanceof AddPlayerEvent)
            socketClient.setPlayer(((AddPlayerEvent)message).getUsername());
        else if(message instanceof RemovePlayerEvent)
            socketClient.removePlayer(((RemovePlayerEvent)message).getUsername());
        else if(message instanceof WindowResponse) {
            windowChoiceGuiController = new WindowChoiceGuiController(GUIManager.initWindowChoiceGuiView((WindowResponse)message, lobbyGuiView.getStage()), socketClient);
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
                        windowGameManager.getWindows()), socketClient);
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
                        windowGameManager.getWindows()), socketClient);
            }

            System.out.println("New round notified");
            gameGuiManager.notifyTurn();
        }
        else if(message instanceof OpponentDiceMoveResponse) {
            System.out.println("Opponent Dice:     " +((OpponentDiceMoveResponse) message).getDice().getValue());
            gameGuiManager.setOpponentDiceResponse((OpponentDiceMoveResponse)message);
        }
        else if(message instanceof RuleResponse)
            gameGuiManager.notifyMoveResponse((RuleResponse) message);
    }

    public String createPayload(Message message) {
        JSONObject jsonObject = null;
        if(message instanceof DiceEvent)
            jsonObject = JsonMessage.createDiceEvent((DiceEvent)message);
        else if(message instanceof WindowEvent)
            jsonObject = JsonMessage.createWindowEvent(username,
                                                      ((WindowEvent) message).getIdWindow(),
                                                       WindowSide.sideToString(((WindowEvent)message).getWindowSide()));
        else if(message instanceof EndTurnEvent)
            jsonObject = JsonMessage.createEndTurnEvent((EndTurnEvent)message);
        if(jsonObject == null)
            return CommandKeyword.ERROR;
        else
            return jsonObject.toJSONString();
    }

    private static void setLobbyGuiView(LobbyGuiView lobbyGuiView) {
        CommandManager.lobbyGuiView = lobbyGuiView;
    }

    public static void setLobbyView(LobbyGuiView lobbyGuiView) {
        setLobbyGuiView(lobbyGuiView);
        for(String username : playerLobbyListBackup)
            lobbyGuiView.setPlayer(username);
        playerList.addAll(playerLobbyListBackup);
        playerLobbyListBackup.clear();
    }

    public void setPlayer(String playerName) {
        if(lobbyGuiView != null) {
            lobbyGuiView.setPlayer(playerName);
            playerList.add(playerName);
        }
        else
            playerLobbyListBackup.add(playerName);
    }

    public void setTimer(String time) {
        if(lobbyGuiView != null)
            lobbyGuiView.setTimer(time);
    }

    public void removePlayer(String playerName) {
        if(lobbyGuiView != null)
            lobbyGuiView.removePlayer(playerName);
    }
}
