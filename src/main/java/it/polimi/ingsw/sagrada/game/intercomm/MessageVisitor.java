package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.*;

public interface MessageVisitor {

    void visit(Message message);

    void visit(AddPlayerEvent message);

    void visit(BeginTurnEvent beginTurnEvent);

    void visit(MatchTimeEvent matchTimeEvent);

    void visit(HeartbeatInitEvent heartbeatInitEvent);

    void visit(RemovePlayerEvent removePlayerEvent);

    void visit(WindowResponse windowResponse);

    void visit(OpponentWindowResponse opponentWindowResponse);

    void visit(DiceResponse diceResponse);

    void visit(OpponentDiceMoveResponse opponentDiceMoveResponse);

    void visit(RuleResponse ruleResponse);

    void visit(NewTurnResponse newTurnResponse);

    void visit(PrivateObjectiveResponse privateObjectiveResponse);

    void visit(PublicObjectiveResponse publicObjectiveResponse);

    void visit(ToolCardResponse toolCardResponse);
}