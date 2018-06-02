package it.polimi.ingsw.sagrada.network.client.protocols.application;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JsonMessage implements CommandKeyword {

    public static JSONObject createLoginMessage(String username, String authentication) {

        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        content.put(AUTH, authentication);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, LOGIN);
        container.put(LOGIN, content);
        return container;
    }

    public static JSONObject createDisconnectMessage(String username) {
        JSONObject content = new JSONObject();
        content.put(USERNAME, username);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, DISCONNECT);
        container.put(DISCONNECT, content);
        return container;
    }

    public static JSONObject createTokenMessage(String token) {
        JSONObject jsonToken = new JSONObject();
        jsonToken.put(TOKEN, token);
        return jsonToken;
    }

    public static JSONObject createMessage(String message) {
        JSONObject content = new JSONObject();
        content.put(MESSAGE, message);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, MESSAGE);
        container.put(MESSAGE, content);
        return container;
    }

    public static JSONObject createWindowEvent(String username, int windowId) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, username);
        content.put(WINDOW_ID, windowId+"");
        content.put("window_side", FRONT); //constant for testing purpose
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, WINDOW_CHOICE);
        container.put(WINDOW, content);
        return container;
    }

    public static JSONObject createDiceEvent(DiceEvent diceEvent) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, diceEvent.getIdPlayer());
        content.put(DICE_ID, diceEvent.getIdDice());
        content.put("source", diceEvent.getSrc());
        JSONObject position = new JSONObject();
        position.put("y", diceEvent.getPosition().getRow());
        position.put("x", diceEvent.getPosition().getCol());
        content.put(POSITION, position);
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, ACTION);
        container.put(COMMAND_TYPE, MOVE_DICE_CHOICE);
        container.put(MOVE_DICE, content);
        return container;
    }

    public static Message parseJsonData(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(json);
            JSONObject data;
            System.out.println("Type : " + (String)jsonMsg.get(COMMAND_TYPE));
            switch ((String)jsonMsg.get(COMMAND_TYPE)) {
                case LOBBY_TIME:
                    data = (JSONObject) jsonMsg.get(TIME);
                    return new MatchTimeEvent((String)data.get(TIME));
                case ADD_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new AddPlayerEvent((String)data.get(USERNAME));
                case REMOVE_PLAYER:
                    data = (JSONObject) jsonMsg.get(PLAYER);
                    return new RemovePlayerEvent((String)data.get(USERNAME));
                case MESSAGE:
                    data = (JSONObject) jsonMsg.get(MESSAGE);
                    return new MessageEvent((String)data.get(METADATA));
                case ERROR:
                    data = (JSONObject) jsonMsg.get(ERROR);
                    return new ErrorEvent((String)data.get(ERROR));
                case "login_register":
                    return new RegisterEvent();
                case "login_heartbeat" :
                    data = (JSONObject) jsonMsg.get(HEARTBEAT);
                    return new HeartbeatInitEvent(
                               Integer.parseInt((String)data.get(HEARTBEAT_PORT)));
                case LOGIN :
                    data = (JSONObject) jsonMsg.get(LOGIN);
                    return new LobbyLoginEvent((String)data.get(TOKEN),
                               Integer.parseInt((String)data.get(LOBBY_PORT)));
                case BEGIN_TURN :
                    data = (JSONObject) jsonMsg.get(BEGIN_TURN);
                    return new BeginTurnEvent((String)data.get(PLAYER_ID));
                case WINDOW_LIST :

                    data = (JSONObject) jsonMsg.get(WINDOW_LIST);
                    WindowResponse windowResponse =  new WindowResponse((String)data.get(PLAYER_ID),
                                              Arrays.asList(Integer.parseInt((String)data.get(WINDOW_OPTION_ONE)),
                                                            Integer.parseInt((String)data.get(WINDOW_OPTION_TWO))));

                    return windowResponse;
                case DICE_LIST :
                    data = (JSONObject) jsonMsg.get(DICE_LIST);
                    List<Dice> diceResponse = new ArrayList<>();
                    JSONArray diceArray = (JSONArray)data.get(DICE);
                    for(int i = 0; i < diceArray.size(); i++) {
                        JSONObject diceJson = ((JSONObject)diceArray.get(i));
                        Dice dice = new Dice(Integer.parseInt((String)(diceJson.get(ID))),
                                Colors.stringToColor((String)(diceJson.get(COLOR))));
                        dice.setValue(Integer.parseInt((String)(diceJson).get(VALUE)));
                        diceResponse.add(dice);
                    }
                    System.out.println("DiceResponse generated");
                    return new DiceResponse((String)(data.get("destination")), diceResponse);
                default:
                    return null;
            }

        }
        catch (ParseException exc) {
            return null;
        }
    }
}