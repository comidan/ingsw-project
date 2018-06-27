package it.polimi.ingsw.sagrada.network.server.protocols.application;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.ScoreResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import static it.polimi.ingsw.sagrada.network.CommandKeyword.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Set;


/**
 * The Class MessageParser.
 */
public class MessageParser implements ResponseMessageVisitor {

    /**
     * Creates the json response.
     *
     * @param responseVisitor the response visitor
     * @return the string
     */
    public String createJsonResponse(ResponseVisitor responseVisitor) {
        return responseVisitor.accept(this);
    }

    /**
     * Creates the json dice response.
     *
     * @param diceResponse the dice response
     * @return the string
     */
    private String createJsonDiceResponse(DiceResponse diceResponse) {
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, DICE_LIST);
        JSONObject diceList = new JSONObject();
        diceList.put(DESTINATION, diceResponse.getDst());
        JSONArray diceArray = new JSONArray();
        diceResponse.getDiceList().forEach(dice -> {
            JSONObject diceM = new JSONObject();
            diceM.put(ID, dice.getId()+"");
            diceM.put(VALUE, dice.getValue()+"");
            diceM.put(COLOR, dice.getColor().toString());
            diceArray.add(diceM);
        });
        diceList.put(DICE, diceArray);
        message.put(DICE_LIST, diceList);
        return message.toJSONString();
    }

    /**
     * Creates the json window response.
     *
     * @param windowResponse the window response
     * @return the string
     */
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

    /**
     * Creates the json begin turn event.
     *
     * @param beginTurnEvent the begin turn event
     * @return the string
     */
    private String createJsonBeginTurnEvent(BeginTurnEvent beginTurnEvent) {
        JSONObject message = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(PLAYER_ID, beginTurnEvent.getIdPlayer());
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, BEGIN_TURN);
        message.put(BEGIN_TURN, content);
        return message.toJSONString();
    }

    /**
     * Creates the json rule response.
     *
     * @param ruleResponse the rule response
     * @return the string
     */
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

    /**
     * Creates the opponent windows response.
     *
     * @param opponentWindowResponse the opponent window response
     * @return the string
     */
    private String createOpponentWindowsResponse(OpponentWindowResponse opponentWindowResponse) {
        List<String> players = opponentWindowResponse.getPlayers();
        JSONArray windows = new JSONArray();
        players.forEach(player -> {
            JSONObject window = new JSONObject();
            window.put(WINDOW_ID, opponentWindowResponse.getPlayerWindowId(player)+"");
            window.put(WINDOW_SIDE, WindowSide.sideToString(opponentWindowResponse.getPlayerWindowSide(player)));
            window.put(PLAYER_ID, player);
            windows.add(window);
        });
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, OPPONENT_WINDOW_LIST);
        container.put(OPPONENT_WINDOW_LIST, windows);
        return container.toJSONString();
    }

    /**
     * Creates the opponent dice response.
     *
     * @param opponentDiceMoveResponse the opponent dice move response
     * @return the string
     */
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

    /**
     * Creates the json new round response.
     *
     * @param newTurnResponse the new turn response
     * @return the string
     */
    private String createJsonNewRoundResponse(NewTurnResponse newTurnResponse) {
        JSONObject content = new JSONObject();
        content.put(NEW_ROUND, newTurnResponse.getRound()+"");
        JSONObject container = new JSONObject();
        container.put(COMMAND_TYPE, NEW_ROUND);
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(NEW_ROUND, content);
        return container.toJSONString();
    }

    /**
     * Creates the json public objectives response.
     *
     * @param publicObjectiveResponse the public objective response
     * @return the string
     */
    private String createJsonPublicObjectivesResponse(PublicObjectiveResponse publicObjectiveResponse) {
        List<Integer> publicObjectiveIds = publicObjectiveResponse.getIdObjective();
        JSONArray ids = new JSONArray();
        publicObjectiveIds.forEach(publicObjectiveId -> {
            JSONObject id = new JSONObject();
            id.put(ID, publicObjectiveId + "");
            ids.add(id);
        });
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, PUBLIC_OBJECTIVES);
        container.put(PUBLIC_OBJECTIVES, ids);
        return container.toJSONString();
    }

    /**
     * Creates the json private objective response.
     *
     * @param privateObjectiveResponse the private objective response
     * @return the string
     */
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

    /**
     * Creates the json tool cards response.
     *
     * @param toolCardResponse the tool card response
     * @return the string
     */
    private String createJsonToolCardsResponse(ToolCardResponse toolCardResponse) {
        List<Integer> toolCardIds = toolCardResponse.getIds();
        JSONArray ids = new JSONArray();
        toolCardIds.forEach(toolCardId -> {
            JSONObject id = new JSONObject();
            id.put(ID, toolCardId + "");
            ids.add(id);
        });
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, TOOL_CARDS);
        container.put(TOOL_CARDS, ids);
        return container.toJSONString();
    }

    private String createJsonScoreResponse(ScoreResponse scoreResponse) {
        JSONArray ids = new JSONArray();
        Set<String> usernames = scoreResponse.getUsernames();
        usernames.forEach(username -> {
            JSONObject id = new JSONObject();
            id.put(USERNAME, username);
            id.put(SCORE, scoreResponse.getScore(username) + "");
            ids.add(id);
        });
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, RANKING);
        container.put(RANKING, ids);
        return container.toJSONString();
    }

    private String createJsonToolResponse(ToolResponse toolResponse) {
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, toolResponse.getIdPlayer());
        data.put(CAN_BUY, toolResponse.isCanBuy() + "");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, TOOL_RESPONSE);
        container.put(TOOL, data);
        return container.toJSONString();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public String visit(Message message) {
        return ERROR;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse)
     */
    @Override
    public String visit(DiceResponse diceResponse) {
        return createJsonDiceResponse(diceResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse)
     */
    @Override
    public String visit(WindowResponse windowResponse) {
        return createJsonWindowResponse(windowResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent)
     */
    @Override
    public String visit(BeginTurnEvent beginTurnEvent) {
        return createJsonBeginTurnEvent(beginTurnEvent);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse)
     */
    @Override
    public String visit(OpponentWindowResponse opponentWindowResponse) {
        return createOpponentWindowsResponse(opponentWindowResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse)
     */
    @Override
    public String visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        return createOpponentDiceResponse(opponentDiceMoveResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse)
     */
    @Override
    public String visit(NewTurnResponse newTurnResponse) {
        return createJsonNewRoundResponse(newTurnResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse)
     */
    @Override
    public String visit(RuleResponse ruleResponse) {
        return createJsonRuleResponse(ruleResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse)
     */
    @Override
    public String visit(PublicObjectiveResponse publicObjectiveResponse) {
        return createJsonPublicObjectivesResponse(publicObjectiveResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse)
     */
    @Override
    public String visit(PrivateObjectiveResponse privateObjectiveResponse) {
        return createJsonPrivateObjectiveResponse(privateObjectiveResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse)
     */
    @Override
    public String visit(ToolCardResponse toolCardResponse) {
        return createJsonToolCardsResponse(toolCardResponse);
    }

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     * @return the string
     */
    @Override
    public String visit(ScoreResponse scoreResponse) { return createJsonScoreResponse(scoreResponse); }

    @Override
    public String visit(ToolResponse toolResponse) { return createJsonToolResponse(toolResponse);}
}
