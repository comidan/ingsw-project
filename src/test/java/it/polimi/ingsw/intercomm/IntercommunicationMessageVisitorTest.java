package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IntercommunicationMessageVisitorTest implements MessageVisitor {

    private static String idPlayer = "test";
    private static int id = 0;
    private static int round = 1;
    private static int score = 1;
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
        AddPlayerEvent addPlayerEvent = new AddPlayerEvent(idPlayer, 0);
        addPlayerEvent.accept(this);
        BeginTurnEvent beginTurnEvent = new BeginTurnEvent(idPlayer);
        beginTurnEvent.accept(this);
        MatchTimeEvent matchTimeEvent = new MatchTimeEvent(time);
        matchTimeEvent.accept(this);
        HeartbeatInitEvent heartbeatInitEvent = new HeartbeatInitEvent(port);
        heartbeatInitEvent.accept(this);
        RemovePlayerEvent removePlayerEvent = new RemovePlayerEvent(idPlayer);
        removePlayerEvent.accept(this);
        WindowResponse windowResponse = new WindowResponse(idPlayer, ids);
        windowResponse.accept(this);
        OpponentWindowResponse opponentWindowResponse = new OpponentWindowResponse(players, ids, sides);
        opponentWindowResponse.accept(this);
        EndTurnEvent endTurnEvent = new EndTurnEvent(idPlayer);
        endTurnEvent.accept(this);
        DiceResponse diceResponse = new DiceResponse(source, diceList);
        diceResponse.accept(this);
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
        ScoreResponse scoreResponse = new ScoreResponse(players, Arrays.asList(score));
        scoreResponse.accept(this);
    }

    @Override
    public void visit(Message message) {
    }

    @Override
    public void visit(AddPlayerEvent addPlayerEvent) {
        assertEquals(idPlayer, addPlayerEvent.getUsername());
    }

    @Override
    public void visit(BeginTurnEvent beginTurnEvent) {
        assertEquals(idPlayer, beginTurnEvent.getIdPlayer());
    }

    @Override
    public void visit(MatchTimeEvent matchTimeEvent) {
        assertEquals(time, matchTimeEvent.getTime());
    }

    @Override
    public void visit(HeartbeatInitEvent heartbeatInitEvent) {
        assertEquals(port, heartbeatInitEvent.getHeartbeatPort());
    }

    @Override
    public void visit(RemovePlayerEvent removePlayerEvent) {
        assertEquals(idPlayer, removePlayerEvent.getUsername());
    }

    @Override
    public void visit(WindowResponse windowResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), windowResponse.getIds().toArray(new Integer[0]));
        assertEquals(idPlayer, windowResponse.getPlayerId());
    }

    @Override
    public void visit(OpponentWindowResponse opponentWindowResponse) {
        assertArrayEquals(players.toArray(new String[0]), opponentWindowResponse.getPlayers().toArray(new String[0]));
        assertEquals(id, opponentWindowResponse.getPlayerWindowId(idPlayer).intValue());
        assertEquals(side, opponentWindowResponse.getPlayerWindowSide(idPlayer));
    }

    @Override
    public void visit(DiceResponse diceResponse) {
        assertArrayEquals(diceList.toArray(new Dice[0]), diceResponse.getDiceList().toArray(new Dice[0]));
        assertEquals(source, diceResponse.getDst());
    }

    @Override
    public void visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        assertEquals(dice, opponentDiceMoveResponse.getDice());
        assertEquals(idPlayer, opponentDiceMoveResponse.getIdPlayer());
        assertEquals(position, opponentDiceMoveResponse.getPosition());
    }

    @Override
    public void visit(RuleResponse ruleResponse) {
        assertEquals(idPlayer, ruleResponse.getPlayerId());
        assertEquals(valid, ruleResponse.isMoveValid());
    }

    @Override
    public void visit(NewTurnResponse newTurnResponse) {
        assertEquals(round, newTurnResponse.getRound());
    }

    @Override
    public void visit(PrivateObjectiveResponse privateObjectiveResponse) {
        assertEquals(id, privateObjectiveResponse.getIdObjective());
        assertEquals(idPlayer, privateObjectiveResponse.getIdPlayer());
    }

    @Override
    public void visit(PublicObjectiveResponse publicObjectiveResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), publicObjectiveResponse.getIdObjective().toArray(new Integer[0]));
    }

    @Override
    public void visit(ToolCardResponse toolCardResponse) {
        assertArrayEquals(ids.toArray(new Integer[0]), toolCardResponse.getIds().toArray(new Integer[0]));
    }

    @Override
    public void visit(ScoreResponse scoreResponse) {
        assertEquals(players.get(0), scoreResponse.getUsernames().iterator().next());
        assertEquals(score, scoreResponse.getScore(idPlayer));
    }

    @Override
    public void visit(ToolResponse toolResponse) {

    }

    @Override
    public void visit(EndTurnResponse endTurnResponse) {

    }

    @Override
    public void visit(TimeRemainingResponse timeRemainingResponse) {

    }
}
