package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

import java.util.function.Consumer;


/**
 * The Class MoveDiceWindowToolMessage.
 */
public class MoveDiceWindowToolMessage implements Message, BaseGameVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;
    
    /** The id player. */
    private String idPlayer;
    
    /** The id dice. */
    private int idDice;
    
    /** The position. */
    private Position position;
    
    /** The ignored value. */
    private Consumer<Integer> ignoredValue;

    /**
     * Instantiates a new move dice window tool message.
     *
     * @param toolCard the tool card
     * @param idPlayer the id player
     * @param idDice the id dice
     * @param position the position
     * @param ignoredValue the ignored value
     */
    public MoveDiceWindowToolMessage(ToolCard toolCard, String idPlayer, int idDice, Position position, Consumer<Integer> ignoredValue) {
        this.toolCard = toolCard;
        this.idPlayer = idPlayer;
        this.idDice = idDice;
        this.position = position;
        this.ignoredValue = ignoredValue;
    }

    /**
     * Gets the tool card.
     *
     * @return the tool card
     */
    public ToolCard getToolCard() {
        return toolCard;
    }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * Gets the id dice.
     *
     * @return the id dice
     */
    public int getIdDice() {
        return idDice;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the ignored value.
     *
     * @return the ignored value
     */
    public Consumer<Integer> getIgnoredValue() {
        return ignoredValue;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) { messageVisitor.visit(this); }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor)
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) { baseGameMessageVisitor.visit(this); }
}
