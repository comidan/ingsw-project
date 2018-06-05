package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

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

    public String createOpponentWindowsResponse(OpponentWindowResponse opponentWindowResponse) {
        List<String> players = opponentWindowResponse.getPlayers();
        JSONArray windows = new JSONArray();
        for(String player : players) {
            JSONObject window = new JSONObject();
            window.put(WINDOW_ID, opponentWindowResponse.getPlayerWindowId(player)+"");
            window.put(WINDOW_SIDE, WindowSide.sideToString(opponentWindowResponse.getPlayerWindowSide(player)));
            window.put(PLAYER_ID, player);
            windows.add(window);
        }
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, OPPONENT_WINDOW_LIST);
        container.put(OPPONENT_WINDOW_LIST, windows);
        return container.toJSONString();
    }

    public String createOpponentDiceResponse(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, opponentDiceMoveResponse.getIdPlayer());
        content.put(DICE_ID, opponentDiceMoveResponse.getDice().getId()+"");
        content.put(VALUE, opponentDiceMoveResponse.getDice().getValue()+"");
        content.put(COLOR, opponentDiceMoveResponse.getDice().getColor().toString());
        JSONObject position = new JSONObject();
        position.put("x", opponentDiceMoveResponse.getPosition().getCol()+"");
        position.put("y", opponentDiceMoveResponse.getPosition().getRow()+"");
        content.put(POSITION, position);
        JSONObject container = new JSONObject();
        container.put(COMMAND_TYPE, OPPONENT_DICE_RESPONSE);
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(DICE, content);
        return container.toJSONString();
    }
}
