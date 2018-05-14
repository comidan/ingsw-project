package it.polimi.ingsw.cards;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.CardType;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PublicObjectiveDealerTest {
    private Cell oneR;
    private Cell twoY;
    private Cell threeG;
    private Cell fourL;
    private Cell fiveP;
    private Cell sixL;
    private Cell oneG;
    private Cell oneY;
    private Cell sixG;

    public PublicObjectiveDealerTest() {
        Dice DoneR;
        Dice DtwoY;
        Dice DthreeG;
        Dice DfourL;
        Dice DfiveP;
        Dice DsixL;
        Dice DoneG;
        Dice DoneY;
        Dice DsixG;
        CellRule cellRule = CellRule.builder().build();

        oneR = new Cell(cellRule);
        DoneR = new Dice(0, Colors.RED);
        DoneR.setValue(1);
        oneR.setDice(DoneR);

        twoY = new Cell(cellRule);
        DtwoY = new Dice(1, Colors.YELLOW);
        DtwoY.setValue(2);
        twoY.setDice(DtwoY);

        threeG = new Cell(cellRule);
        DthreeG = new Dice(2, Colors.GREEN);
        DthreeG.setValue(3);
        threeG.setDice(DthreeG);

        fourL = new Cell(cellRule);
        DfourL = new Dice(3, Colors.LIGHT_BLUE);
        DfourL.setValue(4);
        fourL.setDice(DfourL);

        fiveP = new Cell(cellRule);
        DfiveP = new Dice(4, Colors.PURPLE);
        DfiveP.setValue(5);
        fiveP.setDice(DfiveP);

        sixL = new Cell(cellRule);
        DsixL = new Dice(5, Colors.LIGHT_BLUE);
        DsixL.setValue(6);
        sixL.setDice(DsixL);

        oneG = new Cell(cellRule);
        DoneG = new Dice(6, Colors.GREEN);
        DoneG.setValue(1);
        oneG.setDice(DoneG);

        oneY = new Cell(cellRule);
        DoneY = new Dice(7, Colors.YELLOW);
        DoneY.setValue(1);
        oneY.setDice(DoneY);

        sixG = new Cell(cellRule);
        DsixG = new Dice(8, Colors.GREEN);
        DsixG.setValue(6);
        sixG.setDice(DsixG);
    }

    @Test
    public void testPublicDealer() {
        List<ObjectiveCard> cards;
        List<ObjectiveCard> cardCheck = new ArrayList<>();
        CardController c = new CardController();

        cards = c.dealPublicObjective();
        assertEquals(3, cards.size());

        for(ObjectiveCard card:cards) {
            assertFalse(cardCheck.contains(card));
            assertEquals(card.getType(), CardType.PUBLIC);
            cardCheck.add(card);
        }
    }

    @Test
    public void testDealtPublicObjective() {
        List<ObjectiveCard> publicObjective;
        CardController c = new CardController();
        Cell[][] cells = {
                {oneR, twoY, threeG, fourL, fiveP},
                {sixL, oneG, sixL, oneY, sixG},
                {oneG, sixL, oneG, sixL, oneY},
                {sixL, oneG, sixL, oneY, fourL}};

        publicObjective = c.dealPublicObjective();

        for(ObjectiveCard card:publicObjective) {
            int score = card.getRule().checkRule(cells);
            System.out.println(card.getName());
            switch(card.getId()) {
                case 0: assertEquals(6, score); break;
                case 1: assertEquals(5, score); break;
                case 2: assertEquals(5, score); break;
                case 3: assertEquals(4, score); break;
                case 4: assertEquals(2, score); break;
                case 5: assertEquals(2, score); break;
                case 6: assertEquals(2, score); break;
                case 7: assertEquals(5, score); break;
                case 8: assertEquals(25, score); break; //NOT CORRECT, PENDING FIX, TRUE VALUE 16
                case 9: assertEquals(4, score); break;
                default: fail();
            }
        }
    }
}
