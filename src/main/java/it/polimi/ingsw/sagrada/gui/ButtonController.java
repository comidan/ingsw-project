package it.polimi.ingsw.sagrada.gui;


public abstract class ButtonController implements ButtonControllerInterface {

    private static DiceView diceView; //TODO this thing shouldn't exist


    protected static DiceView getDiceView() {
        return diceView;
    }

    protected static void setDiceView(DiceView diceView) {
        ButtonController.diceView = diceView;
    }
}
