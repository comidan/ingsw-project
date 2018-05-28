package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage {


    public static JSONObject createLoginMessage(String username, String authentication) {

        JSONObject content = new JSONObject();
        content.put("username", username);
        content.put("auth", authentication);
        JSONObject container = new JSONObject();
        container.put("type_msg", "action");
        container.put("type_cmd", "login");
        container.put("login", content);
        return container;
    }

    public static JSONObject creatDisconnectMessage(String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);
        JSONObject container = new JSONObject();
        container.put("type_msg", "action");
        container.put("type_cmd", "disconnect");
        container.put("disconnect", content);
        return container;
    }

    public static JSONObject createTokenMessage(String token) {
        JSONObject jsonToken = new JSONObject();
        jsonToken.put("token", token);
        return jsonToken;
    }

    public static JSONObject createMessage(String message) {
        JSONObject content = new JSONObject();
        content.put("message", message);
        JSONObject container = new JSONObject();
        container.put("type_msg", "action");
        container.put("type_cmd", "message");
        container.put("message", content);
        return container;
    }

    public static Message parseJsonData(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(json);
            JSONObject data;
            switch ((String)jsonMsg.get("type_cmd")) {
                case "lobby_time":
                    data = (JSONObject) jsonMsg.get("time");
                    return new MatchTimeEvent((String)data.get("time"));
                case "lobby_add_player":
                    data = (JSONObject) jsonMsg.get("player");
                    return new AddPlayerEvent((String)data.get("username"));
                case "lobby_remove_player":
                    data = (JSONObject) jsonMsg.get("player");
                    return new RemovePlayerEvent((String)data.get("username"));
                case "message":
                    data = (JSONObject) jsonMsg.get("message");
                    return new MessageEvent((String)data.get("metadata"));
                case "error":
                    data = (JSONObject) jsonMsg.get("error");
                    return new ErrorEvent((String)data.get("error"));
                case "login_register":
                    return new RegisterEvent();
                case "login_heartbeat" :
                    data = (JSONObject) jsonMsg.get("heartbeat");
                    return new HeartbeatInitEvent(
                               Integer.parseInt((String)data.get("heartbeat_port")));
                case "login" :
                    data = (JSONObject) jsonMsg.get("login");
                    return new LobbyLoginEvent((String)data.get("token"),
                               Integer.parseInt((String)data.get("lobby_port")));
                default:
                    return null;
            }

        }
        catch (ParseException exc) {
            return null;
        }
    }
}