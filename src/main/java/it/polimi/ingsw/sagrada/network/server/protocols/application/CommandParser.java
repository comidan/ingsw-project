package it.polimi.ingsw.sagrada.network.server.protocols.application;


import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.LoginEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandParser {
    private KeyParser keyParser;

    private Logger logger = Logger.getLogger(CommandParser.class.getName());

    public CommandParser() {
        keyParser = new KeyParser();
    }


    public synchronized Message parse(String message) {

        JSONParser parser = new JSONParser();

        try {
        JSONObject jsonMsg = (JSONObject)parser.parse(message);

        switch((String)jsonMsg.get("type_cmd")) {
            case "login":
                JSONObject data = (JSONObject) jsonMsg.get("login");
                return new LoginEvent((String)data.get("username"), (String)data.get("auth"));
            case "choice": return null;
            case "settings": return null;
            default:
                logger.log(Level.SEVERE, "Message with incompatible type");
                return null;
        }



        /*String stringAction = keyParser.getKey(message);
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
            }*/

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
