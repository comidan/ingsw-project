package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.sagrada.game.base.DataType.WINDOW_MESSAGE;
import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;

/**
 *
 */

public class GameController implements Channel<Message> {

    private List<Player> players;
    private DiceController diceController;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private CardController cardController;
    private ToolManager toolManager;
    private StateIterator stateIterator = StateIterator.getInstance();
    private RoundIterator roundIterator = RoundIterator.getRoundIterator();
    private PlayerIterator playerIterator;
    private WindowParser windowParser;
    private static GameController gameController;

    private int numWindowDealed = 0;

    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private GameController(List<Player> players) {
        this.players = players;
        cardController = new CardController();
        diceController = DiceController.getDiceController(players.size());
        playerIterator = PlayerIterator.getPlayerIterator(players);
        windowParser = new WindowParser();
    }

    public static GameController getGameController(List<Player> players) {
        if (gameController == null) {
            gameController = new GameController(players);
        }
        return gameController;
    }

    public static GameController getGameController() {
        return gameController;
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
        privateObjective = cardController.dealPrivateObjective(players.size());
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPrivateObjectiveCard(privateObjective.get(i));
        }
    }

    private void dealToolState() {
        List<ToolCard> tools = cardController.dealTool();
        toolManager = ToolManager.getInstance(tools);
    }

    private void dealPublicObjectiveState() {
        List<ObjectiveCard> publicObjective;
        publicObjective = cardController.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
    }

    private void dealWindowState() {
        for(Player p:players) {
            List<Integer> windowsId = windowParser.dealWindowId();
            //this id will be passed to Player in the view
        }
    }

    private void dealWindowsToPlayer(Player player, Window window) {
        player.setWindow(window);
        numWindowDealed++;
        if(numWindowDealed == players.size() && stateIterator.hasNext()) {
            stateIterator.next();
        }
    }

    public void playRound() {

        while (roundIterator.hasNext()) {
            switch (roundIterator.next()) {
                case SETUP_ROUND:
                    diceController.getDice(RoundStateEnum.SETUP_ROUND);
                    break;
                case IN_GAME:
                    while (playerIterator.hasNext()) {
                        playerIterator.next();
                        diceController.getDice(RoundStateEnum.IN_GAME);
                    }
                    break;
                case END_ROUND:
                    diceController.getDice(RoundStateEnum.END_ROUND);
                    break;
            }
        }
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
        switch(eventType) {
            case "WindowGameControllerMessage": ; break;
            default: LOGGER.log(Level.SEVERE, "Type not found");
        }
    }
}