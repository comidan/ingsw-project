package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.message.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MessageParser implements CommandKeyword {


    public String createJsonDiceResponse(DiceResponse diceResponse) {
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, DICE_LIST);
        JSONObject diceList = new JSONObject();
        diceList.put(DESTINATION, diceResponse.getDst());
        JSONArray diceArray = new JSONArray();
        for(Dice dice:diceResponse.getDiceList()) {
            JSONObject diceM = new JSONObject();
            diceM.put(ID, dice.getId()+"");
            diceM.put(VALUE, dice.getValue()+"");
            diceM.put(COLOR, dice.getColor().toString());
            diceArray.add(diceM);
        }
        diceList.put(DICE, diceArray);
        message.put(DICE_LIST, diceList);
        return message.toJSONString();
    }

    public String createJsonWindowResponse(WindowResponse windowResponse) {
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, WINDOW_LIST);
        JSONObject windowList = new JSONObject();
        windowList.put(PLAYER_ID, windowResponse.getPlayerId());
        windowList.put(WINDOW_OPTION_ONE, windowResponse.getIds().get(0)+"");
        windowList.put(WINDOW_OPTION_TWO, windowResponse.getIds().get(1)+"");
        message.put(WINDOW_LIST, windowList);
        return message.toJSONString();
    }

    public String createJsonBeginTurnEvent(BeginTurnEvent beginTurnEvent) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, beginTurnEvent.getIdPlayer());
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, BEGIN_TURN);
        message.put(BEGIN_TURN, content);
        return message.toJSONString();
    }

    public String createJsonRuleResponse(RuleResponse ruleResponse) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, ruleResponse.getPlayerId());
        content.put(VALID_MOVE, ruleResponse.isMoveValid()+"");
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, RULE_RESPONSE);
        message.put(RULE_RESPONSE, content);
        return message.toJSONString();
    }
}
