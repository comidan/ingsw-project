package it.polimi.ingsw.sagrada.game.intercomm.visitor;

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



/**
 * The Interface ResponseMessageVisitor.
 */
public interface ResponseMessageVisitor {

    /**
     * Visit.
     *
     * @param message the message
     * @return the string
     */
    String visit(Message message);

    /**
     * Visit.
     *
     * @param diceResponse the dice response
     * @return the string
     */
    String visit(DiceResponse diceResponse);

    /**
     * Visit.
     *
     * @param windowResponse the window response
     * @return the string
     */
    String visit(WindowResponse windowResponse);

    /**
     * Visit.
     *
     * @param beginTurnEvent the begin turn event
     * @return the string
     */
    String visit(BeginTurnEvent beginTurnEvent);

    /**
     * Visit.
     *
     * @param opponentWindowResponse the opponent window response
     * @return the string
     */
    String visit(OpponentWindowResponse opponentWindowResponse);

    /**
     * Visit.
     *
     * @param opponentDiceMoveResponse the opponent dice move response
     * @return the string
     */
    String visit(OpponentDiceMoveResponse opponentDiceMoveResponse);

    /**
     * Visit.
     *
     * @param newTurnResponse the new turn response
     * @return the string
     */
    String visit(NewTurnResponse newTurnResponse);

    /**
     * Visit.
     *
     * @param ruleResponse the rule response
     * @return the string
     */
    String visit(RuleResponse ruleResponse);

    /**
     * Visit.
     *
     * @param publicObjectiveResponse the public objective response
     * @return the string
     */
    String visit(PublicObjectiveResponse publicObjectiveResponse);

    /**
     * Visit.
     *
     * @param privateObjectiveResponse the private objective response
     * @return the string
     */
    String visit(PrivateObjectiveResponse privateObjectiveResponse);

    /**
     * Visit.
     *
     * @param toolCardResponse the tool card response
     * @return the string
     */
    String visit(ToolCardResponse toolCardResponse);

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     * @return the string
     */
    String visit(ScoreResponse scoreResponse);

    /**
     * Visit.
     *
     * @param toolResponse the tool response
     * @return the string
     */
    String visit(ToolResponse toolResponse);

    /**
     * Visit.
     *
     * @param endTurnResponse the end turn response
     * @return the string
     */
    String visit(EndTurnResponse endTurnResponse);

    /**
     * Visit.
     *
     * @param timeRemainingResponse the time remaining response
     * @return the string
     */
    String visit(TimeRemainingResponse timeRemainingResponse);

    /**
     * Visit.
     *
     * @param enableWindowToolResponse the enable window tool response
     * @return the string
     */
    String visit(EnableWindowToolResponse enableWindowToolResponse);

    /**
     * Visit.
     *
     * @param roundTrackToolResponse the round track tool response
     * @return the string
     */
    String visit(RoundTrackToolResponse roundTrackToolResponse);

    /**
     * Visit.
     *
     * @param colorBagToolResponse the color bag tool response
     * @return the string
     */
    String visit(ColorBagToolResponse colorBagToolResponse);

    /**
     * Visit.
     *
     * @param diceRoundTrackReconnectionEvent the dice round track reconnection event
     * @return the string
     */
    String visit(DiceRoundTrackReconnectionEvent diceRoundTrackReconnectionEvent);
}