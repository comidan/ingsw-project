package it.polimi.ingsw.sagrada.gui;

public class MyWindowView extends WindowView {

    MyWindowView(WindowModelInterface windowModel, Integer windowId) {
        super(windowModel, windowId, 500);
    }

    @Override
    protected CellButton createButton() {
        CellButton button = new CellButton(new WindowButtonController());
        button.setMinHeight(95);
        button.setMinWidth(100);
        button.enable();
        return button;
    }
}