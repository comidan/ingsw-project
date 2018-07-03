package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.EnableWindowToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;


/**
 * The Interface MessageVisitor.
 */
public interface MessageVisitor {

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(Message message);

    /**
     * Visit.
     *
     * @param message the message
     */
    void visit(AddPlayerEvent message);

    /**
     * Visit.
     *
     * @param beginTurnEvent the begin turn event
     */
    void visit(BeginTurnEvent beginTurnEvent);

    /**
     * Visit.
     *
     * @param matchTimeEvent the match time event
     */
    void visit(MatchTimeEvent matchTimeEvent);

    /**
     * Visit.
     *
     * @param heartbeatInitEvent the heartbeat init event
     */
    void visit(HeartbeatInitEvent heartbeatInitEvent);

    /**
     * Visit.
     *
     * @param removePlayerEvent the remove player event
     */
    void visit(RemovePlayerEvent removePlayerEvent);

    /**
     * Visit.
     *
     * @param windowResponse the window response
     */
    void visit(WindowResponse windowResponse);

    /**
     * Visit.
     *
     * @param opponentWindowResponse the opponent window response
     */
    void visit(OpponentWindowResponse opponentWindowResponse);

    /**
     * Visit.
     *
     * @param diceResponse the dice response
     */
    void visit(DiceResponse diceResponse);

    /**
     * Visit.
     *
     * @param opponentDiceMoveResponse the opponent dice move response
     */
    void visit(OpponentDiceMoveResponse opponentDiceMoveResponse);

    /**
     * Visit.
     *
     * @param ruleResponse the rule response
     */
    void visit(RuleResponse ruleResponse);

    /**
     * Visit.
     *
     * @param newTurnResponse the new turn response
     */
    void visit(NewTurnResponse newTurnResponse);

    /**
     * Visit.
     *
     * @param privateObjectiveResponse the private objective response
     */
    void visit(PrivateObjectiveResponse privateObjectiveResponse);

    /**
     * Visit.
     *
     * @param publicObjectiveResponse the public objective response
     */
    void visit(PublicObjectiveResponse publicObjectiveResponse);

    /**
     * Visit.
     *
     * @param toolCardResponse the tool card response
     */
    void visit(ToolCardResponse toolCardResponse);

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     */
    void visit(ScoreResponse scoreResponse);

    /**
     * Visit.
     *
     * @param toolResponse the tool response
     */
    void visit(ToolResponse toolResponse);

    /**
     * Visit.
     *
     * @param endTurnResponse the force end turn response
     */
    void visit(EndTurnResponse endTurnResponse);

    /**
     * Visit.
     *
     * @param timeRemainingResponse the time remaining response
     */
    void visit(TimeRemainingResponse timeRemainingResponse);

    void visit(EnableWindowToolResponse enableWindowToolResponse);
}