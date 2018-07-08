package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class IntercommunicationToolEventVisitor implements ToolGameMessageVisitor {

    private static String idPlayer = "test";
    private static int id = 0;
    private static int round = 1;
    private static int score = 1;
    private static int sampleValue = 3;
    private static int port = 49152;
    private static String time = "3";
    private static WindowSide side = WindowSide.FRONT;
    private static String source = CommandKeyword.DRAFT;
    private static boolean valid = new Random().nextInt() % 2 == 0;
    private static Position position = new Position(0, 0);
    private static Dice dice = new Dice(id, Colors.RED);
    private static List<Dice> diceList= new ArrayList<>();
    private static List<Integer> ids = new ArrayList<>();
    private static List<String> players = new ArrayList<>();
    private static List<WindowSide> sides = new ArrayList<>();

    static {
        diceList.add(dice);
        ids.add(0);
        ids.add(1);
        players.add(idPlayer);
        sides.add(side);
    }

    @Test
    public void testIntercommunication() {
        EndTurnEvent endTurnEvent = new EndTurnEvent(idPlayer);
        endTurnEvent.accept(this);
        DiceDraftSelectionEvent diceDraftSelectionEvent = new DiceDraftSelectionEvent(idPlayer, id);
        diceDraftSelectionEvent.accept(this);
        ToolEvent toolEvent = new ToolEvent(idPlayer, id);
        toolEvent.accept(this);
        DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent =
                new DiceRoundTrackSelectionEvent(idPlayer, id, round);
        diceDraftSelectionEvent.accept(this);
        DiceEvent diceEvent = new DiceEvent(idPlayer, id, position, source);
        diceEvent.accept(this);
        DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent =
                new DiceRoundTrackColorSelectionEvent(idPlayer, Colors.RED);
        diceRoundTrackColorSelectionEvent.accept(this);
    }

    /**
     * Visit.
     *
     * @param endTurnEvent the end turn event
     */
    @Override
    public void visit(EndTurnEvent endTurnEvent) {
        assertEquals(idPlayer, endTurnEvent.getIdPlayer());
    }

    /**
     * Visit.
     *
     * @param toolEvent the tool event
     */
    @Override
    public void visit(ToolEvent toolEvent) {
        assertEquals(idPlayer, toolEvent.getPlayerId());
        assertEquals(id, toolEvent.getToolId());
    }

    /**
     * Visit.
     *
     * @param diceDraftSelectionEvent the dice draft selection event
     */
    @Override
    public void visit(DiceDraftSelectionEvent diceDraftSelectionEvent) {
        assertEquals(idPlayer, diceDraftSelectionEvent.getIdPlayer());
        assertEquals(id, diceDraftSelectionEvent.getIdDice());
    }

    /**
     * Visit.
     *
     * @param diceRoundTrackSelectionEvent the dice round track selection event
     */
    @Override
    public void visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent) {
        assertEquals(idPlayer, diceRoundTrackSelectionEvent.getPlayerId());
        assertEquals(id, diceRoundTrackSelectionEvent.getDiceId());
        assertEquals(round, diceRoundTrackSelectionEvent.getTurn());
    }

    /**
     * Visit.
     *
     * @param diceEvent the dice event
     */
    @Override
    public void visit(DiceEvent diceEvent) {
        assertEquals(id, diceEvent.getIdDice());
        assertEquals(position, diceEvent.getPosition());
        assertEquals(source, diceEvent.getSrc());
        assertEquals(idPlayer, diceEvent.getIdPlayer());
    }

    /**
     * Visit.
     *
     * @param diceRoundTrackColorSelectionEvent the dice round track color selection event
     */
    @Override
    public void visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent) {
        assertEquals(Colors.RED, diceRoundTrackColorSelectionEvent.getConstraint());
        assertEquals(idPlayer, diceRoundTrackColorSelectionEvent.getPlayerId());
    }
}
