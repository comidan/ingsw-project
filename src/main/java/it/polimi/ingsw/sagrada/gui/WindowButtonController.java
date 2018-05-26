package it.polimi.ingsw.sagrada.gui;

public class WindowButtonController extends ButtonController {

    @Override
    public void clickCallback(CellButton cellButton) {
        if (this.getDiceView() != null)
            cellButton.setDiceView(this.getDiceView());
    }

}
