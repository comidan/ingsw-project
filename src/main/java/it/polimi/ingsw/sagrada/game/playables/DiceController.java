package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.GameController;
import it.polimi.ingsw.sagrada.game.base.Observable;
import it.polimi.ingsw.sagrada.game.base.Picker;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DiceController implements Observable<Integer> {
    private GameController gameController;
    private static DiceController diceController;
    private List<Dice> draftPool;
    private List<Dice> bagPool;
    private int numberOfPlayers = 4; // missing method to fetch this value, temporary value for testing

    /**
     * initialize pools
     */
    private DiceController() {
        bagPool = new ArrayList<>();
        draftPool = new ArrayList<>();
        for (Color color : Colors.getColorList()) {
            for (int j = 0; j < 18; j++) {
                bagPool.add(new Dice(generateRandomInt(6), color));
            }
        }
    }

    public static DiceController getDiceController() {
        if (diceController == null) {
            diceController = new DiceController();
        }
        return diceController;
    }


    public int getBagSize() {
        System.out.println(bagPool.size());
        return bagPool.size();
    }

    /**
     * @return dice from draft
     */

    public List<Dice> showDraft() {
        return draftPool;
    }

    private List<Dice> getDiceDraft(Dice chosenDice) throws EmptyDraftException, DiceNotFoundException {
        List<Dice> picked_dice = new ArrayList<>();
        if (draftPool.size() == 0) throw new EmptyDraftException();
        if (!draftPool.contains(chosenDice)) throw new DiceNotFoundException();
        picked_dice.add(draftPool.remove(draftPool.indexOf(chosenDice)));
        return picked_dice;
    }

    /**
     * @param num - number of dices to pick
     * @return num-dices from bag
     */
    private List<Dice> getDiceBag(int num) throws InvalidDiceNumberException {
        if (num != numberOfPlayers * 2 + 1 || num > bagPool.size()) throw new InvalidDiceNumberException();
        List<Dice> picked_dice = new ArrayList<>();
        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();

        for (int i = 0; i < num; i++) {
            Dice dice = bagPicker.next();;
            picked_dice.add(dice);
            draftPool.add(dice);
        }
        return picked_dice;
    }

    /**
     * @return one or more dice
     */

//THIS MUST BE FIXED
    public List<Dice> getDice(int num, Dice chosenDice) throws InvalidDiceNumberException, EmptyDraftException, DiceNotFoundException {
        if (chosenDice == null && num != 0) {
            return getDiceBag(num);
        } else {
            return getDiceDraft(chosenDice);
        }

    }

    //CAN BE IMPROVED
    public List<Dice> takeDiceForRound() {
        List<Dice> takenDiceList = new ArrayList<Dice>();
        for (Dice dice : draftPool) {
            Dice takenDice = draftPool.remove(draftPool.size());
            takenDiceList.add(takenDice);
        }
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
}


