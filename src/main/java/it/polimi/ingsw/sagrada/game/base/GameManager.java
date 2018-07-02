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
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.MoveDiceWindowToolMessage;
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

import java.sql.Date;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Main game class, core class for the model of the main MVC design pattern, manage main and core functions
 */

public class GameManager implements Channel<Message, Message>, BaseGameMessageVisitor {

    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    private final long START_TIME = new java.util.Date().getTime();

    private List<Player> players;

    private List<String> playersId;

    private List<ObjectiveCard> privateObjective;

    private List<ToolCard> tools;

    private List<ObjectiveCard> publicObjective;

    private DiceManager diceManager;

    private ScoreTrack scoreTrack;

    private CardManager cardManager;

    private ToolManager toolManager;

    private RoundTrack roundTrack;

    private StateIterator stateIterator = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());

    private PlayerIterator playerIterator;

    private WindowManager windowManager;

    private RuleManager ruleManager;

    private DynamicRouter dynamicRouter;

    private List<Integer> scores = new ArrayList<>();

    private ScoreResponse finalGameResult = null;

    private int gameID;

    private Timer playTime;

    private BiConsumer<Message, String> fastRecoveryDispatch;

    /**
     * Instantiates a new game manager.
     *
     * @param players the players
     * @param dynamicRouter the dynamic router dispatching message
     */
    public GameManager(List<Player> players, DynamicRouter dynamicRouter, BiConsumer<Message, String> fastRecoveryDispatch) {
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
        dynamicRouter.subscribeChannel(EndTurnEvent.class, this);
        dynamicRouter.subscribeChannel(ByteStreamWindowEvent.class, this);
        dynamicRouter.subscribeChannel(MoveDiceWindowToolMessage.class, this);
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
     * Distribute private objectives
     */
    private void dealPrivateObjectiveState() {
        privateObjective = cardManager.dealPrivateObjective(players.size());
        IntStream.range(0, players.size()).forEach(i -> {
            players.get(i).setPrivateObjectiveCard(privateObjective.get(i));
            sendMessage(new PrivateObjectiveResponse(privateObjective.get(i).getId(), players.get(i).getId()));
        });
    }

    /**
     * Distribute tools
     */
    private void dealToolState() {
        tools = cardManager.dealTool();
        toolManager = new ToolManager(tools, listToMap(players), ruleManager.getIgnoreValueSet(), dynamicRouter);
        List<Integer> toolCardIds = new ArrayList<>();
        tools.forEach(toolCard -> toolCardIds.add(toolCard.getId()));
        sendMessage(new ToolCardResponse(toolCardIds));
    }

    /**
     * Distribute public objectives
     */
    private void dealPublicObjectiveState() {
        publicObjective = cardManager.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
        List<Integer> publicObjectiveIds = new ArrayList<>();
        publicObjective.forEach(objectiveCard -> publicObjectiveIds.add(objectiveCard.getId()));
        sendMessage(new PublicObjectiveResponse(publicObjectiveIds));
    }

    /**
     * Distribute windows
     */
    private void dealWindowState() {
        for (Player p : players) {
            windowManager.dealWindowId(p.getId());
        }
    }

    /**
     * Assign windows to player model
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
     * Start next round or if previous round was the last, init ending game procedures
     */
    private void startRound() {
        if(stateIterator.next() == StateGameEnum.TURN) {
            System.out.println(stateIterator.getRoundNumber() + " turn started");
            diceManager.bagToDraft();
            sendMessage(new NewTurnResponse(stateIterator.getRoundNumber()));
            notifyNextPlayer();
        }
        else scoreState();
    }

    /**
     * Notify next player about his current state in this turn
     */
    private void notifyNextPlayer() {
        if(playTime != null)
            playTime.cancel();
        if(playerIterator.hasNext()) {
            BeginTurnEvent beginTurnEvent = new BeginTurnEvent(playerIterator.next());
            sendMessage(beginTurnEvent);
            System.out.println("Begin turn sent to " + beginTurnEvent.getIdPlayer());
            playTime = new Timer();
            playTime.scheduleAtFixedRate(new GameTimer(beginTurnEvent.getIdPlayer()), 0, 1000L);
        }
        else {
            System.out.println("Ending round...");
            List<Dice> diceToRoundTrack = diceManager.putDiceRoundTrack();
            roundTrack.addDice(diceToRoundTrack, stateIterator.getRoundNumber());
            sendMessage(new DiceResponse(CommandKeyword.ROUND_TRACK, diceToRoundTrack));
            startRound();
        }
    }

    /**
     * Sets a dice in a player's window
     *
     * @param idPlayer the id player
     * @param dice the dice
     * @param position the position
     */
    private void setDiceInWindow(String idPlayer, Dice dice, Position position) {
        Player player = idToPlayer(idPlayer);
        if(player != null) {
            Window window = player.getWindow();
            window.setCell(dice, position.getRow(), position.getCol());
            ErrorType errorType = ruleManager.validateWindow(window.getCellMatrix());
            if(errorType == ErrorType.NO_ERROR) {
                sendMessage(new OpponentDiceMoveResponse(idPlayer, dice, position));
                sendMessage(new DiceResponse(CommandKeyword.DRAFT, diceManager.getDraft()));
            }
            else {
                //revert model to the previous move
                diceManager.revert();
                window.resetCell(position.getRow(), position.getCol());
            }
            sendMessage(new RuleResponse(idPlayer, errorType == ErrorType.NO_ERROR));
        }
    }

    /**
     *  Computer final scores and set them on the score board
     */
    private void scoreState() {
        players.forEach(player -> scores.add(scoreTrack.calculateScore(player)));  //look out for possible sorting issue
        List<String> usernames = new ArrayList<>();
        players.forEach(player -> usernames.add(player.getId()));
        finalGameResult = new ScoreResponse(usernames, scores);
        DataManager dataManager = DataManager.getDataManager();
        long currentTime = new java.util.Date().getTime();
        gameID = dataManager.saveGameRecord(new Date(START_TIME), (int) (currentTime - START_TIME), players.size());
        sendMessage(finalGameResult);
    }

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
     * Gets the scores
     *
     * @return the scores
     */
    public List<Integer> getScores() {
        return scores;
    }

    /**
     * Gets the current game state
     *
     * @return the current state
     */
    public StateGameEnum getCurrentState() {
        return stateIterator.getCurrentState();
    }

    /**
     * Gets the current players number
     *
     * @return the player number
     */
    public int getPlayerNumber() {
        return players.size();
    }

    /**
     * Get player by its username
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
     * Removes the player from the current game by its username
     * Warning : this is not a retroactive operation, canNOT be undone
     *
     * @param playerId username
     */
    public void removePlayer(String playerId) {
        synchronized (playerIterator) {
            playerIterator.removePlayer(playerId);
        }
    }

    private Map<String, Player> listToMap(List<Player> players) {
        Map<String, Player> map = new HashMap<>();
        for(Player player:players) {
            map.put(player.getId(), player);
        }
        return map;
    }

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
            opponentWindowResponse.getPlayers().forEach(player -> System.out.println(opponentWindowResponse.getPlayerWindowId(player)));
            fastRecoveryDispatch.accept(new OpponentWindowResponse(usernames, windowsId, sides), username);

            fastRecoveryDispatch.accept(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(diceManager.getDraft())), username);

            sendWindowsState(username);
        }
        else
            windowManager.dealWindowId(username);

        synchronized (playerIterator) {
            playerIterator.addPlayer(username);
        }
    }

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
        System.out.println("Received baseGameVisitor");
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
        notifyNextPlayer();
    }

    @Override
    public void visit(MoveDiceWindowToolMessage moveDiceWindowToolMessage) {
        System.out.println("---GameManager Tool---");
        DTO dto = new DTO();
        int id = moveDiceWindowToolMessage.getIdDice();
        Player player = idToPlayer(moveDiceWindowToolMessage.getIdPlayer());
        Window window = player.getWindow();
        dto.setCurrentPosition(window.getPositionFromId(id));
        dto.setNewPosition(moveDiceWindowToolMessage.getPosition());
        dto.setWindowMatrix(window.getCellMatrix());
        dto.setIgnoreValueSet(moveDiceWindowToolMessage.getIgnoredValue());

        ErrorType errorTypeRule = moveDiceWindowToolMessage.getToolCard().getRule().checkRule(dto);
        System.out.println("---"+errorTypeRule+"---");
        ErrorType errorType = ruleManager.validateWindow(window.getCellMatrix());
        System.out.println("---"+errorType+"---");
        if(errorType == ErrorType.NO_ERROR) {
            Position pos = dto.getNewPosition();
            Dice dice = window.getCellMatrix()[pos.getRow()][pos.getCol()].getCurrentDice();
            sendMessage(new OpponentDiceMoveResponse(
                    player.getId(), dice, dto.getNewPosition()));
            sendMessage(new OpponentDiceMoveResponse( //remove dice from window
                    player.getId(), new Dice(-1, Colors.RED), dto.getCurrentPosition()));
        }
        else {
            //revert model to the previous move
            Position prevPos = dto.getCurrentPosition();
            Position nextPos = dto.getNewPosition();
            Dice dice = window.getCellMatrix()[nextPos.getRow()][nextPos.getCol()].getCurrentDice();
            window.resetCell(nextPos.getRow(), nextPos.getCol());
            window.setCell(dice, prevPos.getRow(), prevPos.getCol());
            dto.getIgnoreValueSet().remove(dice.getId());
        }
        sendMessage(new RuleResponse(moveDiceWindowToolMessage.getIdPlayer(), errorType == ErrorType.NO_ERROR));
    }

    public class GameTimer extends TimerTask {

        private static final int MAX_WAITING_TIME = 40;

        private int elapsedTime = 0;
        private String username;

        GameTimer(String username) {
            this.username = username;
        }

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