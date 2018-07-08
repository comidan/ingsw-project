package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackReconnectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ColorBagToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableWindowToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.protocols.application.MessageParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NetworkCommunicationProtocolTest implements ResponseMessageVisitor {

    private static String idPlayer = "test";
    private static int id = 0;
    private static int round = 1;
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
    private MessageParser messageParser = new MessageParser();

    static {
        diceList.add(dice);
        ids.add(0);
        ids.add(1);
        players.add(idPlayer);
        sides.add(side);
    }

    @Test
    public void testNetworkCommunicationProtocolTest() {
        ResponseVisitor[] messages = new ResponseVisitor[]{ new DiceResponse(source, diceList),
                                                            new WindowResponse(idPlayer, ids),
                                                            new BeginTurnEvent(idPlayer),
                                                            new OpponentWindowResponse(players, ids, sides),
                                                            new OpponentDiceMoveResponse(idPlayer, dice, position),
                                                            new NewTurnResponse(round),
                                                            new RuleResponse(idPlayer, valid),
                                                            new PublicObjectiveResponse(ids),
                                                            new PrivateObjectiveResponse(id, idPlayer),
                                                            new ToolCardResponse(ids),
                                                            new ToolResponse(true, idPlayer, 0, sampleValue),
                                                            new EndTurnResponse(idPlayer),
                                                            new TimeRemainingResponse(idPlayer, sampleValue),
                                                            new EnableWindowToolResponse(idPlayer, sampleValue),
                                                            new RoundTrackToolResponse(new DiceResponse(CommandKeyword.DRAFT, diceList), round),
                                                            new ColorBagToolResponse(idPlayer, Colors.RED, id),
                                                            new DiceRoundTrackReconnectionEvent(Arrays.asList(diceList), idPlayer)};
        List<ResponseVisitor> messageList = Arrays.asList(messages);
        messageList.forEach(message -> message.accept(this));
        CommandParser commandParser = new CommandParser();
        assertTrue(JsonMessage.parseJsonData(commandParser.crateJSONLoginLobbyResponse(port)) instanceof HeartbeatInitEvent);
        assertTrue(JsonMessage.parseJsonData(commandParser.createJSONAddLobbyPlayer(idPlayer, 0)) instanceof AddPlayerEvent);
        assertTrue(JsonMessage.parseJsonData(commandParser.createJSONLoginResponse(idPlayer, port)) instanceof LobbyLoginEvent);
        assertTrue(JsonMessage.parseJsonData(commandParser.createJSONCountdown(time)) instanceof MatchTimeEvent);
        assertTrue(JsonMessage.parseJsonData(commandParser.createJSONRemoveLobbyPlayer(idPlayer)) instanceof RemovePlayerEvent);
    }

    @Override
    public String visit(Message message) {
        return null;
    }

    @Override
    public String visit(DiceResponse diceResponse) {
        String json = messageParser.createJsonResponse(diceResponse);
        DiceResponse diceResponseMessage = (DiceResponse) JsonMessage.parseJsonData(json);
        assertArrayEquals(diceResponseMessage.getDiceList().toArray(new Dice[0]), diceResponse.getDiceList().toArray(new Dice[0]));
        assertEquals(diceResponseMessage.getDst(), diceResponse.getDst());
        return null;
    }

    @Override
    public String visit(WindowResponse windowResponse) {
        String json = messageParser.createJsonResponse(windowResponse);
        WindowResponse windowResponseMessage = (WindowResponse) JsonMessage.parseJsonData(json);
        assertArrayEquals(windowResponseMessage.getIds().toArray(new Integer[0]), windowResponse.getIds().toArray(new Integer[0]));
        assertEquals(windowResponseMessage.getPlayerId(), windowResponse.getPlayerId());
        return null;
    }

    @Override
    public String visit(BeginTurnEvent beginTurnEvent) {
        String json = messageParser.createJsonResponse(beginTurnEvent);
        BeginTurnEvent beginTurnEventMessage = (BeginTurnEvent) JsonMessage.parseJsonData(json);
        assertEquals(beginTurnEventMessage.getIdPlayer(), beginTurnEvent.getIdPlayer());
        return null;
    }

    @Override
    public String visit(OpponentWindowResponse opponentWindowResponse) {
        String json = messageParser.createJsonResponse(opponentWindowResponse);
        OpponentWindowResponse opponentWindowResponseMessage = (OpponentWindowResponse) JsonMessage.parseJsonData(json);
        assertArrayEquals(opponentWindowResponse.getPlayers().toArray(new String[0]), opponentWindowResponseMessage.getPlayers().toArray(new String[0]));
        assertEquals(opponentWindowResponseMessage.getPlayerWindowId(idPlayer), opponentWindowResponse.getPlayerWindowId(idPlayer));
        assertEquals(opponentWindowResponseMessage.getPlayerWindowSide(idPlayer), opponentWindowResponse.getPlayerWindowSide(idPlayer));
        return null;
    }

    @Override
    public String visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        String json = messageParser.createJsonResponse(opponentDiceMoveResponse);
        OpponentDiceMoveResponse opponentDiceMoveResponseMessage = (OpponentDiceMoveResponse) JsonMessage.parseJsonData(json);
        assertEquals(opponentDiceMoveResponseMessage.getDice(), opponentDiceMoveResponse.getDice());
        assertEquals(opponentDiceMoveResponseMessage.getIdPlayer(), opponentDiceMoveResponse.getIdPlayer());
        assertEquals(opponentDiceMoveResponseMessage.getPosition(), opponentDiceMoveResponse.getPosition());
        return null;
    }

    @Override
    public String visit(NewTurnResponse newTurnResponse) {
        String json = messageParser.createJsonResponse(newTurnResponse);
        NewTurnResponse newTurnResponseMessage = (NewTurnResponse) JsonMessage.parseJsonData(json);
        assertEquals(newTurnResponseMessage.getRound(), newTurnResponse.getRound());
        return null;
    }

    @Override
    public String visit(RuleResponse ruleResponse) {
        String json = messageParser.createJsonResponse(ruleResponse);
        RuleResponse ruleResponseMessage = (RuleResponse) JsonMessage.parseJsonData(json);
        assertEquals(ruleResponseMessage.getPlayerId(), ruleResponse.getPlayerId());
        assertEquals(ruleResponseMessage.isMoveValid(), ruleResponse.isMoveValid());
        return null;
    }

    @Override
    public String visit(PublicObjectiveResponse publicObjectiveResponse) {
        String json = messageParser.createJsonResponse(publicObjectiveResponse);
        PublicObjectiveResponse publicObjectiveResponseMessage = (PublicObjectiveResponse) JsonMessage.parseJsonData(json);
        assertArrayEquals(publicObjectiveResponse.getIdObjective().toArray(new Integer[0]), publicObjectiveResponseMessage.getIdObjective().toArray(new Integer[0]));
        return null;
    }

    @Override
    public String visit(PrivateObjectiveResponse privateObjectiveResponse) {
        String json = messageParser.createJsonResponse(privateObjectiveResponse);
        PrivateObjectiveResponse privateObjectiveResponseMessage = (PrivateObjectiveResponse) JsonMessage.parseJsonData(json);
        assertEquals(privateObjectiveResponseMessage.getIdObjective(), privateObjectiveResponse.getIdObjective());
        assertEquals(privateObjectiveResponseMessage.getIdPlayer(), privateObjectiveResponse.getIdPlayer());
        return null;
    }

    @Override
    public String visit(ToolCardResponse toolCardResponse) {
        String json = messageParser.createJsonResponse(toolCardResponse);
        ToolCardResponse toolCardResponseMessage = (ToolCardResponse) JsonMessage.parseJsonData(json);
        assertArrayEquals(toolCardResponse.getIds().toArray(new Integer[0]), toolCardResponseMessage.getIds().toArray(new Integer[0]));
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
        String json = messageParser.createJsonResponse(scoreResponse);
        ScoreResponse scoreResponseMessage = (ScoreResponse) JsonMessage.parseJsonData(json);
        scoreResponse.getUsernames().forEach(username -> assertEquals(scoreResponseMessage.getScore(username), scoreResponse.getScore(username)));
        return null;
    }

    @Override
    public String visit(ToolResponse toolResponse) {
        String json = messageParser.createJsonResponse(toolResponse);
        ToolResponse toolResponseMessage = (ToolResponse) JsonMessage.parseJsonData(json);
        assertEquals(toolResponse.getIdPlayer(), toolResponseMessage.getIdPlayer());
        assertEquals(toolResponse.getTokenSpent(), toolResponseMessage.getTokenSpent());
        assertEquals(toolResponse.getToolId(), toolResponseMessage.getToolId());
        assertEquals(toolResponse.isCanBuy(), toolResponseMessage.isCanBuy());
        return null;
    }

    @Override
    public String visit(EndTurnResponse endTurnResponse) {
        String json = messageParser.createJsonResponse(endTurnResponse);
        EndTurnResponse endTurnResponseMessage = (EndTurnResponse) JsonMessage.parseJsonData(json);
        assertEquals(endTurnResponse.getUsername(), endTurnResponseMessage.getUsername());
        return null;
    }

    @Override
    public String visit(TimeRemainingResponse timeRemainingResponse) {
        String json = messageParser.createJsonResponse(timeRemainingResponse);
        TimeRemainingResponse timeRemainingResponseMessage = (TimeRemainingResponse) JsonMessage.parseJsonData(json);
        assertEquals(timeRemainingResponse.getRemainingTime(), timeRemainingResponseMessage.getRemainingTime());
        assertEquals(timeRemainingResponse.getUsername(), timeRemainingResponseMessage.getUsername());
        return null;
    }

    @Override
    public String visit(EnableWindowToolResponse enableWindowToolResponse) {
        String json = messageParser.createJsonResponse(enableWindowToolResponse);
        EnableWindowToolResponse enableWindowToolResponseMessage = (EnableWindowToolResponse) JsonMessage.parseJsonData(json);
        assertEquals(enableWindowToolResponse.getPlayerId(), enableWindowToolResponseMessage.getPlayerId());
        assertEquals(enableWindowToolResponse.getToolId(), enableWindowToolResponseMessage.getToolId());
        return null;
    }

    @Override
    public String visit(RoundTrackToolResponse roundTrackToolResponse) {
        String json = messageParser.createJsonResponse(roundTrackToolResponse);
        RoundTrackToolResponse roundTrackToolResponseMessage = (RoundTrackToolResponse) JsonMessage.parseJsonData(json);
        IntStream.range(0, roundTrackToolResponse.getDiceResponse().getDiceList().size()).forEach(index -> roundTrackToolResponse.getDiceResponse().getDiceList()
                                                                                                            .get(index).equals(roundTrackToolResponseMessage
                                                                                                            .getDiceResponse().getDiceList().get(index)));
        assertEquals(roundTrackToolResponse.getRoundNumber(), roundTrackToolResponseMessage.getRoundNumber());
        return null;
    }

    @Override
    public String visit(ColorBagToolResponse colorBagToolResponse) {
        String json = messageParser.createJsonResponse(colorBagToolResponse);
        ColorBagToolResponse colorBagToolResponseMessage = (ColorBagToolResponse) JsonMessage.parseJsonData(json);
        assertEquals(colorBagToolResponse.getColor(), colorBagToolResponseMessage.getColor());
        assertEquals(colorBagToolResponse.getDiceId(), colorBagToolResponseMessage.getDiceId());
        assertEquals(colorBagToolResponse.getPlayerId(), colorBagToolResponseMessage.getPlayerId());
        return null;
    }

    @Override
    public String visit(DiceRoundTrackReconnectionEvent diceRoundTrackReconnectionEvent) {
        String json = messageParser.createJsonResponse(diceRoundTrackReconnectionEvent);
        DiceRoundTrackReconnectionEvent diceRoundTrackReconnectionEventMessage = (DiceRoundTrackReconnectionEvent) JsonMessage.parseJsonData(json);
        diceRoundTrackReconnectionEvent.getRoundTrack().get(0).
                forEach(dice -> assertEquals(dice, diceRoundTrackReconnectionEventMessage.getRoundTrack().get(0).get(diceRoundTrackReconnectionEvent.getRoundTrack().get(0).indexOf(dice))));
        assertEquals(diceRoundTrackReconnectionEventMessage.getPlayerId(), diceRoundTrackReconnectionEvent.getPlayerId());
        return null;
    }
}
