package it.polimi.ingsw.sagrada.gui.test;



//done for now

/**
 * The Class ButtonController.
 */
public abstract class ButtonController implements ButtonControllerInterface {


    //we shouldn't use this static attribute, we should use the model to understand which dice to put

    /** The dice view. */
    private static DiceView diceView; //TODO this thing shouldn't exist


    /**
     * Gets the dice view.
     *
     * @return the dice view
     */
    protected static DiceView getDiceView() {
        return diceView;
    }

    /**
     * Sets the dice view.
     *
     * @param diceView the new dice view
     */
    protected static void setDiceView(DiceView diceView) {
        ButtonController.diceView = diceView;
    }
}
