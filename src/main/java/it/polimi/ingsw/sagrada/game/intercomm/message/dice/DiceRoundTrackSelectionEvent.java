package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class DiceRoundTrackSelectionEvent implements Message, ActionVisitor, ToolGameVisitor {
    private String playerId;
    private int diceId;
    private int turn;

    public DiceRoundTrackSelectionEvent(String playerId, int diceId, int turn) {
        this.playerId = playerId;
        this.diceId = diceId;
        this.turn = turn;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getDiceId() {
        return diceId;
    }

    public int getTurn() {
        return turn;
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
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }

    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) {
        toolGameMessageVisitor.visit(this);
    }
}
