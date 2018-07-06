package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class DraftToBagToolMessage implements Message, BaseGameVisitor {
    private ToolCard toolCard;
    private String playerId;
    private int diceId;

    public DraftToBagToolMessage(ToolCard toolCard, String playerId, int diceId) {
        this.toolCard = toolCard;
        this.playerId = playerId;
        this.diceId = diceId;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public String getPlayerId() {
        return playerId;
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
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
