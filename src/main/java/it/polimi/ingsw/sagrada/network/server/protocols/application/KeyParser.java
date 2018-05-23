package it.polimi.ingsw.sagrada.network.server.protocols.application;

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
            String mainKey = jsonMessage.keySet().toArray()[0].toString();
            return accessNextLevel(message, mainKey);
        } catch (ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "error");
            return null;
        }
    }

    private String accessNextLevel(String message, String mainKey) {
        try {
            JSONObject firstLevel = (JSONObject) jsonParser.parse(message);
            return ((JSONObject)firstLevel.get(mainKey)).keySet().toArray()[0].toString();

        } catch (ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "error");
            return null;
        }
    }


}
