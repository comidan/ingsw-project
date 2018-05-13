package it.polimi.ingsw.sagrada.network;

import org.json.simple.JSONObject;

public class JsonMessage {


    public JSONObject createLoginMessage(String username, String authentication) {

        JSONObject content = new JSONObject();
        content.put("username", username);
        content.put("authentication", authentication);
        JSONObject actionType = new JSONObject();
        actionType.put("login", content);
        JSONObject message = new JSONObject();
        message.put("action", actionType);

        return message;

    }

}

