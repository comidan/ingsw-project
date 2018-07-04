package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableWindowToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IntercommunicationActionResponseVisitorTest implements ActionMessageVisitor, ResponseMessageVisitor {

    private static String idPlayer = "test";
    private static String idPlayer2 = "test2";
    private static int id = 0;
    private static int score = 1;
    private static int round = 1;
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
        ids.add(id);
        ids.add(id + 1);
        players.add(idPlayer);
        players.add(idPlayer2);
        sides.add(side);
        sides.add(side);
    }

    @Test
    public void testIntercommunication() {
        DiceEvent diceEvent = new DiceEvent(idPlayer, id, position, source);
        diceEvent.accept(this);
        WindowEvent windowEvent = new WindowEvent(idPlayer, id, side);
        windowEvent.accept(this);
        EndTurnEvent endTurnEvent = new EndTurnEvent(idPlayer);
        endTurnEvent.accept(this);
        DiceResponse diceResponse = new DiceResponse(source, diceList);
        diceResponse.accept(this);
        WindowResponse windowResponse = new WindowResponse(idPlayer, ids);
        windowResponse.accept(this);
        BeginTurnEvent beginTurnEvent = new BeginTurnEvent(idPlayer);
        beginTurnEvent.accept(this);
        OpponentWindowResponse opponentWindowResponse = new OpponentWindowResponse(players, ids, sides);
        opponentWindowResponse.accept(this);
        OpponentDiceMoveResponse opponentDiceMoveResponse = new OpponentDiceMoveResponse(idPlayer, dice, position);
        opponentDiceMoveResponse.accept(this);
        NewTurnResponse newTurnResponse = new NewTurnResponse(round);
        newTurnResponse.accept(this);
        RuleResponse ruleResponse = new RuleResponse(idPlayer, valid);
        ruleResponse.accept(this);
        PublicObjectiveResponse publicObjectiveResponse = new PublicObjectiveResponse(ids);
        publicObjectiveResponse.accept(this);
        PrivateObjectiveResponse privateObjectiveResponse = new PrivateObjectiveResponse(id, idPlayer);
        privateObjectiveResponse.accept(this);
        ToolCardResponse toolCardResponse = new ToolCardResponse(ids);
        toolCardResponse.accept(this);
        ScoreResponse scoreResponse = new ScoreResponse(players, Arrays.asList(score, score + 1));
        scoreResponse.accept(this);
    }

    @Override
    public String visit(DiceEvent diceEvent) {
        assertEquals(id, diceEvent.getIdDice());
        assertEquals(position, diceEvent.getPosition());
        assertEquals(source, diceEvent.getSrc());
        return null;
    }

    @Override
    public String visit(DiceDraftSelectionEvent diceDraftSelectionEvent) {
        return null;
    }

    @Override
    public String visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent) {
        return null;
    }

    @Override
    public String visit(WindowEvent windowEvent) {
        assertEquals(id, windowEvent.getIdWindow());
        assertEquals(side, windowEvent.getWindowSide());
        assertEquals(idPlayer, windowEvent.getIdPlayer());
        return null;
    }

    @Override
    public String visit(EndTurnEvent endTurnEvent) {
        assertEquals(idPlayer, endTurnEvent.getIdPlayer());
        return null;
    }

    /**
     * Visit.
     *
     * @param byteStreamWindowEvent the window image
     * @return the string
     */
    @Override
    public String visit(ByteStreamWindowEvent byteStreamWindowEvent) {
        return null;
    }

    @Override
    public String visit(Message message) {
        return null;
    }

    @Override
    public String visit(ToolEvent toolEvent) {
        return null;
    }

    @Override
    public String visit(DiceResponse diceResponse) {
        assertArrayEquals(diceList.toArray(new Dice[0]), diceResponse.getDiceList().toArray(new Dice[0]));
        assertEquals(source, diceResponse.getDst());
        return null;
    }

    @Override
    public String visit(WindowResponse windowResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), windowResponse.getIds().toArray(new Integer[0]));
        assertEquals(idPlayer, windowResponse.getPlayerId());
        return null;
    }

    @Override
    public String visit(BeginTurnEvent beginTurnEvent) {
        assertEquals(idPlayer, beginTurnEvent.getIdPlayer());
        return null;
    }

    @Override
    public String visit(OpponentWindowResponse opponentWindowResponse) {
        assertArrayEquals(players.toArray(new String[0]), opponentWindowResponse.getPlayers().toArray(new String[0]));
        assertEquals(id, opponentWindowResponse.getPlayerWindowId(idPlayer).intValue());
        assertEquals(id + 1, opponentWindowResponse.getPlayerWindowId(idPlayer2).intValue());
        assertEquals(side, opponentWindowResponse.getPlayerWindowSide(idPlayer));
        return null;
    }

    @Override
    public String visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        assertEquals(dice, opponentDiceMoveResponse.getDice());
        assertEquals(idPlayer, opponentDiceMoveResponse.getIdPlayer());
        assertEquals(position, opponentDiceMoveResponse.getPosition());
        return null;
    }

    @Override
    public String visit(NewTurnResponse newTurnResponse) {
        assertEquals(round, newTurnResponse.getRound());
        return null;
    }

    @Override
    public String visit(RuleResponse ruleResponse) {
        assertEquals(idPlayer, ruleResponse.getPlayerId());
        assertEquals(valid, ruleResponse.isMoveValid());
        return null;
    }

    @Override
    public String visit(PublicObjectiveResponse publicObjectiveResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), publicObjectiveResponse.getIdObjective().toArray(new Integer[0]));
        return null;
    }

    @Override
    public String visit(PrivateObjectiveResponse privateObjectiveResponse) {
        assertEquals(id, privateObjectiveResponse.getIdObjective());
        assertEquals(idPlayer, privateObjectiveResponse.getIdPlayer());
        return null;
    }

    @Override
    public String visit(ToolCardResponse toolCardResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), toolCardResponse.getIds().toArray(new Integer[0]));
        return null;
    }

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     * @return the string
     */
    @Override
    public String visit(ScoreResponse scoreResponse) {
        assertEquals(players.get(1), scoreResponse.getUsernames().iterator().next());
        assertEquals(score, scoreResponse.getScore(idPlayer));
        return null;
    }

    @Override
    public String visit(ToolResponse toolResponse) {
        return null;
    }

    @Override
    public String visit(EndTurnResponse endTurnResponse) {
        return null;
    }

    @Override
    public String visit(TimeRemainingResponse timeRemainingResponse) {
        return null;
    }

    @Override
    public String visit(EnableWindowToolResponse enableWindowToolResponse) {
        return null;
    }

    @Override
    public String visit(RoundTrackToolResponse roundTrackToolResponse) {
        return null;
    }
}
