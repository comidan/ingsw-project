package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.gui.*;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandManager implements MessageVisitor {

    private static final CommandManager commandManager = new CommandManager();
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    private static Client client;
    private static WindowChoiceGuiController windowChoiceGuiController;
    private static GameGuiManager gameGuiManager;
    private static WindowGameManager windowGameManager;
    private static LobbyGuiView lobbyGuiView;
    private static List<String> playerList = new ArrayList<>();
    private static List<String> playerLobbyListBackup = new ArrayList<>();
    private static String username;
    private PrivateObjectiveResponse privateObjectiveResponse;
    private PublicObjectiveResponse publicObjectiveResponse;
    private ToolCardResponse toolCardResponse;

    private CommandManager() {}

    public static void setLobbyGuiView(LobbyGuiView lobbyGuiView) {
        CommandManager.lobbyGuiView = lobbyGuiView;
    }

    public static void setClientData(String username, Client client) {
        CommandManager.username = username;
        CommandManager.client = client;
    }

    public static void executePayload(String json) {
        Message message = JsonMessage.parseJsonData(json);
        message.accept(commandManager);
    }

    public static void executePayload(Message message) {
        message.accept(commandManager);
    }

    public static String createPayload(Message message) {
        JsonMessage jsonMessage = new JsonMessage(username);
        return jsonMessage.getMessage((ActionVisitor) message);
    }

    public static void setPlayer(String playerName) {
        Platform.runLater(() -> {
            if(lobbyGuiView != null) {
                lobbyGuiView.setPlayer(playerName);
                playerList.add(playerName);
            }
            else
                playerLobbyListBackup.add(playerName);
        });
    }

    public static void setTimer(String time) {
        Platform.runLater(() -> {
            if(lobbyGuiView != null)
                lobbyGuiView.setTimer(time);
        });
    }

    public static void removePlayer(String playerName) {
        Platform.runLater(() -> {
            if(lobbyGuiView != null)
                lobbyGuiView.removePlayer(playerName);
        });
    }

    @Override
    public void visit(Message message) {
        LOGGER.log(Level.INFO, message.getType().getName());
    }

    @Override
    public void visit(AddPlayerEvent addPlayerEvent) {
        setPlayer(addPlayerEvent.getUsername());
    }

    @Override
    public void visit(BeginTurnEvent beginTurnEvent) {
        if(gameGuiManager == null) {
            windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
            gameGuiManager = new GameGuiManager(GameView.getInstance(username,
                                                                    windowChoiceGuiController.getStage(),
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            gameGuiManager.setToolCards(toolCardResponse.getIds());
            gameGuiManager.setPublicObjectives(publicObjectiveResponse.getIdObjective());
            gameGuiManager.setPrivateObjective(privateObjectiveResponse.getIdObjective());
        }
        gameGuiManager.notifyTurn();
    }

    @Override
    public void visit(MatchTimeEvent matchTimeEvent) {
        setTimer(matchTimeEvent.getTime());
    }

    @Override
    public void visit(HeartbeatInitEvent heartbeatInitEvent) {
        try {
            client.startHeartbeat(heartbeatInitEvent.getHeartbeatPort());
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    @Override
    public void visit(RemovePlayerEvent removePlayerEvent) {
        removePlayer(removePlayerEvent.getUsername());
    }

    @Override
    public void visit(WindowResponse windowResponse) {
        windowChoiceGuiController = new WindowChoiceGuiController(GUIManager.initWindowChoiceGuiView(windowResponse, lobbyGuiView.getStage()), client);
    }

    @Override
    public void visit(OpponentWindowResponse opponentWindowResponse) {
        if(windowGameManager == null)
            windowGameManager = new WindowGameManager();
        List<String> players = opponentWindowResponse.getPlayers();
        for(String player : players)
            windowGameManager.addWindow(opponentWindowResponse.getPlayerWindowId(player), opponentWindowResponse.getPlayerWindowSide(player));
    }

    @Override
    public void visit(DiceResponse diceResponse) {
        if(gameGuiManager == null) {
            windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
            gameGuiManager = new GameGuiManager(GameView.getInstance(username,
                                                                    windowChoiceGuiController.getStage(),
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            gameGuiManager.setToolCards(toolCardResponse.getIds());
            gameGuiManager.setPublicObjectives(publicObjectiveResponse.getIdObjective());
            gameGuiManager.setPrivateObjective(privateObjectiveResponse.getIdObjective());
            gameGuiManager.notifyEndTurn();
        }
        gameGuiManager.setDiceList(diceResponse);
    }

    @Override
    public void visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        gameGuiManager.setOpponentDiceResponse(opponentDiceMoveResponse);
    }

    @Override
    public void visit(RuleResponse ruleResponse) {
        gameGuiManager.notifyMoveResponse(ruleResponse);
    }

    @Override
    public void visit(NewTurnResponse newTurnResponse) {
        gameGuiManager.setRound(newTurnResponse.getRound());
    }

    @Override
    public void visit(PrivateObjectiveResponse privateObjectiveResponse) {

        this.privateObjectiveResponse = privateObjectiveResponse;
        if(gameGuiManager != null)
            gameGuiManager.setPrivateObjective(privateObjectiveResponse.getIdObjective());
    }

    @Override
    public void visit(PublicObjectiveResponse publicObjectiveResponse) {
        this.publicObjectiveResponse = publicObjectiveResponse;
        if(gameGuiManager != null)
            gameGuiManager.setPublicObjectives(publicObjectiveResponse.getIdObjective());
    }

    @Override
    public void visit(ToolCardResponse toolCardResponse) {
        this.toolCardResponse = toolCardResponse;
        if(gameGuiManager != null)
            gameGuiManager.setToolCards(toolCardResponse.getIds());
    }
}
