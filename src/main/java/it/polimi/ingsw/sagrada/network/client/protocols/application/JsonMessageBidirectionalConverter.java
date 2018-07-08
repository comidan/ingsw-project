package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RegisterEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class JsonMessageBidirectionalConverter.
 */
public class JsonMessageBidirectionalConverter implements ActionMessageVisitor {

    /** The player id. */
    private String playerId;

    /**
     * Instantiates a new json message.
     *
     * @param playerId the player id
     */
    public JsonMessageBidirectionalConverter(String playerId) {
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
        position.put(CommandKeyword.Y, diceEvent.getPosition().getRow()+"");
        position.put(CommandKeyword.X, diceEvent.getPosition().getCol()+"");
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

    /**
     * Creates the tool choice event.
     *
     * @param toolEvent the tool event
     * @return the JSON object
     */
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

    /**
     * Creates the dice draft selection event.
     *
     * @param diceDraftSelectionEvent the dice draft selection event
     * @return the JSON object
     */
    private JSONObject createDiceDraftSelectionEvent(DiceDraftSelectionEvent diceDraftSelectionEvent) {
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, diceDraftSelectionEvent.getIdPlayer());
        data.put(DICE_ID, diceDraftSelectionEvent.getIdDice()+"");
        JSONObject content = new JSONObject();
        content.put(MESSAGE_TYPE, ACTION);
        content.put(COMMAND_TYPE, DICE_DRAFT);
        content.put(DICE, data);
        return content;
    }

