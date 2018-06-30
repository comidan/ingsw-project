package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.game.GameGuiAdapter;
import it.polimi.ingsw.sagrada.gui.game.GameView;
import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.score.ScoreLobbyView;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.gui.windows.WindowGameManager;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class CommandManager.
 */
public class CommandManager implements MessageVisitor {

    /** The Constant commandManager. */
    private static final CommandManager commandManager = new CommandManager();
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    /** The client. */
    private static Client client;
    
    /** The window choice gui controller. */
    private static WindowChoiceGuiController windowChoiceGuiController;
    
    /** The game gui adapter. */
    private static GameGuiAdapter gameGuiAdapter;
    
    /** The window game manager. */
    private static WindowGameManager windowGameManager;
    
    /** The lobby gui view. */
    private static LobbyGuiView lobbyGuiView;

    /** The score view. */
    private static ScoreLobbyView scoreLobbyView;
    
    /** The player list. */
    private static List<String> playerList = new ArrayList<>();
    
    /** The player lobby list backup. */
    private static List<String> playerLobbyListBackup = new ArrayList<>();
    
    /** The username. */
    private static String username;

    private static Stage stage;
    
    /** The private objective response. */
    private PrivateObjectiveResponse privateObjectiveResponse;
    
    /** The public objective response. */
    private PublicObjectiveResponse publicObjectiveResponse;
    
    /** The tool card response. */
    private ToolCardResponse toolCardResponse;

    /**
     * Instantiates a new command manager.
     */
    private CommandManager() {}

    /**
     * Sets the lobby gui view.
     *
     * @param lobbyGuiView the new lobby gui view
     */
    public static void setLobbyGuiView(LobbyGuiView lobbyGuiView) {
        CommandManager.lobbyGuiView = lobbyGuiView;
        stage = lobbyGuiView.getStage();
    }

    public static void setFutureStage(Stage stage) {
        CommandManager.stage = stage;
    }

    /**
     * Sets the client data.
     *
     * @param username the username
     * @param client the client
     */
    public static void setClientData(String username, Client client) {
        CommandManager.username = username;
        CommandManager.client = client;
    }

    /**
     * Execute payload.
     *
     * @param json the json
     */
    public static void executePayload(String json) {
        Message message = JsonMessage.parseJsonData(json);
        message.accept(commandManager);
    }

    /**
     * Execute payload.
     *
     * @param message the message
     */
    public static void executePayload(Message message) {
        message.accept(commandManager);
    }

    /**
     * Creates the payload.
     *
     * @param message the message
     * @return the string
     */
    public static String createPayload(Message message) {
        JsonMessage jsonMessage = new JsonMessage(username);
        return jsonMessage.getMessage((ActionVisitor) message);
    }

    /**
     * Sets the player.
     *
     * @param playerName the new player
     */
    public static void setPlayer(String playerName, int position) {
        Platform.runLater(() -> {
            if(lobbyGuiView != null) {
                lobbyGuiView.setPlayer(playerName, position);
                if(!playerList.contains(playerName))
                    playerList.add(playerName);
            }
            else
                playerLobbyListBackup.add(playerName);
        });
    }

    /**
     * Sets the timer.
     *
     * @param time the new timer
     */
    public static void setTimer(String time) {
        Platform.runLater(() -> {
            if(lobbyGuiView != null)
                lobbyGuiView.setTimer(time);
        });
    }

