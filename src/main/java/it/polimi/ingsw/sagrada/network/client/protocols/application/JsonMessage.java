package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    public static JSONObject createWindowResponse(String username, int windowId) {
        JSONObject content = new JSONObject();
        content.put("id_player", username);
        content.put("window_id", windowId+"");
        content.put("window_side", "front"); //constant for testing purpose
        JSONObject container = new JSONObject();
        container.put("type_msg", "action");
        container.put("type_cmd", "choice_window");
        container.put("window", content);
        return container;
    }

    public static Message parseJsonData(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(json);
            JSONObject data;
            System.out.println("Type : " + (String)jsonMsg.get("type_cmd"));
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
                case "begin_turn" :
                    data = (JSONObject) jsonMsg.get("begin_turn");
                    return new BeginTurnEvent((String)data.get("id_player"));
                case "window_list" :
                    data = (JSONObject) jsonMsg.get("window_list");
                    WindowResponse windowResponse =  new WindowResponse((String)data.get("id_player"),
                                              Arrays.asList(Integer.parseInt((String)data.get("window_id_1")),
                                                            Integer.parseInt((String)data.get("window_id_2"))));
                    return windowResponse;
                case "dice_list" :
                    data = (JSONObject) jsonMsg.get("dice_list");
                    List<Dice> diceResponse = new ArrayList<>();
                    JSONArray diceArray = (JSONArray)((JSONObject)data.get("dice_list")).get("dice");
                    for(int i = 0; i < diceArray.size(); i++) {
                        Dice dice = new Dice(Integer.parseInt((String)((JSONObject)diceArray.get(i)).get("id")),
                                            (Color)((JSONObject)diceArray.get(i)).get("color"));
                        dice.setValue(Integer.parseInt((String)((JSONObject)diceArray.get(i)).get("value")));
                        diceResponse.add(dice);
                    }
                    return new DiceResponse((String)((JSONObject)data.get("dice_list")).get("destination"),
                                            diceResponse);
                default:
                    return null;
            }

        }
        catch (ParseException exc) {
            return null;
        }
    }
}