package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class DraftView.
 */
public class DraftView extends GridPane {

    /** The draft. */
    private List<DiceView> draft;

    /**
     * Instantiates a new draft view.
     *
     * @param diceResponse the dice response
     */
    public DraftView(DiceResponse diceResponse) {
        draft = new ArrayList<>();
        diceResponse.getDiceList().forEach(dice -> draft.add(new DiceView(Constraint.getColorConstraint(dice.getColor()),
                                                            Constraint.getValueConstraint(dice.getValue()),
                                                            dice.getId())));
        createGrid();
    }

    /**
     * Creates the grid.
     */
    private void createGrid() {
        int counter = 0;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if(counter >= draft.size())
                    return;
                add(draft.get(counter++), j, i);
            }


    }

    /**
     * Sets the draft listener.
     *
     * @param draftClickHandler the new draft listener
     */
    public void setDraftListener(EventHandler<MouseEvent> draftClickHandler) {
        draft.forEach(diceView -> diceView.setOnDragDetected(draftClickHandler));
    }

    public void setDraftChangeValue(EventHandler<MouseEvent> changeValueHandler){
        draft.forEach(diceView -> diceView.setOnDragDetected(changeValueHandler));
    }


}