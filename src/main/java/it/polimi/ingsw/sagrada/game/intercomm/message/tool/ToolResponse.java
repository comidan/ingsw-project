package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

public class ToolResponse implements Message, ResponseVisitor {
    private boolean canBuy;
    private String idPlayer;
    private int tokenSpent;

    public ToolResponse(boolean canBuy, String idPlayer, int tokenSpent) {
        this.canBuy = canBuy;
        this.idPlayer = idPlayer;
        this.tokenSpent = tokenSpent;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public int getTokenSpent() { return tokenSpent; }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) { return responseMessageVisitor.visit(this); }
}
