package it.polimi.ingsw.sagrada.gui;


//done

public class DraftButtonController extends ButtonController {
    @Override
    public void clickCallback(CellButton cellButton) {
        this.setDiceView(cellButton.getDiceView());
    }
}
