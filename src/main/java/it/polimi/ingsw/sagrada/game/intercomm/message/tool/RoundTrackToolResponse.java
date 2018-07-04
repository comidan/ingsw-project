package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

public class RoundTrackToolResponse implements Message, ResponseVisitor {

    private DiceResponse diceResponse;
    private int roundNumber;

    public RoundTrackToolResponse(DiceResponse diceResponse, int roundNumber) {
        this.diceResponse = diceResponse;
        this.roundNumber = roundNumber;
    }

    public DiceResponse getDiceResponse() {
        return diceResponse;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
