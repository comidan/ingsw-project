package it.polimi.ingsw.sagrada.game.playables;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

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
     * @return iterator of current dices on round track
     */
    public List<Color> getAvailableColors() {

        List<Color> colorList = new ArrayList<>();
        for (List<Dice> list:roundDice)
            for (Dice dice : list) {
                Color color = dice.getColor();
                if (!colorList.contains(color)) colorList.add(color);
            }
        return colorList;
    }


    public Dice getDiceFromRound(Color color, int round) {
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
