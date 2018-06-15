package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.gui.game.GameGuiAdapter;
import it.polimi.ingsw.sagrada.gui.game.GameView;
import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.gui.windows.WindowGameManager;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
    
    /** The player list. */
    private static List<String> playerList = new ArrayList<>();
    
    /** The player lobby list backup. */
    private static List<String> playerLobbyListBackup = new ArrayList<>();
    
    /** The username. */
    private static String username;
    
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
        setPlayer(addPlayerEvent.getUsername());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent)
     */
    @Override
    public void visit(BeginTurnEvent beginTurnEvent) {
        if (gameGuiAdapter == null) {
            windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
            gameGuiAdapter = new GameGuiAdapter(GameView.getInstance(username,
                                                                    windowChoiceGuiController.getStage(),
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            gameGuiAdapter.setToolCards(toolCardResponse.getIds());
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
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse)
     */
    @Override
    public void visit(OpponentWindowResponse opponentWindowResponse) {
        if(windowGameManager == null)
            windowGameManager = new WindowGameManager();
        List<String> players = opponentWindowResponse.getPlayers();
        for(String player : players)
            windowGameManager.addWindow(opponentWindowResponse.getPlayerWindowId(player), opponentWindowResponse.getPlayerWindowSide(player));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse)
     */
    @Override
    public void visit(DiceResponse diceResponse) {
        if (gameGuiAdapter == null) {
            windowGameManager.addWindow(windowChoiceGuiController.getWindowId(), windowChoiceGuiController.getWindowSide());
            gameGuiAdapter = new GameGuiAdapter(GameView.getInstance(username,
                                                                    windowChoiceGuiController.getStage(),
                                                                    playerList,
                                                                    windowGameManager.getWindows()), client);
            gameGuiAdapter.setToolCards(toolCardResponse.getIds());
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
            gameGuiAdapter.setToolCards(toolCardResponse.getIds());
    }
}
