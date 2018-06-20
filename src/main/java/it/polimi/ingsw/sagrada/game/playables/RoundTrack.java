package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * The Class RoundTrack.
 */
public class RoundTrack {

    private static final int MAX_ROUND = 10;

    /** The round dice. */
    private List<List<Dice>> roundDice;

    /**
     * Instantiates a new round track.
     */
    public RoundTrack() {
        roundDice = new ArrayList<>(MAX_ROUND);
        IntStream.range(0, MAX_ROUND).forEach(v -> roundDice.add(new ArrayList<>()));
    }

    /**
     * Gets the available colors.
     *
     * @return iterator of current dice on round track
     */
    public List<Colors> getAvailableColors() {
        List<Colors> colorList = new ArrayList<>();
        roundDice.forEach(list -> list.stream().filter(dice -> !colorList.contains(dice.getColor())).forEach(dice -> colorList.add(dice.getColor())));
        return colorList;
    }


    /**
     * Gets the dice from round.
     *
     * @param color the color
     * @param round the round
     * @return the dice from round
     */
    public Dice getDiceFromRound(Colors color, int round) {
        for (Dice dice : roundDice.get(round)) {
            if (dice.getColor() == color)
                return dice;
        }
        return null;

    }

    /**
     * Adds the dice.
     *
     * @param diceList - Dice to be added
     * @param round    - round reference
     */
    public void addDice(List<Dice> diceList, int round) {
        diceList.forEach(roundDice.get(round)::add);
    }


}
