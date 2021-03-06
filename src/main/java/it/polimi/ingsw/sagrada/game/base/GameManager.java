package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.base.state.PlayerIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateIteratorSingletonPool;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.playables.*;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.server.tools.DataManager;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobbyPool;

import java.sql.Date;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


/**
 * Main game class, core class for the model of the main MVC design pattern, manage main and core functions.
 */

public class GameManager implements Channel<Message, Message>, BaseGameMessageVisitor {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    /** The start time. */
    private final long START_TIME = new java.util.Date().getTime();

    /** The players. */
    private List<Player> players;

    /** The players id. */
    private List<String> playersId;

    /** The private objective. */
    private List<ObjectiveCard> privateObjective;

    /** The tools. */
    private List<ToolCard> tools;

    /** The public objective. */
    private List<ObjectiveCard> publicObjective;

    /** The dice manager. */
    private DiceManager diceManager;

    /** The score track. */
    private ScoreTrack scoreTrack;

    /** The card manager. */
    private CardManager cardManager;

    /** The tool manager. */
    private ToolManager toolManager;

    /** The round track. */
    private RoundTrack roundTrack;

    /** The state iterator. */
    private StateIterator stateIterator = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());

    /** The player iterator. */
    private PlayerIterator playerIterator;

    /** The window manager. */
    private WindowManager windowManager;

    /** The rule manager. */
    private RuleManager ruleManager;

    /** The dynamic router. */
    private DynamicRouter dynamicRouter;

    /** The scores. */
    private List<Integer> scores = new ArrayList<>();

    /** The final game result. */
    private ScoreResponse finalGameResult = null;

    /** The game ID. */
    private int gameID;

    /** The play time. */
    private Timer playTime;

    /** The fast recovery dispatch. */
    private BiConsumer<Message, String> fastRecoveryDispatch;

    /** The tool double turn. */
    private boolean toolDoubleTurn;

    /** The tool move alone. */
    private boolean toolMoveAlone;

    /** The dice placed. */
    private int dicePlaced;

    /** The lobby id. */
    private String lobbyId;

    /**
     * Instantiates a new game manager.
     *
     * @param players the players
     * @param dynamicRouter the dynamic router dispatching message
     * @param fastRecoveryDispatch the fast recovery dispatch
     * @param lobbyId the lobby id
     */
    public GameManager(List<Player> players, DynamicRouter dynamicRouter, BiConsumer<Message, String> fastRecoveryDispatch, String lobbyId) {
        Consumer<Message> function = this::dispatch;
        this.players = players;
        this.fastRecoveryDispatch = fastRecoveryDispatch;
        cardManager = new CardManager();
        diceManager = new DiceManager(players.size(), function, dynamicRouter);
        windowManager = new WindowManager(function, dynamicRouter);
        ruleManager = new RuleManager();
        roundTrack = new RoundTrack(dynamicRouter);
        playersId = new ArrayList<>();
        players.forEach(p -> playersId.add(p.getId()));
        playerIterator = new PlayerIterator(playersId);
        toolDoubleTurn = false;
        toolMoveAlone = false;
        this.lobbyId = lobbyId;
        dicePlaced = 0;
        dynamicRouter.subscribeChannel(EndTurnEvent.class, this);
        dynamicRouter.subscribeChannel(ByteStreamWindowEvent.class, this);
        dynamicRouter.subscribeChannel(MoveDiceWindowToolMessage.class, this);
        dynamicRouter.subscribeChannel(MoveDiceToolMessage.class, this);
        dynamicRouter.subscribeChannel(EnableDoubleTurnToolMessage.class, this);
        dynamicRouter.subscribeChannel(MoveAloneDiceToolMessage.class, this);
        dynamicRouter.subscribeChannel(ColorConstraintToolMessage.class, this);
        dynamicRouter.subscribeChannel(DraftToBagToolMessage.class, this);
        this.dynamicRouter = dynamicRouter;
    }

    /**
     * Gets the dispatch method reference.
     *
     * @return the dispatch method reference as a Consumer of Message
     */
    public Consumer<Message> getDispatchReference() {
        return this::dispatch;
    }

    /**
     * Engine start
     * Burn baby burn (Apollo 11 LEM Assembly cit.)
     */
    public void startGame() {
        while (stateIterator.hasNext() && stateIterator.getCurrentState() != StateGameEnum.DEAL_WINDOWS) {
            stateIterator.next();

            switch (stateIterator.getCurrentState()) {
                case DEAL_PRIVATE_OBJECTIVE:
                    dealPrivateObjectiveState();
                    break;
                case DEAL_TOOL:
                    dealToolState();
                    break;
                case DEAL_PUBLIC_OBJECTIVE:
                    dealPublicObjectiveState();
                    break;
                case DEAL_WINDOWS:
                    dealWindowState();
                    break;
                default:
                    throw new NoSuchElementException();
            }
        }
    }

    /**
     * Distribute private objectives.
     */
    private void dealPrivateObjectiveState() {
        privateObjective = cardManager.dealPrivateObjective(players.size());
        IntStream.range(0, players.size()).forEach(i -> {
            players.get(i).setPrivateObjectiveCard(privateObjective.get(i));
            sendMessage(new PrivateObjectiveResponse(privateObjective.get(i).getId(), players.get(i).getId()));
        });
    }

    /**
     * Distribute tools.
     */
    private void dealToolState() {
        tools = cardManager.dealTool();
        toolManager = new ToolManager(tools, listToMap(players), ruleManager::addIgnoreValue, ruleManager::addIgnoreColor, dynamicRouter);
        List<Integer> toolCardIds = new ArrayList<>();
        tools.forEach(toolCard -> toolCardIds.add(toolCard.getId()));
        sendMessage(new ToolCardResponse(toolCardIds));
    }

    /**
     * Distribute public objectives.
     */
    private void dealPublicObjectiveState() {
        publicObjective = cardManager.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
        List<Integer> publicObjectiveIds = new ArrayList<>();
        publicObjective.forEach(objectiveCard -> publicObjectiveIds.add(objectiveCard.getId()));
        sendMessage(new PublicObjectiveResponse(publicObjectiveIds));
    }

    /**
     * Distribute windows.
     */
    private void dealWindowState() {
        for (Player p : players) {
            windowManager.dealWindowId(p.getId());
        }
    }

    /**
     * Assign windows to player model.
     *
     * @param player the player
     * @param window the window
     */
    private void dealWindowsToPlayer(Player player, Window window) {
        boolean dealt = true;

        player.setWindow(window);
        for(Player p:players)
            if(p.isConnected() && p.getWindow()==null)
                dealt = false;

        if(dealt) {
            List<Integer> windowsId = new ArrayList<>();
            List<WindowSide> sides = new ArrayList<>();
            List<String> usernames = new ArrayList<>();
            players.forEach(p -> windowsId.add(p.getWindow().getId()));
            players.forEach(p -> sides.add(p.getWindow().getSide()));
            players.forEach(p -> usernames.add(p.getId()));
            sendMessage(new OpponentWindowResponse(usernames, windowsId, sides));
            startRound();
        }
    }

    /**
     * Start next round or if previous round was the last, init ending game procedures.
     */
    private void startRound() {
        if(stateIterator.next() == StateGameEnum.TURN) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->stateIterator.getRoundNumber() + " turn started");
            diceManager.bagToDraft();
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Sending round number : " + stateIterator.getRoundNumber());
            sendMessage(new NewTurnResponse(stateIterator.getRoundNumber()));
            notifyNextPlayer();
        }
        else {
            scoreState();
            new MatchLobbyPool().releaseLobby(lobbyId);
        }
    }

    /**
     * Notify next player about his current state in this turn.
     */
    private void notifyNextPlayer() {
        if(playTime != null)
            playTime.cancel();
        if(playerIterator.hasNext()) {
            BeginTurnEvent beginTurnEvent = new BeginTurnEvent(playerIterator.next());
            sendMessage(beginTurnEvent);
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Begin turn sent to " + beginTurnEvent.getIdPlayer());
            playTime = new Timer();
            playTime.scheduleAtFixedRate(new GameTimer(beginTurnEvent.getIdPlayer()), 0, 1000L);
        }
        else {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Ending round...");
            List<Dice> diceToRoundTrack = diceManager.putDiceRoundTrack();
            roundTrack.addDice(diceToRoundTrack, stateIterator.getRoundNumber());
            sendMessage(new DiceResponse(CommandKeyword.ROUND_TRACK, diceToRoundTrack));
            startRound();
        }
    }

    /**
     * Sets a dice in a player's window.
     *
     * @param idPlayer the id player
     * @param dice the dice
     * @param position the position
     */
    private void setDiceInWindow(String idPlayer, Dice dice, Position position) {
        Player player = idToPlayer(idPlayer);
        if(player != null) {
            ruleManager.removeDiceFromSet(dice.getId());
            Window window = player.getWindow();
            if(toolMoveAlone) {
                DTO dto = new DTO();
                dto.setNewPosition(position);
                dto.setDice(dice);
                dto.setWindowMatrix(window.getCellMatrix());
                dto.setIgnoreSequenceDice(ruleManager::addIgnoreSequenceDice);
                toolManager.getCurrentSelectedTool().getRule().checkRule(dto);
                toolMoveAlone=false;
            }
            else window.setCell(dice, position.getRow(), position.getCol());

            ErrorType turnErrorType = ErrorType.NO_ERROR;
            if(player.isTurnPlayed()) {
                turnErrorType = ErrorType.ALREADY_PLAYED;
                if(toolDoubleTurn) {
                    turnErrorType = ErrorType.NO_ERROR;
                    toolDoubleTurn = false;
                }
            }

            ErrorType errorType = ruleManager.validateWindow(window.getCellMatrix());
            if(errorType == ErrorType.NO_ERROR && turnErrorType == ErrorType.NO_ERROR) {
                player.setIsTurnPlayed(true);
                sendMessage(new OpponentDiceMoveResponse(idPlayer, dice, position));
                sendMessage(new DiceResponse(CommandKeyword.DRAFT, diceManager.getDraft()));
            }
            else {
                //revert model to the previous move
                ruleManager.revert(dice.getId());
                diceManager.revert();
                window.resetCell(position.getRow(), position.getCol());
            }
            sendMessage(new RuleResponse(idPlayer, errorType == ErrorType.NO_ERROR && turnErrorType == ErrorType.NO_ERROR));
        }
    }

    /**
     *  Computer final scores and set them on the score board.
     */
    private void scoreState() {
        playTime.cancel();
        players.forEach(player -> scores.add(scoreTrack.calculateScore(player)));  //look out for possible sorting issue
        List<String> usernames = new ArrayList<>();
        players.forEach(player -> usernames.add(player.getId()));
        finalGameResult = new ScoreResponse(usernames, scores);
        DataManager dataManager = DataManager.getDataManager();
        long currentTime = new java.util.Date().getTime();
        gameID = dataManager.saveGameRecord(new Date(START_TIME), (int) (currentTime - START_TIME), players.size());
        sendMessage(finalGameResult);
    }

    /**
     * Save stats.
     *
     * @param byteStreamWindowEvent the byte stream window event
     */
    private void saveStats(ByteStreamWindowEvent byteStreamWindowEvent) {
        DataManager dataManager = DataManager.getDataManager();
        Optional<Player> optionalPlayer = players.stream().filter(user -> user.getId().equals(byteStreamWindowEvent.getUsername())).findFirst();
        if(optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            dataManager.savePlayerRecord(player.getId(), gameID, finalGameResult.getScore(player.getId()), new Date(START_TIME), player.getWindow().getId(), byteStreamWindowEvent.getImage());
            dataManager.saveAssignedObjectCards(player.getId(), gameID, player.getPrivateObjectiveCard().getId(), finalGameResult.getScore(player.getId()));
        }
    }

    /**
     * Gets the scores.
     *
     * @return the scores
     */
    public List<Integer> getScores() {
        return scores;
    }

    /**
     * Gets the current game state.
     *
     * @return the current state
     */
    public StateGameEnum getCurrentState() {
        return stateIterator.getCurrentState();
    }

    /**
     * Gets the current players number.
     *
     * @return the player number
     */
    public int getPlayerNumber() {
        return players.size();
    }

    /**
     * Get player by its username.
     *
     * @param id username
     * @return the player
     */
    private Player idToPlayer(String id) {
        for(Player p:players) {
            if(p.getId().equals(id)) return p;
        }
        return null;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public String getCurrentPlayer() {
        return playerIterator.getCurrentPlayer();
    }

    /**
     * Removes the player from the current game by its username
     * Warning : this is not a retroactive operation, canNOT be undone.
     *
     * @param playerId username
     */
    public void removePlayer(String playerId) {
        synchronized (playerIterator) {
            while(playerIterator.getCurrentPlayer().equals(playerId))
                notifyNextPlayer();
            playerIterator.removePlayer(playerId);
            if(playerIterator.getCurrentPlayerNumber() <= 1) {
                scoreState();
                new MatchLobbyPool().releaseLobby(lobbyId);
            }
        }
    }

    /**
     * List to map.
     *
     * @param players the players
     * @return the map
     */
    private Map<String, Player> listToMap(List<Player> players) {
        Map<String, Player> map = new HashMap<>();
        for(Player player:players) {
            map.put(player.getId(), player);
        }
        return map;
    }

    /**
     * Update client state.
     *
     * @param username the username
     */
    public void updateClientState(String username) {
        int index = playersId.indexOf(username);
        sendMessage(new PrivateObjectiveResponse(privateObjective.get(index).getId(), username));

        List<Integer> toolCardIds = new ArrayList<>();
        tools.forEach(toolCard -> toolCardIds.add(toolCard.getId()));
        fastRecoveryDispatch.accept(new ToolCardResponse(toolCardIds), username);

        List<Integer> publicObjectiveIds = new ArrayList<>();
        publicObjective.forEach(objectiveCard -> publicObjectiveIds.add(objectiveCard.getId()));
        fastRecoveryDispatch.accept(new PublicObjectiveResponse(publicObjectiveIds), username);
        if(stateIterator.getCurrentState() == StateGameEnum.TURN) {
            List<Integer> windowsId = new ArrayList<>();
            List<WindowSide> sides = new ArrayList<>();
            List<String> usernames = new ArrayList<>();
            players.forEach(p -> windowsId.add(p.getWindow().getId()));
            players.forEach(p -> sides.add(p.getWindow().getSide()));
            players.forEach(p -> usernames.add(p.getId()));
            OpponentWindowResponse opponentWindowResponse = new OpponentWindowResponse(usernames, windowsId, sides);
            opponentWindowResponse.getPlayers().forEach(player -> Logger.getLogger(getClass().getName()).log(Level.INFO, () ->opponentWindowResponse.getPlayerWindowId(player) + ""));
            fastRecoveryDispatch.accept(new OpponentWindowResponse(usernames, windowsId, sides), username);

            fastRecoveryDispatch.accept(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(diceManager.getDraft())), username);

            fastRecoveryDispatch.accept(new DiceRoundTrackReconnectionEvent(roundTrack.getRoundDice(), username), username);

            sendWindowsState(username);
        }
        else
            windowManager.dealWindowId(username);

        synchronized (playerIterator) {
            playerIterator.addPlayer(username);
        }
    }

    /**
     * Send windows state.
     *
     * @param username the username
     */
    private void sendWindowsState(String username) {
        players.forEach(player -> {
            Cell[][] matrix = player.getWindow().getCellMatrix();
            for(int i = 0; i < matrix.length; i++)
                for(int j = 0; j < matrix[i].length; j++)
                    if(matrix[i][j].isOccupied())
                        fastRecoveryDispatch.accept(new OpponentDiceMoveResponse(player.getId(), matrix[i][j].getCurrentDice(), new Position(i, j)), username);
        });
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(Message message) {
        BaseGameVisitor baseGameVisitor = (BaseGameVisitor) message;
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Received baseGameVisitor");
        baseGameVisitor.accept(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }

    /**
     * Visit.
     *
     * @param message the message
     */
    @Override
    public void visit(Message message) {
        LOGGER.log(Level.INFO, message::toString);
    }

    /**
     * Visit.
     *
     * @param message the message
     */
    @Override
    public void visit(ByteStreamWindowEvent message) {
        saveStats(message);
    }

    /**
     * Visit.
     *
     * @param message the message
     */
    @Override
    public void visit(WindowGameManagerEvent message) {
        dealWindowsToPlayer(idToPlayer(message.getIdPlayer()), message.getWindow());
    }

    /**
     * Visit.
     *
     * @param message the message
     */
    @Override
    public void visit(DiceGameManagerEvent message) {
        setDiceInWindow(message.getIdPlayer(), message.getDice(), message.getPosition());
    }

    /**
     * Visit.
     *
     * @param message the message
     */
    @Override
    public void visit(EndTurnEvent message) {
        idToPlayer(playerIterator.getCurrentPlayer()).setIsTurnPlayed(false);
        toolDoubleTurn = false;
        toolMoveAlone = false;
        notifyNextPlayer();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.MoveDiceWindowToolMessage)
     */
    @Override
    public void visit(MoveDiceWindowToolMessage moveDiceWindowToolMessage) {
        int id = moveDiceWindowToolMessage.getIdDice();
        ruleManager.removeDiceFromSet(id);
        DTO dto = new DTO();
        Player player = idToPlayer(moveDiceWindowToolMessage.getIdPlayer());
        Window window = player.getWindow();
        dto.setCurrentPosition(window.getPositionFromId(id));
        dto.setNewPosition(moveDiceWindowToolMessage.getPosition());
        dto.setWindowMatrix(window.getCellMatrix());
        if(moveDiceWindowToolMessage.getToolCard().getId()==1)
            dto.setIgnoreColorSet(ruleManager::addIgnoreColor);
        else
            dto.setIgnoreValueSet(ruleManager::addIgnoreValue);
        moveDiceWindowToolMessage.getToolCard().getRule().checkRule(dto);
        ErrorType errorType = ruleManager.validateWindow(window.getCellMatrix());
        if(errorType == ErrorType.NO_ERROR) {
            Position pos = dto.getNewPosition();
            Dice dice = window.getCellMatrix()[pos.getRow()][pos.getCol()].getCurrentDice();
            sendMessage(new OpponentDiceMoveResponse(
                    player.getId(), dice, dto.getNewPosition()));
            sendMessage(new OpponentDiceMoveResponse( //remove dice from window
                    player.getId(), new Dice(-1, Colors.RED), dto.getCurrentPosition())); //id -1 means that is a dice to remove
        }
        else {
            //revert model to the previous move
            ruleManager.revert(id);
            Position prevPos = dto.getCurrentPosition();
            Position nextPos = dto.getNewPosition();
            Dice dice = window.getCellMatrix()[nextPos.getRow()][nextPos.getCol()].getCurrentDice();
            window.resetCell(nextPos.getRow(), nextPos.getCol());
            window.setCell(dice, prevPos.getRow(), prevPos.getCol());
            ruleManager.removeIgnoreValue(dice.getId());
        }
        sendMessage(new RuleResponse(moveDiceWindowToolMessage.getIdPlayer(), errorType == ErrorType.NO_ERROR));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.MoveDiceToolMessage)
     */
    @Override
    public void visit(MoveDiceToolMessage moveDiceToolMessage) {
        Player player = idToPlayer(moveDiceToolMessage.getPlayerId());
        if(player != null) {
            ruleManager.removeDiceFromSet(moveDiceToolMessage.getDiceId());
            Window window = player.getWindow();
            Dice dice = window.getDicefromId(moveDiceToolMessage.getDiceId());
            Position prevPos = window.getPositionFromId(moveDiceToolMessage.getDiceId());
            Position nextPos = moveDiceToolMessage.getPosition();
            window.setCell(dice, nextPos.getRow(), nextPos.getCol());
            window.resetCell(prevPos.getRow(), prevPos.getCol());
            ErrorType errorType = ruleManager.validateWindow(window.getCellMatrix());
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->errorType + "");
            if(errorType == ErrorType.NO_ERROR) {
                sendMessage(new OpponentDiceMoveResponse(player.getId(), dice, nextPos));
                sendMessage(new OpponentDiceMoveResponse( //remove dice from window
                        player.getId(), new Dice(-1, Colors.RED), prevPos)); //id -1 means that is a dice to remove
            }
            else {
                //revert model to the previous move
                ruleManager.revert(moveDiceToolMessage.getDiceId());
                window.resetCell(nextPos.getRow(), nextPos.getCol());
                window.setCell(dice, prevPos.getRow(), prevPos.getCol());
            }
            sendMessage(new RuleResponse(moveDiceToolMessage.getPlayerId(), errorType == ErrorType.NO_ERROR));
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableDoubleTurnToolMessage)
     */
    @Override
    public void visit(EnableDoubleTurnToolMessage enableDoubleTurnToolMessage) {
        toolDoubleTurn = playerIterator.canApplyToolChange(enableDoubleTurnToolMessage.getPlayerId());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.MoveAloneDiceToolMessage)
     */
    @Override
    public void visit(MoveAloneDiceToolMessage moveAloneDiceToolMessage) {
        toolMoveAlone = true;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.ColorConstraintToolMessage)
     */
    @Override
    public void visit(ColorConstraintToolMessage colorConstraintToolMessage) {
        DTO dto = new DTO();
        DiceEvent diceEvent = colorConstraintToolMessage.getDiceEvent();
        Player player = idToPlayer(diceEvent.getIdPlayer());
        if(player!=null) {
            ruleManager.removeDiceFromSet(diceEvent.getIdDice());
            Window window = player.getWindow();
            dto.setDice(window.getDicefromId(diceEvent.getIdDice()));
            dto.setWindowMatrix(window.getCellMatrix());
            dto.setCurrentPosition(window.getPositionFromId(diceEvent.getIdDice()));
            dto.setNewPosition(diceEvent.getPosition());
            dto.setImposedColor(colorConstraintToolMessage.getConstraint());
            ErrorType errorTool = colorConstraintToolMessage.getToolCard().getRule().checkRule(dto);
            ErrorType errorWindow = ruleManager.validateWindow(window.getCellMatrix());

            if(errorTool==ErrorType.NO_ERROR && errorWindow==ErrorType.NO_ERROR) {
                sendMessage(new OpponentDiceMoveResponse(player.getId(), dto.getDice(), dto.getNewPosition()));
                sendMessage(new OpponentDiceMoveResponse(player.getId(), new Dice(-1, Colors.RED), dto.getCurrentPosition()));
            }
            else {
                ruleManager.revert(diceEvent.getIdDice());
                window.resetCell(dto.getNewPosition().getRow(), dto.getNewPosition().getCol());
                window.setCell(dto.getDice(), dto.getCurrentPosition().getRow(), dto.getCurrentPosition().getCol());
            }

            sendMessage(new RuleResponse(player.getId(), errorTool==ErrorType.NO_ERROR && errorWindow==ErrorType.NO_ERROR));
        }

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.DraftToBagToolMessage)
     */
    @Override
    public void visit(DraftToBagToolMessage draftToBagToolMessage) {
        DTO dto = new DTO();
        dto.setPlayerId(draftToBagToolMessage.getPlayerId());
        dto.setDiceId(draftToBagToolMessage.getDiceId());
        dto.setMoveDiceFromDraftToBag(diceManager::moveDiceFromDraftToBag);
        draftToBagToolMessage.getToolCard().getRule().checkRule(dto);
    }

    /**
     * The Class GameTimer.
     */
    public class GameTimer extends TimerTask {

        /** The Constant MAX_WAITING_TIME. */
        private static final int MAX_WAITING_TIME = 40;

        /** The elapsed time. */
        private int elapsedTime = 0;
        
        /** The username. */
        private String username;

        /**
         * Instantiates a new game timer.
         *
         * @param username the username
         */
        GameTimer(String username) {
            this.username = username;
        }

        /* (non-Javadoc)
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            if(elapsedTime > MAX_WAITING_TIME) {
                sendMessage(new EndTurnResponse(username));
                notifyNextPlayer();
                cancel();
            }
            sendMessage(new TimeRemainingResponse(username, MAX_WAITING_TIME - elapsedTime));
            elapsedTime++;
        }
    }
}