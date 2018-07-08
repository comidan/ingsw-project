package it.polimi.ingsw.sagrada.game.intercomm.message.tool;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;


/**
 * The Class MoveDiceToolMessage.
 */
public class MoveDiceToolMessage implements Message, BaseGameVisitor {
    
    /** The tool card. */
    private ToolCard toolCard;
    
    /** The dice id. */
    private int diceId;
    
    /** The player id. */
    private String playerId;
    
    /** The position. */
    private Position position;

    /**
     * Instantiates a new move dice tool message.
     *
     * @param toolCard the tool card
     * @param playerId the player id
     * @param diceId the dice id
     * @param position the position
     */
    public MoveDiceToolMessage(ToolCard toolCard, String playerId, int diceId, Position position) {
        this.toolCard = toolCard;
        this.diceId = diceId;
        this.playerId = playerId;
        this.position = position;
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

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor)
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
