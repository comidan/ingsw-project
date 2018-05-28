package it.polimi.ingsw.sagrada.gui.test;


//done for now

public abstract class ButtonController implements ButtonControllerInterface {


    //we shouldn't use this static attribute, we should use the model to understand which dice to put

    private static DiceView diceView; //TODO this thing shouldn't exist


    protected static DiceView getDiceView() {
        return diceView;
    }

    protected static void setDiceView(DiceView diceView) {
        ButtonController.diceView = diceView;
    }
}
