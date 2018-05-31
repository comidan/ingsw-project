package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DraftView extends GridPane {

    private List<DiceView> draft;

    public DraftView(DiceResponse diceResponse, ClickedObject clickedObject) {
        draft = new ArrayList<>();
        diceResponse.getDiceList().forEach(dice -> draft.add(new DiceView(Constraint.getColorConstraint(dice.getColor()),
                                                            Constraint.getValueConstraint(dice.getValue()),
                                                            dice.getId(), clickedObject)));
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

    public void setDraftListener() {
        draft.forEach(diceView -> diceView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                diceView.clickHandler.clickCallbackDice(diceView);
            }
        })

        );
    }

    public List<DiceView> getDraft(){
        return draft;
    }

}