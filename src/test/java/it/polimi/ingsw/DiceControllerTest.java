

package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;
import it.polimi.ingsw.sagrada.game.playables.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


import java.util.*;

public class DiceControllerTest {
    List<Dice> pickedDice;

// test execution is simultaneous, test will always fail, how to fix?

    @Test
    public void testDicePick() {
        int numberOfPlayers = 3;
        assertEquals(90, DiceController.getDiceController(numberOfPlayers).getBagSize());
        pickedDice = new ArrayList<Dice>();
        List<Dice> diceCompared = new ArrayList<Dice>();
        DiceController diceController = DiceController.getDiceController(numberOfPlayers);
        diceController.getDice(RoundStateEnum.SETUP_ROUND);
        pickedDice = diceController.getDraft();
        diceCompared.add(pickedDice.get(0));
        diceController.setId(pickedDice.get(0).getId());
        assertNotNull(diceController.getDraft());
        assertEquals(diceController.getDice(RoundStateEnum.IN_GAME), diceCompared);

    }


}
