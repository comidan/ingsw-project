package it.polimi.ingsw.sagrada.gui.test;


//done for now

/**
 * The Class WindowButtonController.
 */
public class WindowButtonController extends ButtonController {


    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.ButtonControllerInterface#clickCallback(it.polimi.ingsw.sagrada.gui.test.CellButton)
     */
    @Override
    public void clickCallback(CellButton cellButton) {
        if (this.getDiceView() != null)
            cellButton.setDiceView(this.getDiceView());
    }

}
