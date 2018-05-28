package it.polimi.ingsw.sagrada.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class WindowView extends GridPane {

    private final Label label;
    private final CellView[][] windowDices;

    public WindowView(Constraint[][] constraints) {
        label = new Label();
        windowDices = new CellView[4][5];
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                windowDices[i][j] = new CellView(i, j, constraints[i][j]);
        createGrid();
    }
    
    private void createGrid() {
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                add(windowDices[i][j], j, i);
    }
    
    public void setWindowDiceListener(EventHandler eventHandler) {
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                windowDices[i][j].setOnMouseClicked(eventHandler);
    }
}
