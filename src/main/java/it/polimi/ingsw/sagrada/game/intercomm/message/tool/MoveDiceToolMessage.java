package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

public class MoveDiceToolMessage implements Message, BaseGameVisitor {
    private ToolCard toolCard;
    private int diceId;
    private String playerId;
    private Position position;

    public MoveDiceToolMessage(ToolCard toolCard, String playerId, int diceId, Position position) {
        this.toolCard = toolCard;
        this.diceId = diceId;
        this.playerId = playerId;
        this.position = position;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public int getDiceId() {
        return diceId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Position getPosition() {
        return position;
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
