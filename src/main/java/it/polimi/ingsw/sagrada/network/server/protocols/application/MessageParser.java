package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.message.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MessageParser {

    public String createJsonDiceResponse(DiceResponse diceResponse) {
        JSONObject message = new JSONObject();
        message.put("type_msg", "response");
        message.put("type_cmd", "dice_list");
        JSONObject diceList = new JSONObject();
        diceList.put("destination", diceResponse.getDst());
        JSONArray diceArray = new JSONArray();
        for(Dice dice:diceResponse.getDiceList()) {
            JSONObject diceM = new JSONObject();
            diceM.put("id", dice.getId()+"");
            diceM.put("value", dice.getValue()+"");
            diceM.put("color", dice.getColor().toString());
            diceArray.add(diceM);
        }
        diceList.put("dice", diceArray);
        message.put("dice_list", diceList);
        return message.toJSONString();
    }

    public String createJsonWindowResponse(WindowResponse windowResponse) {
        JSONObject message = new JSONObject();
        message.put("type_msg", "response");
        message.put("type_cmd", "window_list");
        JSONObject windowList = new JSONObject();
        windowList.put("id_player", windowResponse.getPlayerId());
        windowList.put("window_id_1", windowResponse.getIds().get(0)+"");
        windowList.put("window_id_2", windowResponse.getIds().get(1)+"");
        message.put("window_list", windowList);
        return message.toJSONString();
    }

    public String createJsonBeginTurnEvent(BeginTurnEvent beginTurnEvent) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("id_player", beginTurnEvent.getIdPlayer());
        message.put("type_msg", "response");
        message.put("type_cmd", "begin_turn");
        message.put("begin_turn", content);
        return message.toJSONString();
    }
}
