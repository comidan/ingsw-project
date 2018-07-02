package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DiceEvent.
 */
public class DiceEvent implements Message, ActionVisitor, DiceManagerVisitor, ToolGameVisitor {

    /** The id player. */
    private String idPlayer;
    
    /** The id dice. */
    private int idDice;
    
    /** The position. */
    private Position position;
    
    /** The src. */
    private String src;

    /**
     * Instantiates a new dice event.
     *
     * @param idPlayer the id player
     * @param idDice the id dice
     * @param position the position
     * @param src the src
     */
    public DiceEvent(String idPlayer, int idDice, Position position, String src) {
        this.idPlayer = idPlayer;
        this.idDice = idDice;
        this.position = position;
        this.src = src;
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
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * Gets the src.
     *
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return  getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }

    @Override
    public void accept(DiceManagerMessageVisitor diceManagerMessageVisitor) {
        diceManagerMessageVisitor.visit(this);
    }

    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) { toolGameMessageVisitor.visit(this); }
}
