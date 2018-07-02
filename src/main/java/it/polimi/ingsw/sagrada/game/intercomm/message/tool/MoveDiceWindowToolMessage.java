package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

import java.util.function.Consumer;

public class MoveDiceWindowToolMessage implements Message, BaseGameVisitor {
    private ToolCard toolCard;
    private String idPlayer;
    private int idDice;
    private Position position;
    private Consumer<Integer> ignoredValue;

    public MoveDiceWindowToolMessage(ToolCard toolCard, String idPlayer, int idDice, Position position, Consumer<Integer> ignoredValue) {
        this.toolCard = toolCard;
        this.idPlayer = idPlayer;
        this.idDice = idDice;
        this.position = position;
        this.ignoredValue = ignoredValue;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public int getIdDice() {
        return idDice;
    }

    public Position getPosition() {
        return position;
    }

    public Consumer<Integer> getIgnoredValue() {
        return ignoredValue;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) { baseGameMessageVisitor.visit(this); }
}
