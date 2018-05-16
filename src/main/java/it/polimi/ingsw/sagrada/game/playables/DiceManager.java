package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Picker;
import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.DiceGameControllerEvent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiceManager implements Channel<DiceEvent> {
    private static DiceManager diceManager;
    private List<Dice> draftPool;
    private List<Dice> bagPool;
    private static final int DICE_PER_COLOR = 18;
    private int diceNumber;
    private int numberOfPlayers; // missing method to fetch this value, temporary value for testing
    private GameController gameController;

    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    /**
     * initialize pools
     */
    private DiceManager(int numberOfPlayers) {
        bagPool = new ArrayList<>();
        draftPool = new ArrayList<>();
        int id = 0;
        for (Color color : Colors.getColorList()) {
            for (int j = 0; j < DICE_PER_COLOR; j++) {
                bagPool.add(new Dice(id++, color));
            }
        }
        gameController = GameController.getGameController();
        this.numberOfPlayers = numberOfPlayers;
        diceNumber = this.numberOfPlayers * 2 + 1;
    }

    public static DiceManager getDiceManager(int numberOfPlayers) {
        if (diceManager == null) {
            diceManager = new DiceManager(numberOfPlayers);
        }
        return diceManager;
    }


    public int getBagSize() {
        return bagPool.size();
    } //TESTING

    /**
     * @return dice from draft
     */

    public List<Dice> getDraft() {
        return draftPool;
    } //TESTING

    /**
     * @return one or more dice
     */

//THIS MUST BE FIXED
    public List<Dice> getDice(RoundStateEnum stateEnum) {
        List<Dice> pickedDice = new ArrayList<>();

        switch (stateEnum) {
            case SETUP_ROUND:
                bagToDraft();
                break;
            case IN_GAME:
                //pickedDice = getDiceDraft(id);
                break;
            case END_ROUND:
                pickedDice = putDiceScoreTrack();
                break;
            default:
                LOGGER.log(Level.SEVERE, "Wrong state of round");
        }
        return pickedDice;
    }

    private void bagToDraft() {
        draftPool.clear();
        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();
        for (int i = 0; i < diceNumber; i++) {
            Dice dice = bagPicker.next();
            dice.roll();
            draftPool.add(dice);
        }
    }

    private Dice getDiceDraft(int idDice) {
        for (Dice dice : draftPool) {
            if (dice.getId() == idDice) {
                draftPool.remove(dice);
                return dice;
            }
        }
        return null;
    }

    private List<Dice> putDiceScoreTrack() {
        return new ArrayList<>(draftPool);
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public void dispatch(DiceEvent message) {
        Dice dice = getDiceDraft(message.getIdDice());
        DiceGameControllerEvent diceGameControllerEvent = new DiceGameControllerEvent(dice, message);
        gameController.dispatch(diceGameControllerEvent);
    }
}