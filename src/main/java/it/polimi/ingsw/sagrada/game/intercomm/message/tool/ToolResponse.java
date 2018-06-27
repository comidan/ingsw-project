package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class ToolResponse implements Message {
    private boolean canBuy;

    private String idPlayer;

    public ToolResponse(boolean canBuy, String idPlayer) {
        this.canBuy = canBuy;
        this.idPlayer = idPlayer;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
