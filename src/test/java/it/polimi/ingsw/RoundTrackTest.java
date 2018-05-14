package it.polimi.ingsw;


import it.polimi.ingsw.sagrada.game.playables.*;
import org.junit.Test;

import it.polimi.ingsw.sagrada.game.base.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoundTrackTest {

    @Test
    public void testRoundTrack() {

        Dice dice = new Dice(3, Colors.RED);
        List<Dice> diceList = new ArrayList<>();
        diceList.add(dice);
        RoundTrack roundTrack = RoundTrack.getRoundTrack();
        assertNotNull(roundTrack);
        roundTrack.addDice(diceList, 2);
        assertEquals(roundTrack.getDiceFromRound(Colors.RED, 2), dice);

        List<Color> colorList = new ArrayList<>();
        colorList.add(dice.getColor());

        assertEquals(roundTrack.getAvailableColors(), colorList);

        assertEquals(roundTrack.getAvailableColors(), colorList);


        // get list of color dice from draft and assertEquals with roundTrack.getAvailableColors

    }
}

