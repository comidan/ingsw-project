package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.playables.DiceController;
import it.polimi.ingsw.sagrada.game.playables.RoundTrack;
import it.polimi.ingsw.sagrada.game.playables.ScoreTrack;
import it.polimi.ingsw.sagrada.game.playables.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;

/**
 *
 */

public class GameController implements Observer<Integer> {

    private List<Player> players;
    private List<Observable<Integer>> observers;
    private DiceController diceController;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private CardController cardController;
    private StateIterator stateIterator = StateIterator.getInstance();
    private static GameController gameController;

    private GameController(List<Player> players) {
        this.players = players;
        cardController = new CardController();
        observers = new ArrayList<>();
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

    public void setupGame() {
        while (stateIterator.hasNext() && stateIterator.getCurrentState() != DEAL_PUBLIC_OBJECTIVE) {
            stateIterator.next();

            switch (stateIterator.getCurrentState()) {
                case DEAL_PRIVATE_OBJECTIVE:
                    dealPrivateObjectiveState();
                    break;
                case DEAL_WINDOWS:
                    dealWindowsState();
                    break;
                case DEAL_TOOL:
                    dealToolState();
                    break;
                case DEAL_PUBLIC_OBJECTIVE:
                    dealPublicObjectiveState();
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

    private void dealWindowsState() {
        WindowParser windowParser = WindowParser.getInstance();
        for (Player p : players) {
            List<Window> windowa = windowParser.generateWindowCard();
        }
    }

    private void dealToolState() {
        // TODO implement here
    }

    private void dealPublicObjectiveState() {
        List<ObjectiveCard> publicObjective;
        publicObjective = cardController.dealPublicObjective();
        scoreTrack = ScoreTrack.getScoreTrack(publicObjective);
    }

    public void playRound() {
        // TODO implement here
    }

    private void scoreState() {
        // TODO implement here
    }

    public StateGameEnum getCurrentState() {
        return stateIterator.getCurrentState();
    }

    public int getPlayerNumber() {
        return players.size();
    }


    public Player selectStarterPlayer() {
        Random rand = new Random();
        int index = rand.nextInt(getPlayerNumber());
        return players.get(index);
    }

    @Override
    public void notify(Observable<Integer> observable, Integer data) {
        observable.update(data);
    }

    @Override
    public void notifyAll(Integer data) {
        observers.forEach(observer -> observer.update(data));
    }

    @Override
    public boolean subscribe(Observable<Integer> observable) {
        if (observers.contains(observable))
            return false;
        observers.add(observable);
        return true;
    }

    @Override
    public boolean unsubscribe(Observable<Integer> observable) {
        if (!observers.contains(observable))
            return false;
        observers.remove(observable);
        return true;
    }


}