    /**
     * Removes the player.
     *
     * @param playerName the player name
     */
    public static void removePlayer(String playerName) {
        Platform.runLater(() -> {
            if(gameGuiAdapter != null)
                gameGuiAdapter.removePlayer(playerName);
            else if(lobbyGuiView != null)
                lobbyGuiView.removePlayer(playerName);
            playerList.remove(playerName);
        });
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void visit(Message message) {
        LOGGER.log(Level.INFO, message.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent)
     */
    @Override
    public void visit(AddPlayerEvent addPlayerEvent) {
        setPlayer(addPlayerEvent.getUsername(), addPlayerEvent.getPosition());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent)
     */
    @Override
    public void visit(BeginTurnEvent beginTurnEvent) {
        if (gameGuiAdapter == null) {
            if(lobbyGuiView != null)
                playerList = new ArrayList<>(lobbyGuiView.getPlayerShown());
            gameGuiAdapter = new GameGuiAdapter(GameView.getInstance(username,
                                                                    stage,
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            gameGuiAdapter.setToolCards(toolCardResponse.getIds(), client);
            gameGuiAdapter.setPublicObjectives(publicObjectiveResponse.getIdObjective());
            gameGuiAdapter.setPrivateObjective(privateObjectiveResponse.getIdObjective());
        }
        gameGuiAdapter.notifyTurn();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent)
     */
    @Override
    public void visit(MatchTimeEvent matchTimeEvent) {
        setTimer(matchTimeEvent.getTime());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent)
     */
    @Override
    public void visit(HeartbeatInitEvent heartbeatInitEvent) {
        try {
            client.startHeartbeat(heartbeatInitEvent.getHeartbeatPort());
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent)
     */
    @Override
    public void visit(RemovePlayerEvent removePlayerEvent) {
        removePlayer(removePlayerEvent.getUsername());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse)
     */
    @Override
    public void visit(WindowResponse windowResponse) {
        windowChoiceGuiController = new WindowChoiceGuiController(GUIManager.initWindowChoiceGuiView(windowResponse, lobbyGuiView.getStage()), client);
        stage = windowChoiceGuiController.getStage();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse)
     */
    @Override
    public void visit(OpponentWindowResponse opponentWindowResponse) {
        if(windowGameManager == null)
            windowGameManager = new WindowGameManager();
        windowGameManager.getWindows().clear();
        List<String> players = opponentWindowResponse.getPlayers();
        Map<String, Pair<Integer, WindowSide>> windows = new HashMap<>();
        for(String player : players) {
            windows.put(player, new Pair<>(opponentWindowResponse.getPlayerWindowId(player), opponentWindowResponse.getPlayerWindowSide(player)));
        }
        windowGameManager.setWindows(windows, players);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse)
     */
    @Override
    public void visit(DiceResponse diceResponse) {
        if (gameGuiAdapter == null) {
            if(lobbyGuiView != null)
                playerList = new ArrayList<>(lobbyGuiView.getPlayerShown());
            gameGuiAdapter = new GameGuiAdapter(GameView.getInstance(username,
                                                                    stage,
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            System.out.println("Token dati: " + windowGameManager.getToken());
            gameGuiAdapter.setToken(windowGameManager.getToken());
            gameGuiAdapter.setToolCards(toolCardResponse.getIds(), client);
            gameGuiAdapter.setPublicObjectives(publicObjectiveResponse.getIdObjective());
            gameGuiAdapter.setPrivateObjective(privateObjectiveResponse.getIdObjective());
            gameGuiAdapter.notifyEndTurn();
        }
        gameGuiAdapter.setDiceList(diceResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse)
     */
    @Override
    public void visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        gameGuiAdapter.setOpponentDiceResponse(opponentDiceMoveResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse)
     */
    @Override
    public void visit(RuleResponse ruleResponse) {
        gameGuiAdapter.notifyMoveResponse(ruleResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse)
     */
    @Override
    public void visit(NewTurnResponse newTurnResponse) {
        gameGuiAdapter.setRound(newTurnResponse.getRound());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse)
     */
    @Override
    public void visit(PrivateObjectiveResponse privateObjectiveResponse) {

        this.privateObjectiveResponse = privateObjectiveResponse;
        if (gameGuiAdapter != null)
            gameGuiAdapter.setPrivateObjective(privateObjectiveResponse.getIdObjective());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse)
     */
    @Override
    public void visit(PublicObjectiveResponse publicObjectiveResponse) {
        this.publicObjectiveResponse = publicObjectiveResponse;
        if (gameGuiAdapter != null)
            gameGuiAdapter.setPublicObjectives(publicObjectiveResponse.getIdObjective());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse)
     */
    @Override
    public void visit(ToolCardResponse toolCardResponse) {
        this.toolCardResponse = toolCardResponse;
        if (gameGuiAdapter != null)
            gameGuiAdapter.setToolCards(toolCardResponse.getIds(), client);
    }

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     */
    @Override
    public void visit(ScoreResponse scoreResponse) {
        try {
            client.sendRemoteMessage(new ByteStreamWindowEvent(username, gameGuiAdapter.getWindowAsByteArray()));
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
        Map<String, Integer> ranking = new HashMap<>();
        scoreResponse.getUsernames().forEach(username -> ranking.put(username, scoreResponse.getScore(username)));
        scoreLobbyView = ScoreLobbyView.getInstance(ranking, gameGuiAdapter.getStage());
    }

    @Override
    public void visit(ToolResponse toolResponse) {
        if(toolResponse.isCanBuy()) {
            gameGuiAdapter.setNotification("Tool comprato");
            gameGuiAdapter.removeToken(toolResponse.getTokenSpent());
        }
        else gameGuiAdapter.setNotification("Non hai abbastanza token!");
    }

    @Override
    public void visit(EndTurnResponse endTurnResponse) {
        gameGuiAdapter.notifyEndTurn();
    }

    @Override
    public void visit(TimeRemainingResponse timeRemainingResponse) {
        gameGuiAdapter.setTimeRemaining(timeRemainingResponse.getRemainingTime());
    }
}
