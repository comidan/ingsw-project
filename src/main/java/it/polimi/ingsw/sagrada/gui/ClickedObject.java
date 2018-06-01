package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.playables.Dice;

public class ClickedObject {
    private DiceView clickedDice;

    public DiceView getClickedDice() {
        return clickedDice;
    }

    public void setClickedDice(DiceView diceView) {
        this.clickedDice = diceView;
    }
}
