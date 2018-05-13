package it.polimi.ingsw.cards;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import org.junit.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrivateObjectiveDealerTest {

    @Test
    public void testPrivateDealer() {
        int numPlayer = 3;
        List<ObjectiveCard> privateObjective;
        CardController c = new CardController();

        privateObjective = c.dealPrivateObjective(numPlayer);
        assertEquals(privateObjective.size(), numPlayer);
        for (ObjectiveCard card:privateObjective) {
            assertEquals(card.getType(), CardType.PRIVATE);
        }

        numPlayer = 6;

        privateObjective = c.dealPrivateObjective(numPlayer);
        assertEquals(privateObjective.size(), 0);
    }

    @Test
    public void testDealtPrivateObjective() {
        int numPlayer = 4;
        List<ObjectiveCard> privateObjective;
        CardController c = new CardController();

        privateObjective = c.dealPrivateObjective(numPlayer);
        assertEquals(numPlayer, privateObjective.size());

        for(ObjectiveCard card:privateObjective) {
            assertEquals(1, card.getRule().getColorConstraints().size());
        }
    }
}
