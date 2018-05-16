package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.playables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;

/**
 *
 */

public class GameManager implements Channel<Message> {

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
    private static GameManager gameManager;

    private int numWindowDealed = 0;

    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    private GameManager(List<Player> players) {
        this.players = players;
        cardManager = new CardManager();
        diceManager = DiceManager.getDiceManager(players.size());
        playerIterator = PlayerIterator.getPlayerIterator(players);
        windowManager = new WindowManager();
    }

    public static GameManager getGameController(List<Player> players) {
        if (gameManager == null) {
            gameManager = new GameManager(players);
        }
        return gameManager;
    }

    public static GameManager getGameManager() {
        return gameManager;
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
            List<Integer> windowsId = windowManager.dealWindowId();
            //this id will be passed to Player in the view
        }
    }

    private void dealWindowsToPlayer(Player player, Window window) {
        player.setWindow(window);
        numWindowDealed++;
        if (numWindowDealed == players.size() && stateIterator.hasNext()) {
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
        switch (eventType) {
            case "WindowGameManagerEvent":
                WindowGameManagerEvent msgW = (WindowGameManagerEvent) message;
                dealWindowsToPlayer(players.get(msgW.getIdPlayer()), msgW.getWindow());
                break;
            case "DiceGameManagerEvent":
                DiceGameManagerEvent msgD = (DiceGameManagerEvent) message;
                setDiceInWindow(msgD.getIdPlayer(), msgD.getDice(), msgD.getPosition());
                break;
            default:
                LOGGER.log(Level.SEVERE, "Type not found");
        }
    }
}