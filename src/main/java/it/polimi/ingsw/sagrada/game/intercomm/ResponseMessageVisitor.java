package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.*;

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