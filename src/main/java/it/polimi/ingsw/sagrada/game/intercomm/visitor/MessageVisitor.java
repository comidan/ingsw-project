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
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;

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