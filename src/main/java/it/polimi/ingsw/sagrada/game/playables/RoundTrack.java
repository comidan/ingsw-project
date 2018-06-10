package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RoundTrack {
    private List<List<Dice>> roundDice;

    public RoundTrack() {
        roundDice = new ArrayList<>(10);
        for (int i=0; i<10; i++) {
            roundDice.add(new ArrayList<>());
        }
    }

    /**
     * @return iterator of current dice on round track
     */
    public List<Colors> getAvailableColors() {

        List<Colors> colorList = new ArrayList<>();
        for (List<Dice> list:roundDice)
            for (Dice dice : list) {
                Colors color = dice.getColor();
                if (!colorList.contains(color)) colorList.add(color);
            }
        return colorList;
    }


    public Dice getDiceFromRound(Colors color, int round) {
        for (Dice dice : roundDice.get(round)) {
            if (dice.getColor() == color)
                return dice;
        }
        return null;

    }

    /**
     * @param diceList - Dice to be added
     * @param round    - round reference
     */
    public void addDice(List<Dice> diceList, int round) {
        for (Dice dice : diceList)
            roundDice.get(round).add(dice);
    }


}
