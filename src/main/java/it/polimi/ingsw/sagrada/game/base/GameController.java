package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;

import it.polimi.ingsw.sagrada.game.playables.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;

/**
 *
 */

public class GameController {

    private List<Player> players;
    private DiceController diceController;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private CardController cardController;
    private StateIterator stateIterator = StateIterator.getInstance();
    private static GameController gameController;

    public void setupGame() {
        while (stateIterator.hasNext() && stateIterator.getCurrentState()!=DEAL_PUBLIC_OBJECTIVE) {
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
        // TODO implement here
    }

    private void dealToolState() {
        // TODO implement here
    }

    private void dealPublicObjectiveState() {
        List<ObjectiveCard> publicObjective;
        publicObjective=cardController.dealPublicObjective();
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


    /**
     * Default constructor
     */
    private GameController(List<Player> players) {
        this.players = players;
        cardController = new CardController();
    }

    public static GameController getGameController(List<Player> players) {
        if (gameController == null) {
            gameController = new GameController(players);
        }
        return gameController;
    }

    public int getPlayerNumber() {
        return players.size();
    }


    public Player selectStarterPlayer() {
        Random rand = new Random();
        int index = rand.nextInt(getPlayerNumber());
        return players.get(index);
    }

    //if diceNumber!= 0 it's draftPick, else if dice==null it's bagPick
    /*public void getDice(int diceNumber, Dice dice) {
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

    public List<Dice> showDraft() {
        return diceController.showDraft();
    }*/


}