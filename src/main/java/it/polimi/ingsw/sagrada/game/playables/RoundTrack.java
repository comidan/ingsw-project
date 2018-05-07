package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.DTO;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 */
public class RoundTrack {

    private List<Dice>[] roundDice;
    private static RoundTrack roundTrack;


    /**
     * Default constructor
     */
    private RoundTrack() {
        roundDice = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            roundDice[i] = new ArrayList<Dice>();
        }

    }

    public static RoundTrack getRoundTrack() {

        if (roundTrack == null) {
            roundTrack = new RoundTrack();
        }

        return roundTrack;
    }


    /**
     * @return iterator of current dices on round track
     */

    public List<Color> getAvailableColors() {

        List<Color> colorList = new ArrayList<>();
        for (int i = 0; i < roundDice.length; i++) {
            for (Dice dice : roundDice[i]) {
                Color color = dice.getColor();
                if (!colorList.contains(color)) colorList.add(color);
            }
        }
        return colorList;
    }


    public Dice getDiceFromRound(Color color, int round) throws DiceNotFoundException {

        if (roundDice.length == 0) throw new DiceNotFoundException();

        for (Dice dice : roundDice[round]) {

            if (dice.getColor() == color)
                return dice;
        }

        throw new DiceNotFoundException(); //SHOULD HANDLE THIS CASE

    }


    /**
     * @param diceList - Dice to be added
     * @param round    - round reference
     */
    public void addDice(List<Dice> diceList, int round) {

        for (Dice dice : diceList) {

            roundDice[round].add(dice);
        }


    }


}
