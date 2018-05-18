package it.polimi.ingsw.sagrada.network.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.logging.Level;
import java.util.logging.Logger;


public class KeyParser {
    private JSONParser jsonParser;
    private static final Logger LOGGER = Logger.getLogger(KeyParser.class.getName());

    public KeyParser() {
        jsonParser = new JSONParser();
    }

    public String getKey(String message) {
        try {
            JSONObject jsonMessage = (JSONObject) jsonParser.parse(message);
            return jsonMessage.keySet().toArray()[0].toString();

        } catch (ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "error");
            return null;
        }
    }

    public String accessNextLevel(String message) {
        try {
            JSONObject firstLevel = (JSONObject) jsonParser.parse(message);
            String key = firstLevel.keySet().toArray()[0].toString();
            return firstLevel.get(key).toString();

        } catch (ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "error");
            return null;
        }
    }


}
