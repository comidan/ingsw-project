package it.polimi.ingsw.sagrada.network.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CommandParser {
    private JSONParser jsonParser;

    public CommandParser() {
        jsonParser = new JSONParser();
    }


    public ActionEnum parse(String message) {

        try {
            JSONObject jsonMessage = (JSONObject) jsonParser.parse(message);
            String action = jsonMessage.get("action").toString();
            JSONObject jsonAction = (JSONObject) jsonParser.parse(action);
            String stringAction = jsonAction.keySet().toArray()[0].toString();

            switch (stringAction) {
                case "login":
                    return ActionEnum.LOGIN;
                case "choice":
                    return ActionEnum.CHOICE;
                case "settings":
                    return ActionEnum.SETTINGS;

                default:
                    return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }
}
