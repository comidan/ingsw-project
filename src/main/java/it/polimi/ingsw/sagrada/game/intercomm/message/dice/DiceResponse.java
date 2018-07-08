package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.List;



/**
 * The Class DiceResponse.
 */
public class DiceResponse implements Message, ResponseVisitor {

    /** The dst. */
    private String dst;
    
    /** The dice list. */
    private List<Dice> diceList;

    /**
     * Instantiates a new dice response.
     *
     * @param dst the dst
     * @param diceList the dice list
     */
    public DiceResponse(String dst, List<Dice> diceList) {
        this.dst = dst;
        this.diceList = diceList;
    }

    /**
     * Gets the dst.
     *
     * @return the dst
     */
    public String getDst() {
        return dst;
    }

    /**
     * Gets the dice list.
     *
     * @return the dice list
     */
    public List<Dice> getDiceList() {
        return diceList;
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
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
