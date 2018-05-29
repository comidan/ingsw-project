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
        for(int i = 0; i < constraints.length; i++)
            for(int j = 0; j < constraints[0].length; j++)
                windowDices[i][j] = new CellView(i, j, constraints[i][j]);
        setGridLinesVisible(true);
        createGrid();
    }
    
    private void createGrid() {
        for(int i = 0; i < windowDices.length; i++)
            for(int j = 0; j < windowDices[0].length; j++)
                add(windowDices[i][j], j, i);
    }
    
    public void setWindowDiceListener(EventHandler eventHandler) {
        for(int i = 0; i < windowDices.length; i++)
            for(int j = 0; j < windowDices[0].length; j++)
                windowDices[i][j].setOnMouseClicked(eventHandler);
    }
}