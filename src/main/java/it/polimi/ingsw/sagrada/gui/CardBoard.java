package it.polimi.ingsw.sagrada.gui;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardBoard extends GridPane {
    List<ToolCardView> toolCardViewList;
    Resizer resizer;

    public CardBoard() {
        toolCardViewList = new ArrayList<>();
        resizer = new Resizer();
        Random rand = new Random();
        for (int i = 1; i <= 3; i++) {
            ToolCardView toolCardView = new ToolCardView(rand.nextInt(6)+1);
            this.add(toolCardView, i, 1);
            toolCardViewList.add(toolCardView);

        }
    }

    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        toolCardViewList.forEach(toolCardView -> {toolCardView.setToolClickHandler(toolClickHandler);});

        }



}
