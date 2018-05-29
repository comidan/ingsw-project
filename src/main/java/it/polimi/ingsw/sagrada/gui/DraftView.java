package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DraftView extends GridPane {

    private List<DiceView> draft;

    public DraftView(DiceResponse diceResponse) {
        draft = new ArrayList<>();
        diceResponse.getDiceList().forEach(dice -> draft.add(new DiceView(Constraint.getColorConstraint(dice.getColor()),
                                                            Constraint.getValueConstraint(dice.getValue()),
                                                            dice.getId())));
        createGrid();
    }

    private void createGrid() {
        int counter = 0;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if(counter >= draft.size())
                    return;
                add(draft.get(counter++), j, i);
            }


    }

    public void setDraftListener(EventHandler eventHandler) {
        draft.forEach(diceView -> diceView.setOnMouseClicked(eventHandler));
    }
}