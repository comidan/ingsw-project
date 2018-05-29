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
        add(draft.get(0), 0, 0);
        add(draft.get(1), 1, 0);
        add(draft.get(2), 2, 0);
        add(draft.get(3), 0, 1);
        add(draft.get(4), 0, 2);

    }

    public void setDraftListener(EventHandler eventHandler) {
        draft.forEach(diceView -> diceView.setOnMouseClicked(eventHandler));
    }
}