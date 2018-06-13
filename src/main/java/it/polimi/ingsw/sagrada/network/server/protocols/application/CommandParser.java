package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.LoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.MessageEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import static it.polimi.ingsw.sagrada.network.CommandKeyword.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommandParser {

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
                case SETTINGS :  //is settings response useless?
                    return null;
                default:
                    return null;
            }

        }
        catch (ParseException exc) {
            return null;
        }
    }

    public String createJSONCountdown(String time) {
        JSONObject content = new JSONObject();
        content.put(TIME, time);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, LOBBY_TIME);
        container.put(TIME, content);
        return container.toJSONString();
    }

    public String createJSONAddLobbyPlayer(String username) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, ADD_PLAYER);
        container.put(PLAYER, content);
        return container.toJSONString();
    }

    public String createJSONRemoveLobbyPlayer(String username) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, REMOVE_PLAYER);
        container.put(PLAYER, content);
        return container.toJSONString();
    }

    public String crateJSONMessage(String message) {
        JSONObject content = new JSONObject();
        content.put(METADATA, message);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, MESSAGE);
        container.put(MESSAGE, content);
        return container.toJSONString();
    }

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

    public String crateJSONLoginResponseRegister() {
        JSONObject content = new JSONObject();
        content.put(LOGIN, REGISTER);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, "login_register");
        container.put(LOGIN, content);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseError() {
        JSONObject content = new JSONObject();
        content.put(ERROR, ERROR);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, ERROR);
        container.put(ERROR, content);
        return container.toJSONString();
    }

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