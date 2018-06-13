package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.base.state.PlayerIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.playables.*;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum.*;

/**
 *
 */

public class GameManager implements Channel<Message, Message> {

    private List<Player> players;
    private DiceManager diceManager;
    private ScoreTrack scoreTrack;
    private CardManager cardManager;
    private ToolManager toolManager;
    private RoundTrack roundTrack;
    private StateIterator stateIterator = StateIterator.getInstance();
    private PlayerIterator playerIterator;
    private WindowManager windowManager;
    private RuleManager ruleManager;
    private DynamicRouter dynamicRouter;

    private List<Integer> scores = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    public GameManager(List<Player> players, DynamicRouter dynamicRouter) {
        Consumer<Message> function = this::dispatch;

        this.players = players;

        cardManager = new CardManager();
        diceManager = new DiceManager(players.size(), function, dynamicRouter);
        windowManager = new WindowManager(function, dynamicRouter);
        ruleManager = new RuleManager();
        roundTrack = new RoundTrack();

        List<String> playersId = new ArrayList<>();
        for (Player p:players) {
            playersId.add(p.getId());
        }
        playerIterator = new PlayerIterator(playersId);

        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(EndTurnEvent.class, this);
    }

    public Consumer<Message> getDispatchReference() {
        return this::dispatch;
    }

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

    private void dealPrivateObjectiveState() {
        List<ObjectiveCard> privateObjective;
        privateObjective = cardManager.dealPrivateObjective(players.size());
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPrivateObjectiveCard(privateObjective.get(i));
            sendMessage(new PrivateObjectiveResponse(privateObjective.get(i).getId(), players.get(i).getId()));
        }
    }

    private void dealToolState() {
        List<ToolCard> tools = cardManager.dealTool();
        toolManager = ToolManager.getInstance(tools);
        List<Integer> toolCardIds = new ArrayList<>();
        tools.forEach(toolCard -> toolCardIds.add(toolCard.getId()));
        sendMessage(new ToolCardResponse(toolCardIds));
    }

    private void dealPublicObjectiveState() {
        List<ObjectiveCard> publicObjective;
        publicObjective = cardManager.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
        List<Integer> publicObjectiveIds = new ArrayList<>();
        publicObjective.forEach(objectiveCard -> publicObjectiveIds.add(objectiveCard.getId()));
        sendMessage(new PublicObjectiveResponse(publicObjectiveIds));
    }

    private void dealWindowState() {
        for (Player p : players) {
            windowManager.dealWindowId(p.getId());
        }
    }

    private void dealWindowsToPlayer(Player player, Window window) {
        boolean dealt = true;

        player.setWindow(window);
        System.out.println("Window player " + player.getId() + " : " + window.getName() + " " + window.getId());
        for(Player p:players) {
            if(p.isConnected() && p.getWindow()==null) dealt = false;
        }

        if(dealt) {
            System.out.println("Initializing opponents windows message");
            List<Integer> windowsId = new ArrayList<>();
            List<WindowSide> sides = new ArrayList<>();
            List<String> usernames = new ArrayList<>();
            players.forEach(p -> windowsId.add(p.getWindow().getId()));
            players.forEach(p -> sides.add(player.getWindow().getSide()));
            players.forEach(p -> usernames.add(p.getId()));
            sendMessage(new OpponentWindowResponse(usernames, windowsId, sides));
            startRound();
        }
    }

    private void startRound() {
        if(stateIterator.next()==StateGameEnum.TURN) {
            System.out.println(stateIterator.getRoundNumber() + " turn started");
            diceManager.bagToDraft();
            sendMessage(new NewTurnResponse(stateIterator.getRoundNumber()));
            notifyNextPlayer();
        }
        else scoreState();
    }

    private void notifyNextPlayer() {
        if(playerIterator.hasNext()) {
            BeginTurnEvent beginTurnEvent = new BeginTurnEvent(playerIterator.next());
            sendMessage(beginTurnEvent);
            System.out.println("Begin turn sent to " + beginTurnEvent.getIdPlayer());
        }
        else {
            System.out.println("Ending round...");
            List<Dice> diceToRoundTrack = diceManager.putDiceRoundTrack();
            roundTrack.addDice(diceToRoundTrack, stateIterator.getRoundNumber());
            sendMessage(new DiceResponse(CommandKeyword.ROUND_TRACK, diceToRoundTrack));
            startRound();
        }
    }

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

    private void scoreState() {
        for (Player p:players) {
            scores.add(scoreTrack.calculateScore(p));
        }
    }

    public List<Integer> getScores() {
        return scores;
    }

    public StateGameEnum getCurrentState() {
        return stateIterator.getCurrentState();
    }

    public int getPlayerNumber() {
        return players.size();
    }

    private Player idToPlayer(String id) {
        for(Player p:players) {
            if(p.getId().equals(id)) return p;
        }
        return null;
    }

    public void removePlayer(String playerId) {
        synchronized (players) {
            players.removeIf(player -> player.getId().equals(playerId));
            playerIterator.removePlayer(playerId);
            diceManager.setNumberOfPlayers(players.size());
        }
    }

    @Override
    public void dispatch(Message message) {
        String eventType = message.getType().getName();
        System.out.println(eventType);
        if(eventType.equals(EventTypeEnum.toString(WINDOW_GAME_MANAGER_EVENT))) {
            WindowGameManagerEvent msgW = (WindowGameManagerEvent) message;
            dealWindowsToPlayer(idToPlayer(msgW.getIdPlayer()), msgW.getWindow());
        } else if(eventType.equals(EventTypeEnum.toString(DICE_GAME_MANAGER_EVENT))) {
            DiceGameManagerEvent msgD = (DiceGameManagerEvent) message;
            setDiceInWindow(msgD.getIdPlayer(), msgD.getDice(), msgD.getPosition());
        } else if(eventType.equals(EventTypeEnum.toString(END_TURN_EVENT))) {
            notifyNextPlayer();
        }
        else {
            LOGGER.log(Level.SEVERE, "GameManager has received a wrong event");
        }
    }

    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }
}