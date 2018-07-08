package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class RollAllDiceToolMessage.
 */
public class RollAllDiceToolMessage implements Message, DiceManagerVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;

    /**
     * Instantiates a new roll all dice tool message.
     *
     * @param toolCard the tool card
     */
    public RollAllDiceToolMessage(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    /**
     * Gets the tool card.
     *
     * @return the tool card
     */
    public ToolCard getToolCard() {
        return toolCard;
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
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) {
        diceManagerMessageVisitor.visit(this);
    }
}
