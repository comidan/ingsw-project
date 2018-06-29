package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RegisterEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.ErrorEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.MessageEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static it.polimi.ingsw.sagrada.network.CommandKeyword.*;

import java.util.*;


/**
 * The Class JsonMessage.
 */
public class JsonMessage implements ActionMessageVisitor {

    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new json message.
     *
     * @param playerId the player id
     */
    public JsonMessage(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets the message.
     *
     * @param actionVisitor the action visitor
     * @return the message
     */
    public String getMessage(ActionVisitor actionVisitor) {
        return actionVisitor.accept(this);
    }

    /**
     * Creates the login message.
     *
     * @param username the username
     * @param authentication the authentication
     * @return the JSON object
     */
    public static JSONObject createLoginMessage(String username, String authentication) {

        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        content.put(AUTH, authentication);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, LOGIN);
        container.put(LOGIN, content);
        return container;
    }

    /**
     * Creates the disconnect message.
     *
     * @param username the username
     * @return the JSON object
     */
    public static JSONObject createDisconnectMessage(String username) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, DISCONNECT);
        container.put(DISCONNECT, content);
        return container;
    }

    /**
     * Creates the token message.
     *
     * @param token the token
     * @return the JSON object
     */
    public static JSONObject createTokenMessage(String token) {
        JSONObject jsonToken = new JSONObject();
        jsonToken.put(TOKEN, token);
        return jsonToken;
    }

    /**
     * Creates the message.
     *
     * @param message the message
     * @return the JSON object
     */
    public static JSONObject createMessage(String message) {
        JSONObject content = new JSONObject();
        content.put(MESSAGE, message);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, MESSAGE);
        container.put(MESSAGE, content);
        return container;
    }

    /**
     * Creates the window event.
     *
     * @param username the username
     * @param windowId the window id
     * @param side the side
     * @return the JSON object
     */
    private JSONObject createWindowEvent(String username, int windowId, String side) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, username);
        content.put(WINDOW_ID, windowId+"");
        content.put("window_side", side);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, WINDOW_CHOICE);
        container.put(WINDOW, content);
        return container;
    }

    /**
     * Creates the dice event.
     *
     * @param diceEvent the dice event
     * @return the JSON object
     */
    private JSONObject createDiceEvent(DiceEvent diceEvent) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, diceEvent.getIdPlayer());
        content.put(DICE_ID, diceEvent.getIdDice()+"");
        content.put("source", diceEvent.getSrc());
        JSONObject position = new JSONObject();
        position.put("y", diceEvent.getPosition().getRow()+"");
        position.put("x", diceEvent.getPosition().getCol()+"");
        content.put(POSITION, position);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, MOVE_DICE_CHOICE);
        container.put(MOVE_DICE, content);
        return container;
    }

    /**
     * Creates the end turn event.
     *
     * @param endTurnEvent the end turn event
     * @return the JSON object
     */
    private JSONObject createEndTurnEvent(EndTurnEvent endTurnEvent) {
        JSONObject content = new JSONObject();
        content.put(MESSAGE_TYPE, ACTION);
        content.put(COMMAND_TYPE, END_TURN);
        content.put(PLAYER_ID, endTurnEvent.getIdPlayer());
        return content;
    }

    private JSONObject createToolChoiceEvent(ToolEvent toolEvent) {
        JSONObject data = new JSONObject();
        data.put(TOOL_ID, toolEvent.getToolId()+"");
        data.put(PLAYER_ID, toolEvent.getPlayerId());
        JSONObject content = new JSONObject();
        content.put(MESSAGE_TYPE, ACTION);
        content.put(COMMAND_TYPE, TOOL_CHOICE);
        content.put(TOOL, data);
        return content;
    }

    private JSONObject createByteStreamWindowResponse(ByteStreamWindowEvent byteStreamWindowEvent) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, playerId);
        byte[] image = byteStreamWindowEvent.getImage();
        content.put(BINARY, new String(image));
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, BINARY);
        container.put(BINARY, content);
        return container;
    }

    /**
     * Parses the json data.
     *
     * @param json the json
     * @return the message
     */
    public static Message parseJsonData(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(json);
            JSONObject data;
            System.out.println("Type : " + jsonMsg.get(COMMAND_TYPE));
            switch ((String)jsonMsg.get(COMMAND_TYPE)) {
                case LOBBY_TIME:
                    data = (JSONObject) jsonMsg.get(TIME);
                    return new MatchTimeEvent((String)data.get(TIME));
                case ADD_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new AddPlayerEvent((String)data.get(USERNAME));
                case REMOVE_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new RemovePlayerEvent((String)data.get(USERNAME));
                case MESSAGE:
                    data = (JSONObject) jsonMsg.get(MESSAGE);
                    return new MessageEvent((String)data.get(METADATA));
                case ERROR:
                    data = (JSONObject) jsonMsg.get(ERROR);
                    return new ErrorEvent((String)data.get(ERROR));
                case "login_register":
                    return new RegisterEvent();
                case "login_heartbeat" :
                    data = (JSONObject) jsonMsg.get(HEARTBEAT);
                    return new HeartbeatInitEvent(
                               Integer.parseInt((String)data.get(HEARTBEAT_PORT)));
                case LOGIN :
                    data = (JSONObject) jsonMsg.get(LOGIN);
                    return new LobbyLoginEvent((String)data.get(TOKEN),
                               Integer.parseInt((String)data.get(LOBBY_PORT)));
                case BEGIN_TURN :
                    data = (JSONObject) jsonMsg.get(BEGIN_TURN);
                    return new BeginTurnEvent((String)data.get(PLAYER_ID));
                case WINDOW_LIST :

                    data = (JSONObject) jsonMsg.get(WINDOW_LIST);
                    WindowResponse windowResponse =  new WindowResponse((String)data.get(PLAYER_ID),
                                              Arrays.asList(Integer.parseInt((String)data.get(WINDOW_OPTION_ONE)),
                                                            Integer.parseInt((String)data.get(WINDOW_OPTION_TWO))));

                    return windowResponse;
                case DICE_LIST :
                    data = (JSONObject) jsonMsg.get(DICE_LIST);
                    List<Dice> diceResponse = new ArrayList<>();
                    JSONArray diceArray = (JSONArray)data.get(DICE);
                    diceArray.forEach(raw -> {
                        JSONObject diceJson = (JSONObject) raw;
                        Dice dice = new Dice(Integer.parseInt((String)(diceJson.get(ID))),
                                Colors.stringToColor((String)(diceJson.get(COLOR))));
                        dice.setValue(Integer.parseInt((String)(diceJson).get(VALUE)));
                        diceResponse.add(dice);
                    });
                    return new DiceResponse((String)(data.get(DESTINATION)), diceResponse);
                case OPPONENT_WINDOW_LIST:
                    JSONArray array = (JSONArray) jsonMsg.get(OPPONENT_WINDOW_LIST);
                    List<Integer> windowIds = new ArrayList<>();
                    List<WindowSide> windowSides = new ArrayList<>();
                    List<String> players = new ArrayList<>();
                    array.forEach(raw -> {
                        JSONObject windowJson = (JSONObject) raw;
                        players.add((String) windowJson.get(PLAYER_ID));
                        windowIds.add(Integer.parseInt((String) windowJson.get(WINDOW_ID)));
                        windowSides.add(WindowSide.stringToWindowSide((String) windowJson.get(WINDOW_SIDE)));
                    });
                    OpponentWindowResponse opponentWindowResponse = new OpponentWindowResponse(players, windowIds, windowSides);
                    opponentWindowResponse.getPlayers().forEach(player -> System.out.println(opponentWindowResponse.getPlayerWindowId(player)));
                    return new OpponentWindowResponse(players, windowIds, windowSides);
                case OPPONENT_DICE_RESPONSE:
                    JSONObject dice = (JSONObject)jsonMsg.get(DICE);
                    String idPlayer = (String)dice.get(PLAYER_ID);
                    Dice diceOpponent = new Dice(Integer.parseInt((String)dice.get(DICE_ID)), Colors.stringToColor((String)dice.get(COLOR)));
                    diceOpponent.setValue(Integer.parseInt((String)dice.get(VALUE)));
                    JSONObject pos = (JSONObject)dice.get(POSITION);
                    Position position = new Position(Integer.parseInt((String)pos.get("y")), Integer.parseInt((String)pos.get("x")));
                    return new OpponentDiceMoveResponse(idPlayer, diceOpponent, position);
                case RULE_RESPONSE:
                    JSONObject ruleResponse = (JSONObject)jsonMsg.get(RULE_RESPONSE);
                    return new RuleResponse((String)ruleResponse.get(PLAYER_ID), Boolean.parseBoolean((String)ruleResponse.get(VALID_MOVE)));
                case NEW_ROUND:
                    JSONObject newRoundResponse = (JSONObject)jsonMsg.get(NEW_ROUND);
                    return new NewTurnResponse(Integer.parseInt((String) newRoundResponse.get(NEW_ROUND)));
                case PUBLIC_OBJECTIVES:
                    JSONArray publicObjectives = (JSONArray) jsonMsg.get(PUBLIC_OBJECTIVES);
                    List<Integer> ids = new ArrayList<>();
                    publicObjectives.forEach(raw -> {
                        JSONObject publicObjective = (JSONObject) raw;
                        ids.add(Integer.parseInt((String) publicObjective.get(ID)));
                    });
                    return new PublicObjectiveResponse(ids);
                case PRIVATE_OBJECTIVE:
                    data = (JSONObject) jsonMsg.get(PRIVATE_OBJECTIVE);
                    String playerId = (String) data.get(PLAYER_ID);
                    int privateObjectId = Integer.parseInt((String) data.get(ID));
                    return new PrivateObjectiveResponse(privateObjectId, playerId);
                case TOOL_CARDS:
                    JSONArray toolCards = (JSONArray) jsonMsg.get(TOOL_CARDS);
                    List<Integer> toolIds = new ArrayList<>();
                    toolCards.forEach(raw -> {
                        JSONObject publicObjective = (JSONObject) raw;
                        toolIds.add(Integer.parseInt((String) publicObjective.get(ID)));
                    });
                    return new ToolCardResponse(toolIds);
                case RANKING:
                    JSONArray ranks = (JSONArray) jsonMsg.get(RANKING);
                    List<Pair<String, Integer>> ranking = new ArrayList<>();
                    ranks.forEach(raw -> {
                        JSONObject rank = (JSONObject) raw;
                        ranking.add(new Pair<>((String) rank.get(USERNAME), Integer.parseInt((String) rank.get(SCORE))));
                    });
                    return new ScoreResponse(ranking);
                case TOOL_RESPONSE :
                    data = (JSONObject) jsonMsg.get(TOOL);
                    int cost = Integer.parseInt((String)data.get(COST));
                    System.out.println(cost);
                    boolean canBuy = Boolean.parseBoolean((String)data.get(CAN_BUY));
                    String player = (String)data.get(PLAYER_ID);
                    return new ToolResponse(canBuy, player, cost);
                case END_TURN :
                    data = (JSONObject) jsonMsg.get(END_TURN);
                    return new EndTurnResponse((String) data.get(USERNAME));
                case TIME :
                    data = (JSONObject) jsonMsg.get(TIME);
                    return new TimeRemainingResponse((String) data.get(USERNAME), Integer.parseInt((String) data.get(TIME)));
                default:
                    return null;
            }
        }
        catch (ParseException exc) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent)
     */
    @Override
    public String visit(DiceEvent diceEvent) {
        return createDiceEvent(diceEvent).toJSONString();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent)
     */
    @Override
    public String visit(WindowEvent windowEvent) {
        return createWindowEvent(playerId, windowEvent.getIdWindow(), WindowSide.sideToString(windowEvent.getWindowSide())).toJSONString();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent)
     */
    @Override
    public String visit(EndTurnEvent endTurnEvent) {
        return createEndTurnEvent(endTurnEvent).toJSONString();
    }

    /**
     * Visit.
     *
     * @param byteStreamWindowEvent the window image
     * @return the string
     */
    @Override
    public String visit(ByteStreamWindowEvent byteStreamWindowEvent) {
        return createByteStreamWindowResponse(byteStreamWindowEvent).toJSONString();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public String visit(Message message) {
        return CommandKeyword.ERROR;
    }

    @Override
    public String visit(ToolEvent toolEvent) { return createToolChoiceEvent(toolEvent).toJSONString();}
}