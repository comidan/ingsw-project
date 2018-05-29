package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommandParser {


    public synchronized Message parse(String message) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(message);
            JSONObject data;
            switch ((String)jsonMsg.get("type_cmd")) {
                case "login":
                    data = (JSONObject) jsonMsg.get("login");
                    return new LoginEvent((String)data.get("username"), (String)data.get("auth"));
                case "disconnect":
                    data = (JSONObject) jsonMsg.get("disconnect");
                    return new DisconnectEvent((String)data.get("username"));
                case "message":
                    data = (JSONObject) jsonMsg.get("message");
                    return new MessageEvent((String)data.get("message"));
                case "choice" :
                    return parseChoice(jsonMsg);
                case "settings":  //is settings response useless?
                    return null;
                default:
                    return null;
            }

        }
        catch (ParseException exc) {
            return null;
        }
    }

    private Message parseChoice(JSONObject message) {
        switch((String)message.get("type_cmd")) {
            case "choice_window":
                JSONObject messageW = (JSONObject)message.get("window");
                String idPlayerW = (String)messageW.get("id_player");
                int idWindow = ((Long)messageW.get("window_id")).intValue();
                WindowSide side = WindowSide.stringtoWindowSide((String)messageW.get("window_side"));
                return new WindowEvent(idPlayerW, idWindow, side);
            case "choice_move_dice":
                JSONObject data = (JSONObject) message.get("move_dice");
                String idPlayerD = (String)data.get("player_id");
                int idDice = ((Long)data.get("dice_id")).intValue();
                String source = (String)data.get("source");
                JSONObject pos = (JSONObject)data.get("position");
                int row = ((Long)pos.get("y")).intValue();
                int col = ((Long)pos.get("x")).intValue();
                Position position = new Position(row, col);
                return  new DiceEvent(idPlayerD, idDice, position, source);
            case "end_turn":
                return new EndTurnEvent((String)message.get("id_player"));
            default: return null;
        }
    }

    public String createJSONCountdown(String time) {
        JSONObject content = new JSONObject();
        content.put("time", time);
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "lobby_time");
        container.put("time", content);
        return container.toJSONString();
    }

    public String createJSONAddLobbyPlayer(String time) {
        JSONObject content = new JSONObject();
        content.put("username", time);
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "lobby_add_player");
        container.put("player", content);
        return container.toJSONString();
    }

    public String createJSONRemoveLobbyPlayer(String time) {
        JSONObject content = new JSONObject();
        content.put("username", time);
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "lobby_remove_player");
        container.put("player", content);
        return container.toJSONString();
    }

    public String crateJSONMessage(String message) {
        JSONObject content = new JSONObject();
        content.put("metadata", message);
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "message");
        container.put("message", content);
        return container.toJSONString();
    }

    public String createJSONLoginResponse(String token, int lobbyPort) {
        JSONObject content = new JSONObject();
        content.put("token", token);
        content.put("lobby_port", lobbyPort+"");
        content.put("login", "successful");
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "login");
        container.put("login", content);
        return container.toJSONString();
    }

    public String crateJSONLoginLobbyResponse(int heartbeatPort) {
        JSONObject content = new JSONObject();
        content.put("heartbeat_port", heartbeatPort+"");
        content.put("login", "successful_lobby");
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "login_heartbeat");
        container.put("heartbeat", content);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseRegister() {
        JSONObject content = new JSONObject();
        content.put("login", "register");
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "login_register");
        container.put("login", content);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseError() {
        JSONObject content = new JSONObject();
        content.put("error", "error");
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "error");
        container.put("error", content);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseError(String error) {
        JSONObject content = new JSONObject();
        content.put("error", error);
        JSONObject container = new JSONObject();
        container.put("type_msg", "response");
        container.put("type_cmd", "error");
        container.put("error", content);
        return container.toJSONString();
    }
}