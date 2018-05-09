

package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.playables.*;
import org.junit.Test;

import it.polimi.ingsw.sagrada.game.base.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoundTrackTest {

    @Test
    public void testRoundTrack() {

        Dice dice = new Dice(3, Colors.RED);
        List<Dice> diceList = new ArrayList<>();
        diceList.add(dice);
        RoundTrack roundTrack = RoundTrack.getRoundTrack();
        assertNotNull(roundTrack);
        roundTrack.addDice(diceList, 2);
        try {
            assertEquals(roundTrack.getDiceFromRound(Colors.RED, 2), dice);
        } catch (DiceNotFoundException exc) {
            //to be handled
        }
        List<Color> colorList = new ArrayList<>();
        colorList.add(dice.getColor());

        assertEquals(roundTrack.getAvailableColors(), colorList);

        assertThrows(DiceNotFoundException.class, () -> roundTrack.getDiceFromRound(Colors.GREEN, 2));
        assertEquals(roundTrack.getAvailableColors(), colorList);

        assertThrows(DiceNotFoundException.class, () -> roundTrack.getDiceFromRound(Colors.RED, 3));


        // get list of color dice from draft and assertEquals with roundTrack.getAvailableColors

    }
}

