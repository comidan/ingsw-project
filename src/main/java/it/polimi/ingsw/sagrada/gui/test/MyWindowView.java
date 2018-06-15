package it.polimi.ingsw.sagrada.gui.test;



//done

/**
 * The Class MyWindowView.
 */
public class MyWindowView extends WindowView {

    /**
     * Instantiates a new my window view.
     *
     * @param windowModel the window model
     * @param windowId the window id
     */
    MyWindowView(WindowModelInterface windowModel, Integer windowId) {
        super(windowModel, windowId, 500);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.WindowView#createButton()
     */
    @Override
    protected CellButton createButton() {
        CellButton button = new CellButton(new WindowButtonController());
        button.setMinHeight(95);
        button.setMinWidth(100);
        button.enable();
        return button;
    }
}