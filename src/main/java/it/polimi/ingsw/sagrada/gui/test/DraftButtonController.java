package it.polimi.ingsw.sagrada.gui.test;



//done

/**
 * The Class DraftButtonController.
 */
public class DraftButtonController extends ButtonController {
    
    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.ButtonControllerInterface#clickCallback(it.polimi.ingsw.sagrada.gui.test.CellButton)
     */
    @Override
    public void clickCallback(CellButton cellButton) {
        this.setDiceView(cellButton.getDiceView());
    }
}
