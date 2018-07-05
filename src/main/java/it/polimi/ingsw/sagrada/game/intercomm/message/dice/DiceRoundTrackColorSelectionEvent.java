package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class DiceRoundTrackColorSelectionEvent implements Message, ToolGameVisitor, ActionVisitor {
    private String playerId;
    private Colors constraint;

    public DiceRoundTrackColorSelectionEvent(String playerId, Colors constraint) {
        this.playerId = playerId;
        this.constraint = constraint;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Colors getConstraint() {
        return constraint;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) {
        toolGameMessageVisitor.visit(this);
    }

    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
