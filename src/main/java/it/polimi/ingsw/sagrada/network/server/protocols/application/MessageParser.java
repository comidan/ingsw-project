package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class MessageParser implements CommandKeyword, ResponseMessageVisitor {

    public String createJsonResponse(ResponseVisitor responseVisitor) {
        return responseVisitor.accept(this);
    }

    private String createJsonDiceResponse(DiceResponse diceResponse) {
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, DICE_LIST);
        JSONObject diceList = new JSONObject();
        diceList.put(DESTINATION, diceResponse.getDst());
        JSONArray diceArray = new JSONArray();
        for(Dice dice : diceResponse.getDiceList()) {
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

    private String createJsonWindowResponse(WindowResponse windowResponse) {
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

    private String createJsonBeginTurnEvent(BeginTurnEvent beginTurnEvent) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, beginTurnEvent.getIdPlayer());
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, BEGIN_TURN);
        message.put(BEGIN_TURN, content);
        return message.toJSONString();
    }

    private String createJsonRuleResponse(RuleResponse ruleResponse) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, ruleResponse.getPlayerId());
        content.put(VALID_MOVE, ruleResponse.isMoveValid()+"");
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, RULE_RESPONSE);
        message.put(RULE_RESPONSE, content);
        return message.toJSONString();
    }

    private String createOpponentWindowsResponse(OpponentWindowResponse opponentWindowResponse) {
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

    private String createOpponentDiceResponse(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, opponentDiceMoveResponse.getIdPlayer());
        content.put(DICE_ID, opponentDiceMoveResponse.getDice().getId()+"");
        System.out.println("Dice pre message: "+opponentDiceMoveResponse.getDice().getValue());
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

    private String createJsonNewRoundResponse(NewTurnResponse newTurnResponse) {
        JSONObject content = new JSONObject();
        content.put(NEW_ROUND, newTurnResponse.getRound()+"");
        JSONObject container = new JSONObject();
        container.put(COMMAND_TYPE, NEW_ROUND);
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(NEW_ROUND, content);
        return container.toJSONString();
    }

    private String createJsonPublicObjectivesResponse(PublicObjectiveResponse publicObjectiveResponse) {
        List<Integer> publicObjectiveIds = publicObjectiveResponse.getIdObjective();
        JSONArray ids = new JSONArray();
        for(int publicObjectiveId : publicObjectiveIds) {
            JSONObject id = new JSONObject();
            id.put(ID, publicObjectiveId + "");
            ids.add(id);
        }
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, PUBLIC_OBJECTIVES);
        container.put(PUBLIC_OBJECTIVES, ids);
        return container.toJSONString();
    }

    private String createJsonPrivateObjectiveResponse(PrivateObjectiveResponse privateObjectiveResponse) {
        JSONObject content = new JSONObject();
        content.put(ID, privateObjectiveResponse.getIdObjective() + "");
        content.put(PLAYER_ID, privateObjectiveResponse.getIdPlayer());
        JSONObject container = new JSONObject();
        container.put(COMMAND_TYPE, PRIVATE_OBJECTIVE);
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(PRIVATE_OBJECTIVE, content);
        return container.toJSONString();
    }

    private String createJsonToolCardsResponse(ToolCardResponse toolCardResponse) {
        List<Integer> toolCardIds = toolCardResponse.getIds();
        JSONArray ids = new JSONArray();
        for(int toolCardId : toolCardIds) {
            JSONObject id = new JSONObject();
            id.put(ID, toolCardId + "");
            ids.add(id);
        }
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, TOOL_CARDS);
        container.put(TOOL_CARDS, ids);
        return container.toJSONString();
    }

    @Override
    public String visit(Message message) {
        return ERROR;
    }

    @Override
    public String visit(DiceResponse diceResponse) {
        return createJsonDiceResponse(diceResponse);
    }

    @Override
    public String visit(WindowResponse windowResponse) {
        return createJsonWindowResponse(windowResponse);
    }

    @Override
    public String visit(BeginTurnEvent beginTurnEvent) {
        return createJsonBeginTurnEvent(beginTurnEvent);
    }

    @Override
    public String visit(OpponentWindowResponse opponentWindowResponse) {
        return createOpponentWindowsResponse(opponentWindowResponse);
    }

    @Override
    public String visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        return createOpponentDiceResponse(opponentDiceMoveResponse);
    }

    @Override
    public String visit(NewTurnResponse newTurnResponse) {
        return createJsonNewRoundResponse(newTurnResponse);
    }

    @Override
    public String visit(RuleResponse ruleResponse) {
        return createJsonRuleResponse(ruleResponse);
    }

    @Override
    public String visit(PublicObjectiveResponse publicObjectiveResponse) {
        return createJsonPublicObjectivesResponse(publicObjectiveResponse);
    }

    @Override
    public String visit(PrivateObjectiveResponse privateObjectiveResponse) {
        return createJsonPrivateObjectiveResponse(privateObjectiveResponse);
    }

    @Override
    public String visit(ToolCardResponse toolCardResponse) {
        return createJsonToolCardsResponse(toolCardResponse);
    }
}
