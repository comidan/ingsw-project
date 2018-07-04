package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.CompleteSwapDiceToolMessage;

public interface RoundTrackMessageVisitor {
    void visit(DiceEvent diceEvent);

    void visit(CompleteSwapDiceToolMessage completeSwapDiceToolMessage);
}
