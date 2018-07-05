package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackColorSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.LoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ColorConstraintToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.MessageEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.ByteStreamWindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import static it.polimi.ingsw.sagrada.network.CommandKeyword.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.stream.IntStream;


/**
 * The Class CommandParser.
 */
public class CommandParser {

    /**
     * Parses the.
     *
     * @param message the message
     * @return the message
     */
    public synchronized Message parse(String message) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(message);
            JSONObject data;
            switch ((String)jsonMsg.get(COMMAND_TYPE)) {
                case LOGIN:
                    data = (JSONObject) jsonMsg.get(LOGIN);
                    return new LoginEvent((String)data.get(USERNAME), (String)data.get(AUTH));
                case DISCONNECT:
                    data = (JSONObject) jsonMsg.get(DISCONNECT);
                    return new DisconnectEvent((String)data.get(USERNAME));
                case MESSAGE:
                    data = (JSONObject) jsonMsg.get(MESSAGE);
                    return new MessageEvent((String)data.get(MESSAGE));
                case WINDOW_CHOICE :
                    data = (JSONObject) jsonMsg.get(WINDOW);
                    System.out.println(message);
                    String idPlayerW = (String)data.get(PLAYER_ID);
                    int idWindow = Integer.parseInt((String)data.get(WINDOW_ID));
                    WindowSide side = WindowSide.stringToWindowSide((String)data.get(WINDOW_SIDE));
                    return new WindowEvent(idPlayerW, idWindow, side);
                case MOVE_DICE_CHOICE:
                    data = (JSONObject) jsonMsg.get(MOVE_DICE);
                    String idPlayerD = (String)data.get(PLAYER_ID);
                    int idDice = Integer.parseInt((String)data.get(DICE_ID));
                    String source = (String)data.get("source");
                    JSONObject pos = (JSONObject)data.get(POSITION);
                    int row = Integer.parseInt((String)pos.get("y"));
                    int col = Integer.parseInt((String)pos.get("x"));
                    Position position = new Position(row, col);
                    return new DiceEvent(idPlayerD, idDice, position, source);
                case END_TURN:
                    return new EndTurnEvent((String)jsonMsg.get(PLAYER_ID));
                case BINARY:
                    data = (JSONObject) jsonMsg.get(BINARY);
                    String username = (String) data.get(USERNAME);
                    byte[] image = ((String) data.get(BINARY)).getBytes();
                    return new ByteStreamWindowEvent(username, image);
                case SETTINGS :  //is settings response useless?
                    return null;
                case TOOL_CHOICE :
                    data = (JSONObject) jsonMsg.get(TOOL);
                    String playerId = (String)data.get(PLAYER_ID);
                    int toolId = Integer.parseInt((String)data.get(TOOL_ID));
                    return new ToolEvent(playerId, toolId);
                case DICE_DRAFT:
                    data = (JSONObject) jsonMsg.get(DICE);
                    String idPlayer = (String)data.get(PLAYER_ID);
                    int diceId = Integer.parseInt((String)data.get(DICE_ID));
                    return new DiceDraftSelectionEvent(idPlayer, diceId);
                case ROUND_TRACK_SELECTION:
                    data = (JSONObject) jsonMsg.get(ROUND_TRACK_SELECTION);
                    String idP = (String)data.get(PLAYER_ID);
                    int idD = Integer.parseInt((String)data.get(DICE_ID));
                    int roundNum = Integer.parseInt((String)data.get(ROUND_NUMBER));
                    return new DiceRoundTrackSelectionEvent(idP, idD, roundNum);
                case ROUND_COLOR_SELECTION:
                    data = (JSONObject) jsonMsg.get(ROUND_COLOR_SELECTION);
                    playerId = (String)data.get(PLAYER_ID);
                    Colors color = Colors.stringToColor((String)data.get(COLOR));
                    return new DiceRoundTrackColorSelectionEvent(playerId, color);
                default:
                    return null;
            }
        }
        catch (ParseException exc) {
            return null;
        }
    }

    /**
     * Creates the JSON countdown.
     *
     * @param time the time
     * @return the string
     */
    public String createJSONCountdown(String time) {
        JSONObject content = new JSONObject();
        content.put(TIME, time);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, LOBBY_TIME);
        container.put(TIME, content);
        return container.toJSONString();
    }

    /**
     * Creates the JSON add lobby player.
     *
     * @param username the username
     * @return the string
     */
    public String createJSONAddLobbyPlayer(String username, int position) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        content.put(POSITION, position + "");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, ADD_PLAYER);
        container.put(PLAYER, content);
        return container.toJSONString();
    }

    /**
     * Creates the JSON remove lobby player.
     *
     * @param username the username
     * @return the string
     */
    public String createJSONRemoveLobbyPlayer(String username) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, REMOVE_PLAYER);
        container.put(PLAYER, content);
        return container.toJSONString();
    }

    /**
     * Crate JSON message.
     *
     * @param message the message
     * @return the string
     */
    public String crateJSONMessage(String message) {
        JSONObject content = new JSONObject();
        content.put(METADATA, message);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, MESSAGE);
        container.put(MESSAGE, content);
        return container.toJSONString();
    }

    /**
     * Creates the JSON login response.
     *
     * @param token the token
     * @param lobbyPort the lobby port
     * @return the string
     */
    public String createJSONLoginResponse(String token, int lobbyPort) {
        JSONObject content = new JSONObject();
        content.put(TOKEN, token);
        content.put(LOBBY_PORT, lobbyPort+"");
        content.put(LOGIN, "successful");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, LOGIN);
        container.put(LOGIN, content);
        return container.toJSONString();
    }

    /**
     * Crate JSON login lobby response.
     *
     * @param heartbeatPort the heartbeat port
     * @return the string
     */
    public String crateJSONLoginLobbyResponse(int heartbeatPort) {
        JSONObject content = new JSONObject();
        content.put(HEARTBEAT_PORT, heartbeatPort+"");
        content.put(LOGIN, "successful_lobby");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, "login_heartbeat");
        container.put(HEARTBEAT, content);
        return container.toJSONString();
    }

    /**
     * Crate JSON login response register.
     *
     * @return the string
     */
    public String crateJSONLoginResponseRegister() {
        JSONObject content = new JSONObject();
        content.put(LOGIN, REGISTER);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, "login_register");
        container.put(LOGIN, content);
        return container.toJSONString();
    }

    /**
     * Crate JSON login response error.
     *
     * @return the string
     */
    public String crateJSONLoginResponseError() {
        JSONObject content = new JSONObject();
        content.put(ERROR, ERROR);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, ERROR);
        container.put(ERROR, content);
        return container.toJSONString();
    }

    /**
     * Crate JSON login response error.
     *
     * @param error the error
     * @return the string
     */
    public String crateJSONLoginResponseError(String error) {
        JSONObject content = new JSONObject();
        content.put(ERROR, error);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, ERROR);
        container.put(ERROR, content);
        return container.toJSONString();
    }
}