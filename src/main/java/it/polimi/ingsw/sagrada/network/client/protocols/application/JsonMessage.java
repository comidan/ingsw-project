package it.polimi.ingsw.sagrada.network.client.protocols.application;

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

    public static Map<String, String> parseJsonData(String json) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            JSONObject jsonLoginData = (JSONObject) jsonObject.get("response");
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("type", "login");
            dataMap.put("login", (String) jsonLoginData.get("login"));
            if(dataMap.get("login").equals("successful")) {
                dataMap.put("token", (String) jsonLoginData.get("token"));
                dataMap.put("lobby_port", (String) jsonLoginData.get("lobby_port"));
            }
            else if(dataMap.get("login").equals("successful_lobby"))
                dataMap.put("heartbeat_port", (String) jsonLoginData.get("heartbeat_port"));
            else if(dataMap.get("login").equals("error"))
                dataMap.put("metadata", (String) jsonLoginData.get("metadata"));
            return dataMap;
        }
        catch (ParseException exc) {
            return null;
        }
    }
}