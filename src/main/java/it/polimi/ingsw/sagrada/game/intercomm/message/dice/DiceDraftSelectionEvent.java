package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;


/**
 * The Class DiceDraftSelectionEvent.
 */
public class DiceDraftSelectionEvent implements Message, ActionVisitor, ToolGameVisitor {

    /** The id player. */
    private String idPlayer;

    /** The id dice. */
    private int idDice;

    /**
     * Instantiates a new dice draft selection event.
     *
     * @param idPlayer the id player
     * @param idDice the id dice
     */
    public DiceDraftSelectionEvent(String idPlayer, int idDice) {
        this.idPlayer = idPlayer;
        this.idDice = idDice;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor)
     */
    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) { toolGameMessageVisitor.visit(this); }
}
