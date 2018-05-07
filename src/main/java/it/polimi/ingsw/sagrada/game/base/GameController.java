package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.playables.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static it.polimi.ingsw.sagrada.game.base.StateGameController.*;

/**
 *
 */

public class GameController {

    private Player[] players;
    private DiceController diceController;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private CardController cardController;
    private StateGameController state;
    private StateIterator stateIterator = StateIterator.getInstance();
    private int currentRound;
    private static GameController gameController;

    /**
     * Default constructor
     */
    public GameController() {

    }

    public void setupGame() {
        while (stateIterator.hasNext() && state != DEAL_PUBLIC_OBJECTIVE) {
            state = stateIterator.next();
            switch (state) {
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
        privateObjective = cardController.dealPrivateObjective(players.length);
        for (int i = 0; i < players.length; i++) {
            players[i].setPrivateObjectiveCard(privateObjective.get(i));
        }
    }

    private void dealWindowsState() {
        // TODO implement here
    }

    private void dealToolState() {
        // TODO implement here
    }

    private void dealPublicObjectiveState() {
        // TODO implement here
    }

    public void playRound() {
        // TODO implement here
    }


    /**
     * Default constructor
     */
    private GameController(Player[] players) {
        this.players = players;
        for (Player player : players) {
            //create new window for each player
        }
        currentRound = 0;
    }

    public static GameController getGameController(Player[] players) {
        if (gameController == null) {
            gameController = new GameController(players);
        }
        return gameController;
    }

    public int getPlayerNumber() {
        return players.length;
    }


    public Player selectStarterPlayer() {
        Random rand = new Random();
        int index = rand.nextInt(getPlayerNumber());
        return players[index];
    }

    //if diceNumber!= 0 it's draftPick, else if dice==null it's bagPick
    public void getDice(int diceNumber, Dice dice) {
        try {
            diceController.getDice(diceNumber, null);
        } catch (EmptyDraftException | InvalidDiceNumberException | DiceNotFoundException exc) {
            // how do we handle exceptions
        }
    }


    public void addDiceToRoundTrack() {
        List<Dice> diceList = diceController.takeDiceForRound();
        roundTrack.addDice(diceList, getCurrentRound());
        this.currentRound += 1;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public List<Dice> showDraft() {
        return diceController.showDraft();
    }


}