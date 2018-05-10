package it.polimi.ingsw.cards;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PublicObjectiveDealerTest {
    @Test
    public void testPublicDealer() {
        List<ObjectiveCard> cards;
        List<ObjectiveCard> cardCheck = new ArrayList<>();
        CardController c = new CardController();

        cards = c.dealPublicObjective();

        for(ObjectiveCard card:cards) {
            assertFalse(cardCheck.contains(card));
            assertEquals(card.getType(), CardType.PUBLIC);
            cardCheck.add(card);
            System.out.println(card.toString());
        }
    }
}
