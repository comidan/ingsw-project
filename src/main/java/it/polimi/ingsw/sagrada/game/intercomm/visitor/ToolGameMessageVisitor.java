package it.polimi.ingsw.sagrada.game.intercomm.visitor;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;

public interface ToolGameMessageVisitor {
    void visit(EndTurnEvent endTurnEvent);

    void visit(ToolEvent toolEvent);

    void visit(DiceDraftSelectionEvent diceDraftSelectionEvent);
}
