package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.gui.components.DiceView;

public class ClickedObject {
    private DiceView clickedDice;

    public DiceView getClickedDice() {
        return clickedDice;
    }

    public void setClickedDice(DiceView diceView) {
        this.clickedDice = diceView;
    }
}
