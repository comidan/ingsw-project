package it.polimi.ingsw.cards.test;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

public class ToolDealerTest {

    @Test
    public void testJSONFile() {
        CardController c = new CardController();
        try {
            c.createObjectiveCards();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
