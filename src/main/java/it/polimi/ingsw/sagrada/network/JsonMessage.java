package it.polimi.ingsw.sagrada.network;

import org.json.simple.JSONObject;

public class JsonMessage {


    public JSONObject createMessage(String username) {

        JSONObject content = new JSONObject();
        content.put("username", username);
        JSONObject actionType = new JSONObject();
        actionType.put("login", content);
        JSONObject message = new JSONObject();
        message.put("action", actionType);

        return message;

    }

}