    /**
     * Creates the dice round track selection event.
     *
     * @param diceRoundTrackSelectionEvent the dice round track selection event
     * @return the JSON object
     */
    private JSONObject createDiceRoundTrackSelectionEvent(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent) {
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, diceRoundTrackSelectionEvent.getPlayerId());
        data.put(DICE_ID, diceRoundTrackSelectionEvent.getDiceId()+"");
        data.put(ROUND_NUMBER, diceRoundTrackSelectionEvent.getTurn()+"");
        JSONObject content = new JSONObject();
        content.put(MESSAGE_TYPE, ACTION);
        content.put(COMMAND_TYPE, ROUND_TRACK_SELECTION);
        content.put(ROUND_TRACK_SELECTION, data);
        return content;
    }

    /**
     * Creates the dice round track color selection event.
     *
     * @param diceRoundTrackColorSelectionEvent the dice round track color selection event
     * @return the JSON object
     */
    private JSONObject createDiceRoundTrackColorSelectionEvent(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent) {
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, diceRoundTrackColorSelectionEvent.getPlayerId());
        data.put(COLOR, diceRoundTrackColorSelectionEvent.getConstraint().toString());
        JSONObject content = new JSONObject();
        content.put(MESSAGE_TYPE, ACTION);
        content.put(COMMAND_TYPE, ROUND_COLOR_SELECTION);
        content.put(ROUND_COLOR_SELECTION, data);
        return content;
    }

    /**
     * Creates the dice value event.
     *
     * @param diceValueEvent the dice value event
     * @return the JSON object
     */
    private JSONObject createDiceValueEvent(DiceValueEvent diceValueEvent) {
        DiceEvent diceEvent = diceValueEvent.getDiceEvent();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, diceEvent.getIdPlayer());
        content.put(DICE_ID, diceEvent.getIdDice()+"");
        content.put("source", diceEvent.getSrc());
        content.put(VALUE, diceValueEvent.getValue()+"");
        JSONObject position = new JSONObject();
        position.put(CommandKeyword.Y, diceEvent.getPosition().getRow()+"");
        position.put(CommandKeyword.X, diceEvent.getPosition().getCol()+"");
        content.put(POSITION, position);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, MOVE_DICE_VALUE);
        container.put(MOVE_DICE_VALUE, content);
        return container;
    }

    /**
     * Creates the byte stream window response.
     *
     * @param byteStreamWindowEvent the byte stream window event
     * @return the JSON object
     */
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
            Logger.getLogger(JsonMessageBidirectionalConverter.class.getName()).log(Level.INFO, () ->"Type : " + jsonMsg.get(COMMAND_TYPE));
            switch ((String)jsonMsg.get(COMMAND_TYPE)) {
                case LOBBY_TIME:
                    data = (JSONObject) jsonMsg.get(TIME);
                    return new MatchTimeEvent((String) data.get(TIME));
                case ADD_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new AddPlayerEvent((String) data.get(USERNAME), Integer.parseInt((String) data.get(POSITION)));
                case REMOVE_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new RemovePlayerEvent((String) data.get(USERNAME));
                case MESSAGE:
                    data = (JSONObject) jsonMsg.get(MESSAGE);
                    return new MessageEvent((String) data.get(METADATA));
                case ERROR:
                    data = (JSONObject) jsonMsg.get(ERROR);
                    return new ErrorEvent((String) data.get(ERROR));
                case LOGIN_REGISTER:
                    return new RegisterEvent();
                case LOGIN_HEARTBEAT:
                    data = (JSONObject) jsonMsg.get(HEARTBEAT);
                    return new HeartbeatInitEvent(
                            Integer.parseInt((String) data.get(HEARTBEAT_PORT)));
                case LOGIN:
                    data = (JSONObject) jsonMsg.get(LOGIN);
                    return new LobbyLoginEvent((String) data.get(TOKEN),
                            Integer.parseInt((String) data.get(LOBBY_PORT)));
                case BEGIN_TURN:
                    data = (JSONObject) jsonMsg.get(BEGIN_TURN);
                    return new BeginTurnEvent((String) data.get(PLAYER_ID));
                case WINDOW_LIST:

                    data = (JSONObject) jsonMsg.get(WINDOW_LIST);
                    WindowResponse windowResponse = new WindowResponse((String) data.get(PLAYER_ID),
                            Arrays.asList(Integer.parseInt((String) data.get(WINDOW_OPTION_ONE)),
                                    Integer.parseInt((String) data.get(WINDOW_OPTION_TWO))));

                    return windowResponse;
                case DICE_LIST:
                    data = (JSONObject) jsonMsg.get(DICE_LIST);
                    List<Dice> diceResponse = new ArrayList<>();
                    JSONArray diceArray = (JSONArray) data.get(DICE);
                    diceArray.forEach(raw -> {
                        JSONObject diceJson = (JSONObject) raw;
                        Dice dice = new Dice(Integer.parseInt((String) (diceJson.get(ID))),
                                Colors.stringToColor((String) (diceJson.get(COLOR))));
                        dice.setValue(Integer.parseInt((String) (diceJson).get(VALUE)));
                        diceResponse.add(dice);
                    });
                    return new DiceResponse((String) (data.get(DESTINATION)), diceResponse);
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
                    opponentWindowResponse.getPlayers().forEach(player -> Logger.getLogger(JsonMessageBidirectionalConverter.class.getName()).log(Level.INFO, () ->opponentWindowResponse.getPlayerWindowId(player) + ""));
                    return new OpponentWindowResponse(players, windowIds, windowSides);
                case OPPONENT_DICE_RESPONSE:
                    JSONObject dice = (JSONObject) jsonMsg.get(DICE);
                    String idPlayer = (String) dice.get(PLAYER_ID);
                    Dice diceOpponent = new Dice(Integer.parseInt((String) dice.get(DICE_ID)), Colors.stringToColor((String) dice.get(COLOR)));
                    diceOpponent.setValue(Integer.parseInt((String) dice.get(VALUE)));
                    JSONObject pos = (JSONObject) dice.get(POSITION);
                    Position position = new Position(Integer.parseInt((String) pos.get(CommandKeyword.Y)), Integer.parseInt((String) pos.get(CommandKeyword.X)));
                    return new OpponentDiceMoveResponse(idPlayer, diceOpponent, position);
                case RULE_RESPONSE:
                    JSONObject ruleResponse = (JSONObject) jsonMsg.get(RULE_RESPONSE);
                    return new RuleResponse((String) ruleResponse.get(PLAYER_ID), Boolean.parseBoolean((String) ruleResponse.get(VALID_MOVE)));
                case NEW_ROUND:
                    JSONObject newRoundResponse = (JSONObject) jsonMsg.get(NEW_ROUND);
                    Logger.getLogger(JsonMessageBidirectionalConverter.class.getName()).log(Level.INFO, () ->"Round received and parsed from json : " + Integer.parseInt((String) newRoundResponse.get(NEW_ROUND)));
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
                case TOOL_RESPONSE:
                    data = (JSONObject) jsonMsg.get(TOOL);
                    int cost = Integer.parseInt((String) data.get(COST));
                    boolean canBuy = Boolean.parseBoolean((String) data.get(CAN_BUY));
                    int toolId = Integer.parseInt((String) data.get(TOOL_ID));
                    String player = (String) data.get(PLAYER_ID);
                    return new ToolResponse(canBuy, player, cost, toolId);
                case END_TURN:
                    data = (JSONObject) jsonMsg.get(END_TURN);
                    return new EndTurnResponse((String) data.get(USERNAME));
                case TIME:
                    data = (JSONObject) jsonMsg.get(TIME);
                    return new TimeRemainingResponse((String) data.get(USERNAME), Integer.parseInt((String) data.get(TIME)));
                case WINDOW_ENABLE:
                    data = (JSONObject) jsonMsg.get(WINDOW_ENABLE);
                    return new EnableWindowToolResponse((String) data.get(USERNAME), Integer.parseInt((String) data.get(TOOL_ID)));
                case ROUND_TRACK_RESPONSE:
                    data = (JSONObject) jsonMsg.get(DICE_LIST);
                    diceResponse = new ArrayList<>();
                    diceArray = (JSONArray) data.get(DICE);
                    diceArray.forEach(raw -> {
                        JSONObject diceJson = (JSONObject) raw;
                        Dice diceR = new Dice(Integer.parseInt((String) (diceJson.get(ID))),
                                Colors.stringToColor((String) (diceJson.get(COLOR))));
                        diceR.setValue(Integer.parseInt((String) (diceJson).get(VALUE)));
                        diceResponse.add(diceR);
                    });
                    return new RoundTrackToolResponse(new DiceResponse((String) (data.get(DESTINATION)), diceResponse), Integer.parseInt((String) data.get(ROUND_NUMBER)));
                case COLOR_SELECTION:
                    data = (JSONObject) jsonMsg.get(COLOR_SELECTION);
                    player = (String) data.get(USERNAME);
                    Colors color = Colors.stringToColor((String) data.get(COLOR));
                    int diceId = Integer.parseInt((String) data.get(DICE_ID));
                    return new ColorBagToolResponse(player, color, diceId);
                case ROUND_TRACK_RECONNECT:
                    List<List<Dice>> roundTrackRecovery = new ArrayList<>();
                    data = (JSONObject) jsonMsg.get(ROUND_TRACK_RECONNECT);
                    playerId = (String) data.get(PLAYER_ID);
                    JSONArray roundTrack = (JSONArray) data.get(ROUND_TRACK);
                    roundTrack.forEach(round -> {
                        List<Dice> list = new ArrayList<>();
                        ((JSONArray) round).forEach(diceRoundTrack -> {
                            JSONObject diceR = (JSONObject) diceRoundTrack;
                            Dice diceRound = new Dice(Integer.parseInt((String) (diceR.get(ID))),
                                    Colors.stringToColor((String) (diceR.get(COLOR))));
                            diceRound.setValue(Integer.parseInt((String) (diceR).get(VALUE)));
                            list.add(diceRound);
                        });
                        roundTrackRecovery.add(list);
                    });
                    return new DiceRoundTrackReconnectionEvent(roundTrackRecovery, playerId);
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent)
     */
    @Override
    public String visit(DiceDraftSelectionEvent diceDraftSelectionEvent) { return createDiceDraftSelectionEvent(diceDraftSelectionEvent).toJSONString(); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackSelectionEvent)
     */
    @Override
    public String visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent) { return createDiceRoundTrackSelectionEvent(diceRoundTrackSelectionEvent).toJSONString(); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackColorSelectionEvent)
     */
    @Override
    public String visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent) {
        return createDiceRoundTrackColorSelectionEvent(diceRoundTrackColorSelectionEvent).toJSONString();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceValueEvent)
     */
    @Override
    public String visit(DiceValueEvent diceValueEvent) {
        return createDiceValueEvent(diceValueEvent).toJSONString();
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent)
     */
    @Override
    public String visit(ToolEvent toolEvent) { return createToolChoiceEvent(toolEvent).toJSONString();}


}