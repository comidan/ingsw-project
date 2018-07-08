package it.polimi.ingsw.sagrada.network.server.protocols.application;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackReconnectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ColorBagToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableWindowToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import static it.polimi.ingsw.sagrada.network.CommandKeyword.*;

import it.polimi.ingsw.sagrada.network.CommandKeyword;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Set;



/**
 * The Class MessageToJsonConverter.
 */
public class MessageToJsonConverter implements ResponseMessageVisitor {

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
        content.put(VALUE, opponentDiceMoveResponse.getDice().getValue()+"");
        content.put(COLOR, opponentDiceMoveResponse.getDice().getColor().toString());
        JSONObject position = new JSONObject();
        position.put(CommandKeyword.X, opponentDiceMoveResponse.getPosition().getCol()+"");
        position.put(CommandKeyword.Y, opponentDiceMoveResponse.getPosition().getRow()+"");
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

    /**
     * Creates the json score response.
     *
     * @param scoreResponse the score response
     * @return the string
     */
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

    /**
     * Creates the json tool response.
     *
     * @param toolResponse the tool response
     * @return the string
     */
    private String createJsonToolResponse(ToolResponse toolResponse) {
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, toolResponse.getIdPlayer());
        data.put(CAN_BUY, toolResponse.isCanBuy() + "");
        data.put(COST, toolResponse.getTokenSpent() + "");
        data.put(TOOL_ID, toolResponse.getToolId() + "");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, TOOL_RESPONSE);
        container.put(TOOL, data);
        return container.toJSONString();
    }

    /**
     * Creates the json end turn response.
     *
     * @param endTurnResponse the end turn response
     * @return the string
     */
    private String createJsonEndTurnResponse(EndTurnResponse endTurnResponse) {
        JSONObject data = new JSONObject();
        data.put(USERNAME, endTurnResponse.getUsername());
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, END_TURN);
        container.put(END_TURN, data);
        return container.toJSONString();
    }

    /**
     * Creates the json time remaining response.
     *
     * @param timeRemainingResponse the time remaining response
     * @return the string
     */
    private String createJsonTimeRemainingResponse(TimeRemainingResponse timeRemainingResponse) {
        JSONObject data = new JSONObject();
        data.put(USERNAME, timeRemainingResponse.getUsername());
        data.put(TIME, timeRemainingResponse.getRemainingTime() + "");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, TIME);
        container.put(TIME, data);
        return container.toJSONString();
    }

    /**
     * Creates the json enable window response.
     *
     * @param enableWindowToolResponse the enable window tool response
     * @return the string
     */
    private String createJsonEnableWindowResponse(EnableWindowToolResponse enableWindowToolResponse) {
        JSONObject data = new JSONObject();
        data.put(USERNAME, enableWindowToolResponse.getPlayerId());
        data.put(TOOL_ID, enableWindowToolResponse.getToolId()+"");
        JSONObject container = new JSONObject();
        container.put(MESSAGE_TYPE, RESPONSE);
        container.put(COMMAND_TYPE, WINDOW_ENABLE);
        container.put(WINDOW_ENABLE, data);
        return container.toJSONString();
    }

    /**
     * Creates the json round track tool response.
     *
     * @param roundTrackToolResponse the round track tool response
     * @return the string
     */
    private String createJsonRoundTrackToolResponse(RoundTrackToolResponse roundTrackToolResponse) {
        DiceResponse diceResponse = roundTrackToolResponse.getDiceResponse();
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, ROUND_TRACK_RESPONSE);
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
        diceList.put(ROUND_NUMBER, roundTrackToolResponse.getRoundNumber()+"");
        message.put(DICE_LIST, diceList);
        return message.toJSONString();
    }

    /**
     * Creates the json color bag tool response.
     *
     * @param colorBagToolResponse the color bag tool response
     * @return the string
     */
    private String createJsonColorBagToolResponse(ColorBagToolResponse colorBagToolResponse) {
        JSONObject data = new JSONObject();
        data.put(USERNAME, colorBagToolResponse.getPlayerId());
        data.put(COLOR, colorBagToolResponse.getColor().toString());
        data.put(DICE_ID, colorBagToolResponse.getDiceId()+"");
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, COLOR_SELECTION);
        message.put(COLOR_SELECTION, data);
        return message.toJSONString();
    }

    /**
     * Creates the json dice round track reconnection event.
     *
     * @param diceRoundTrackReconnectionEvent the dice round track reconnection event
     * @return the string
     */
    private String createJsonDiceRoundTrackReconnectionEvent(DiceRoundTrackReconnectionEvent diceRoundTrackReconnectionEvent) {
        JSONObject message = new JSONObject();
        message.put(MESSAGE_TYPE, RESPONSE);
        message.put(COMMAND_TYPE, ROUND_TRACK_RECONNECT);
        JSONObject data = new JSONObject();
        data.put(PLAYER_ID, diceRoundTrackReconnectionEvent.getPlayerId());
        JSONArray list = new JSONArray();
        diceRoundTrackReconnectionEvent.getRoundTrack().forEach(round -> {
            JSONArray singleRound = new JSONArray();
            round.forEach(dice -> {
                JSONObject diceM = new JSONObject();
                diceM.put(ID, dice.getId()+"");
                diceM.put(VALUE, dice.getValue()+"");
                diceM.put(COLOR, dice.getColor().toString());
                singleRound.add(diceM);
            });
            list.add(singleRound);
        });
        data.put(ROUND_TRACK, list);
        message.put(ROUND_TRACK_RECONNECT, data);

        return message.toJSONString();
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse)
     */
    @Override
    public String visit(ToolResponse toolResponse) { return createJsonToolResponse(toolResponse);}

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnResponse)
     */
    @Override
    public String visit(EndTurnResponse endTurnResponse) {
        return createJsonEndTurnResponse(endTurnResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.TimeRemainingResponse)
     */
    @Override
    public String visit(TimeRemainingResponse timeRemainingResponse) {
        return createJsonTimeRemainingResponse(timeRemainingResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableWindowToolResponse)
     */
    @Override
    public String visit(EnableWindowToolResponse enableWindowToolResponse) {
        return createJsonEnableWindowResponse(enableWindowToolResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse)
     */
    @Override
    public String visit(RoundTrackToolResponse roundTrackToolResponse) {
        return createJsonRoundTrackToolResponse(roundTrackToolResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.ColorBagToolResponse)
     */
    @Override
    public String visit(ColorBagToolResponse colorBagToolResponse) {
        return createJsonColorBagToolResponse(colorBagToolResponse);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackReconnectionEvent)
     */
    @Override
    public String visit(DiceRoundTrackReconnectionEvent diceRoundTrackReconnectionEvent) {
        return createJsonDiceRoundTrackReconnectionEvent(diceRoundTrackReconnectionEvent);
    }
}
