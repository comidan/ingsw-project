package it.polimi.ingsw.sagrada.network.server.protocols.application;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private KeyParser keyParser;

    public CommandParser() {
        keyParser = new KeyParser();
    }


    public synchronized Map<String, String> parse(String message) {

        String stringAction = keyParser.getKey(message);
        try {
            switch (stringAction) {
                case "login":
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
                    JSONObject jsonLoginData = (JSONObject)((JSONObject)jsonObject.get("action")).get("login");
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("type", "login");
                    dataMap.put("username", (String)jsonLoginData.get("username"));
                    dataMap.put("auth", (String)jsonLoginData.get("auth"));
                    return dataMap;
                case "choice":
                    return null;
                case "settings":  //is settings response useless?
                    return null;

                default:
                    return null;
            }

        }catch (ParseException exc) {
            return null;
        }
    }

    public String crateJSONMessage(String message) {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("metadata", message);
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }

    public String crateJSONLoginResponse(String token, int lobbyPort) {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("token", token);
        jsonMessage.put("lobby_port", lobbyPort);
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }
}
