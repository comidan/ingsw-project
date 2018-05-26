package it.polimi.ingsw.sagrada.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class DraftView extends StackPane {


    DiceModel diceModel;

    DraftView() {
        this.diceModel = new DiceModel();
    }


    public void setDraft(List<Integer> Id) {


        GridPane gridPane = new GridPane();
        String url = null;
        int position = 0;
        for (Integer i : Id) {
            position++;
            DiceView diceView = new DiceView(diceModel);
            CellButton button = createButton(diceView);
            gridPane.add(button, position % 3, position);

        }

        this.getChildren().add(gridPane);
    }


    protected CellButton createButton(DiceView diceView) {
        CellButton button = new CellButton(new DraftButtonController(), diceView);
        button.enable();
        return button;
    }
}
