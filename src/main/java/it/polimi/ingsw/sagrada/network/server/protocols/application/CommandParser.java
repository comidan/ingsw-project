package it.polimi.ingsw.sagrada.network.server.protocols.application;


import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.LoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.MessageEvent;
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
        jsonMessage.put("login", "error");
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }

    public String crateJSONLoginResponse(String token, int lobbyPort) {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("token", token);
        jsonMessage.put("lobby_port", lobbyPort+"");
        jsonMessage.put("login", "successful");
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }

    public String crateJSONLoginLobbyResponse(int lobbyPort) {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("heartbeat_port", lobbyPort+"");
        jsonMessage.put("login", "successful_lobby");
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseRegister() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("login", "register");
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }

    public String crateJSONLoginResponseError() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("login", "error");
        JSONObject container = new JSONObject();
        container.put("response", jsonMessage);
        return container.toJSONString();
    }
}
