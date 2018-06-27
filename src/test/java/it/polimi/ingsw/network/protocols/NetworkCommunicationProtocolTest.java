package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.ScoreResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NetworkCommunicationProtocolTest implements ResponseMessageVisitor {

    private static String idPlayer = "test";
    private static int id = 0;
    private static int round = 1;
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
                                                            new ToolCardResponse(ids)};
        List<ResponseVisitor> messageList = Arrays.asList(messages);
        messageList.forEach(message -> message.accept(this));
        CommandParser commandParser = new CommandParser();
        assertTrue(JsonMessage.parseJsonData(commandParser.crateJSONLoginLobbyResponse(port)) instanceof HeartbeatInitEvent);
        assertTrue(JsonMessage.parseJsonData(commandParser.createJSONAddLobbyPlayer(idPlayer)) instanceof AddPlayerEvent);
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
        return null;
    }
}
