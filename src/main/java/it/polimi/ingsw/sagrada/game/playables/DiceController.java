package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Observable;
import it.polimi.ingsw.sagrada.game.base.Picker;
import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DiceController implements Observable<Integer> {
    private static DiceController diceController;
    private List<Dice> draftPool;
    private List<Dice> bagPool;
    private static final int DICE_PER_COLOR = 18;
    private int diceNumber;
    private int numberOfPlayers; // missing method to fetch this value, temporary value for testing
    private int currentId; //FOR TESTING, MUST BE REMOVED !

    /**
     * initialize pools
     */
    private DiceController(int numberOfPlayers) {
        bagPool = new ArrayList<>();
        draftPool = new ArrayList<>();
        int id = 0;
        for (Color color : Colors.getColorList()) {
            for (int j = 0; j < DICE_PER_COLOR; j++) {
                bagPool.add(new Dice(id++, color));
            }
        }
        this.numberOfPlayers = numberOfPlayers;
        diceNumber = numberOfPlayers * 2 + 1;
    }

    public static DiceController getDiceController(int numberOfPlayers) {
        if (diceController == null) {
            diceController = new DiceController(numberOfPlayers);
        }
        return diceController;
    }


    public int getBagSize() {
        return bagPool.size();
    }

    /**
     * @return dice from draft
     */

    public List<Dice> getDraft() {
        return draftPool;
    }

    private List<Dice> getDiceDraft(int diceId) {
        diceId = this.currentId; // FOR TESTING, MUST BE REMOVED
        List<Dice> pickedDice = new ArrayList<>();
        for (Dice dice : draftPool) {
            if (dice.getId() == diceId) pickedDice.add(dice);
        }

        return pickedDice;
    }

    /**
     * @return num-dices from bag
     */
    private void bagToDraft() {

        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();
        for (int i = 0; i < diceNumber; i++) {
            Dice dice = bagPicker.next();
            dice.setValue(generateRandomInt(6));
            draftPool.add(dice);
        }
    }

    /**
     * @return one or more dice
     */

//THIS MUST BE FIXED
    public List<Dice> getDice(RoundStateEnum stateEnum) {
        Random rand = new Random();
        int id = rand.nextInt(90) + 1;
        List<Dice> pickedDice = new ArrayList<>();

        switch (stateEnum) {
            case SETUP_ROUND:
                bagToDraft();
                break;
            case IN_GAME:
                pickedDice = getDiceDraft(id);
                break;
            case END_ROUND:
                pickedDice = putDiceScoreTrack();
                break;


        }
        return pickedDice;
    }

    //CAN BE IMPROVED
    public List<Dice> putDiceScoreTrack() {
        List<Dice> takenDiceList = new ArrayList<>(draftPool);
        draftPool.forEach(draftPool::remove);
        return takenDiceList;

    }


    private int generateRandomInt(int bound) {
        Random rand = new Random();
        return rand.nextInt(bound) + 1;
    }


    @Override
    public void update(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    //FOR TESTING, IT MUST BE REMOVED!
    public void setId(int id) {
        currentId = id;

    }
}


