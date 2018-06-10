package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;

public interface ResponseMessageVisitor {

    String visit(Message message);

    String visit(DiceResponse diceResponse);

    String visit(WindowResponse windowResponse);

    String visit(BeginTurnEvent beginTurnEvent);

    String visit(OpponentWindowResponse opponentWindowResponse);

    String visit(OpponentDiceMoveResponse opponentDiceMoveResponse);

    String visit(NewTurnResponse newTurnResponse);

    String visit(RuleResponse ruleResponse);

    String visit(PublicObjectiveResponse publicObjectiveResponse);

    String visit(PrivateObjectiveResponse privateObjectiveResponse);

    String visit(ToolCardResponse toolCardResponse);
}