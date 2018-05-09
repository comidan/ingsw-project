

package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.playables.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


import java.util.*;

public class DiceControllerTest {
    List<Dice> picked_dice;

// test execution is simultaneous, test will always fail, how to fix?

    @Test
    public void testDicePick() {
        assertEquals(90, DiceController.getDiceController().getBagSize());
        int num = 9;
        try {
            picked_dice = new ArrayList<>();
            List<Dice> diceCompared = new ArrayList<>();
            picked_dice = DiceController.getDiceController().getDice(num, null);
            assertEquals(picked_dice.size(), num);
            Dice chosenDice = picked_dice.get(0);
            diceCompared.add(picked_dice.get(0));
            assertEquals(DiceController.getDiceController().getDice(0, chosenDice), diceCompared);
        } catch (InvalidDiceNumberException exc) {
            fail("Unexpected InvalidDiceNumberException was thrown");
        } catch (EmptyDraftException exc) {
            fail("Unexpected EmptyDraftException was thrown");
        } catch (DiceNotFoundException exc) {
            fail("Unexpected DiceNotFoundException was thrown");
        }


    }

    @Test
    public void testEmptyDraft() {
        DiceController diceController = DiceController.getDiceController();
        Colors color = new Colors();
        int num = 0;
        Dice dice = new Dice(1, color.RED);
        assertThrows(EmptyDraftException.class, () -> diceController.getDice(0, dice));
    }


    @Test
    public void TestWrongPickNumbers() {
        DiceController diceController = DiceController.getDiceController();
        int supLimit = 90;

        assertThrows(InvalidDiceNumberException.class, () -> diceController.getDice(supLimit, null));
        assertThrows(InvalidDiceNumberException.class, () -> diceController.getDice((5 * 2 + 1), null));


    }


}
