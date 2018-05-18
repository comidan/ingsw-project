package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.base.state.*;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.playables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.sagrada.game.base.state.StateGameEnum.*;
import static it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum.*;

/**
 *
 */

public class GameManager implements Channel<Message, Message> {

    private List<Player> players;
    private DiceManager diceManager;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private CardManager cardManager;
    private ToolManager toolManager;
    private StateIterator stateIterator = StateIterator.getInstance();
    private RoundIterator roundIterator = RoundIterator.getRoundIterator();
    private PlayerIterator playerIterator;
    private WindowManager windowManager;
    private DynamicRouter dynamicRouter;

    private int numWindowDealt = 0;

    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    public GameManager(List<Player> players, DynamicRouter dynamicRouter) {
        Consumer<Message> function = this::dispatch;

        this.players = players;

        cardManager = new CardManager();
        diceManager = new DiceManager(players.size(), function, dynamicRouter);
        windowManager = new WindowManager(function, dynamicRouter);

        playerIterator = PlayerIterator.getPlayerIterator(players);

        this.dynamicRouter = dynamicRouter;
    }

    public Consumer<Message> getDispatchReference() {
        return this::dispatch;
    }

    public void startGame() {
        while (stateIterator.hasNext() && stateIterator.getCurrentState() != DEAL_WINDOWS) {
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
        }
    }

    private void dealToolState() {
        List<ToolCard> tools = cardManager.dealTool();
        toolManager = ToolManager.getInstance(tools);
    }

    private void dealPublicObjectiveState() {
        List<ObjectiveCard> publicObjective;
        publicObjective = cardManager.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
    }

    private void dealWindowState() {
        for (Player p : players) {
            windowManager.dealWindowId(p.getId());
        }
    }

    private void dealWindowsToPlayer(Player player, Window window) {
        player.setWindow(window);
        numWindowDealt++;
        if (numWindowDealt == players.size() && stateIterator.hasNext()) {
            stateIterator.next();
        }
    }

    public void playRound() {

        while (roundIterator.hasNext()) {
            switch (roundIterator.next()) {
                case SETUP_ROUND:
                    diceManager.getDice(RoundStateEnum.SETUP_ROUND);
                    break;
                case IN_GAME:
                    while (playerIterator.hasNext()) {
                        playerIterator.next();
                        diceManager.getDice(RoundStateEnum.IN_GAME);
                    }
                    break;
                case END_ROUND:
                    diceManager.getDice(RoundStateEnum.END_ROUND);
                    break;
            }
        }
    }

    private void setDiceInWindow(int idPlayer, Dice dice, Position position) {
        players.get(idPlayer).getWindow().setCell(dice, position.getCol(), position.getRow());
    }


    public void scoreState() {
        List<Integer> scoreList = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            scoreList.add(scoreTrack.calculateScore(players.get(i)));
        }
    }

    public StateGameEnum getCurrentState() {
        return stateIterator.getCurrentState();
    }

    public RoundStateEnum getCurrentRoundState() {
        return roundIterator.getCurrentState();
    }

    public int getPlayerNumber() {
        return players.size();
    }


    @Override
    public void dispatch(Message message) {
        String eventType = message.getType().getName();
        if(eventType.equals(EventTypeEnum.toString(WINDOW_GAME_MANAGER_EVENT))) {
            WindowGameManagerEvent msgW = (WindowGameManagerEvent) message;
            dealWindowsToPlayer(players.get(msgW.getIdPlayer()), msgW.getWindow());
        } else if(eventType.equals(EventTypeEnum.toString(DICE_GAME_MANAGER_EVENT))) {
            DiceGameManagerEvent msgD = (DiceGameManagerEvent) message;
            setDiceInWindow(msgD.getIdPlayer(), msgD.getDice(), msgD.getPosition());
        } else {
            LOGGER.log(Level.SEVERE, "GameManager has received a wrong event");
        }
    }

    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }
}