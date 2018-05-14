package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;
import it.polimi.ingsw.sagrada.game.playables.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiceControllerTest {
    private List<Dice> pickedDice;

    @Test
    public void testDicePick() {
        int numberOfPlayers = 3;
        assertEquals(90, DiceController.getDiceController(numberOfPlayers).getBagSize());
        pickedDice = new ArrayList<>();
        List<Dice> diceCompared = new ArrayList<>();
        DiceController diceController = DiceController.getDiceController(numberOfPlayers);
        diceController.getDice(RoundStateEnum.SETUP_ROUND);
        pickedDice = diceController.getDraft();
        diceCompared.add(pickedDice.get(0));
        diceController.setId(pickedDice.get(0).getId());
        assertNotNull(diceController.getDraft());
        assertEquals(diceController.getDice(RoundStateEnum.IN_GAME), diceCompared);

    }


}
