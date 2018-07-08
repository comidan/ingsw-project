package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.gui.components.DiceView;



/**
 * The Class ClickedObject.
 */
public class ClickedObject {
    
    /** The clicked dice. */
    private DiceView clickedDice;

    /**
     * Gets the clicked dice.
     *
     * @return the clicked dice
     */
    public DiceView getClickedDice() {
        return clickedDice;
    }

    /**
     * Sets the clicked dice.
     *
     * @param diceView the new clicked dice
     */
    public void setClickedDice(DiceView diceView) {
        this.clickedDice = diceView;
    }
}
