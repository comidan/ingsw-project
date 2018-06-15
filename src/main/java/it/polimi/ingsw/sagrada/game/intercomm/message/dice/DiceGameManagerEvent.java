package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.Dice;


/**
 * The Class DiceGameManagerEvent.
 */
public class DiceGameManagerEvent extends DiceEvent {

    /** The dice. */
    private Dice dice;

    /**
     * Instantiates a new dice game manager event.
     *
     * @param dice the dice
     * @param diceEvent the dice event
     */
    public DiceGameManagerEvent(Dice dice, DiceEvent diceEvent) {
        super(diceEvent.getIdPlayer(), diceEvent.getIdDice(), diceEvent.getPosition(), diceEvent.getSrc());
        this.dice = dice;
    }

    /**
     * Gets the dice.
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return  getClass();
    }
}
