package it.polimi.ingsw.cards;

import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ToolDealerTest {
    @Test
    public void testToolDealer() {
        List<ToolCard> toolCards;
        List<ToolCard> cardCheck = new ArrayList<>();
        CardManager cardManager = new CardManager();

        toolCards = cardManager.dealTool();
        assertEquals(3, toolCards.size());

        for(ToolCard card:toolCards) {
            assertFalse(cardCheck.contains(card));
            cardCheck.add(card);
        }
    }
}
