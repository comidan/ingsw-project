package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

public class ColorBagToolResponse implements Message, ResponseVisitor {
    private String playerId;
    private Colors color;
    private int diceId;

    public ColorBagToolResponse(String playerId, Colors color, int diceId) {
        this.playerId = playerId;
        this.color = color;
        this.diceId = diceId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Colors getColor() {
        return color;
    }

    public int getDiceId() {
        return diceId;
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
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
