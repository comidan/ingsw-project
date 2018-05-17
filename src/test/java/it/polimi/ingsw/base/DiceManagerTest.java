package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;
import it.polimi.ingsw.sagrada.game.playables.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiceManagerTest {
    private List<Dice> pickedDice;

    @Test
    public void testDicePick() {
        /*int numberOfPlayers = 3;
        DiceManager diceManager = new DiceManager(2, )
        assertEquals(90, DiceManager.getDiceManager(numberOfPlayers).getBagSize());
        pickedDice = new ArrayList<>();
        List<Dice> diceCompared = new ArrayList<>();
        DiceManager diceManager = DiceManager.getDiceManager(numberOfPlayers);
        diceManager.getDice(RoundStateEnum.SETUP_ROUND);
        pickedDice = diceManager.getDraft();
        diceCompared.add(pickedDice.get(0));

        //diceManager.setId(pickedDice.get(0).getId());
        //assertNotNull(diceManager.getDraft());
        //assertEquals(diceManager.getDice(RoundStateEnum.IN_GAME), diceCompared);*/
    }
}
