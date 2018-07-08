package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

import java.util.Set;


/**
 * The Class ChangeDiceValueToolMessage.
 */
public class ChangeDiceValueToolMessage implements Message, DiceManagerVisitor {

    /** The tool card. */
    private ToolCard toolCard;
    
    /** The dice id. */
    private int diceId;

    /**
     * Instantiates a new change dice value tool message.
     *
     * @param toolCard the tool card
     * @param diceId the dice id
     */
    public ChangeDiceValueToolMessage(ToolCard toolCard, int diceId) {
        this.toolCard = toolCard;
        this.diceId = diceId;
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
     * Gets the dice id.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
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
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor)
     */
    @Override
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) { diceManagerMessageVisitor.visit(this); }
}
