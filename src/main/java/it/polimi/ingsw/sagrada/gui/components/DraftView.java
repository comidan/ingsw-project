package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class DraftView.
 * @author Valentina, Daniele
 */
public class DraftView extends GridPane {

    /** The draft, containing the views of the dice drawn for the current round. */
    private List<DiceView> draft;

    /** The event handler to handle the drag on the draft dice. */
    private EventHandler<MouseEvent> draftClickHandler;

    /** The event handler to handle the click on the draft dice used for some toolcards */
    EventHandler<MouseEvent> changeValueHandler;

    /**
     * Instantiates a new draft view.
     *
     * @param diceResponse the message containing information on the dice
     */
    public DraftView(DiceResponse diceResponse) {
        draft = new ArrayList<>();
        diceResponse.getDiceList().forEach(dice -> draft.add(new DiceView(Constraint.getColorConstraint(dice.getColor()),
                                                            Constraint.getValueConstraint(dice.getValue()),
                                                            dice.getId())));
        createGrid();
    }

    /**
     * Creates the grid containing the dice.
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
        this.draftClickHandler = draftClickHandler;
        draft.forEach(diceView -> diceView.addEventHandler(MouseEvent.DRAG_DETECTED, this.draftClickHandler));
        }

    /**
     * enable the listener used to handle some toolcard effects regarding the draftwiew
     */

    public void enableDraftChangeValue(EventHandler<MouseEvent> changeValueHandler){
        this.changeValueHandler = changeValueHandler;
        if(! (draftClickHandler== null))
        draft.forEach(diceView -> diceView.removeEventHandler(MouseEvent.DRAG_DETECTED, draftClickHandler));
        draft.forEach(diceView -> diceView.setOnMouseClicked(changeValueHandler));
    }

    /**
     * disable the listener used to handle some toolcard effects regarding the draftwiew
     */
    public void disableDraftClick(){
        if(changeValueHandler!= null)
            draft.forEach(diceView -> diceView.removeEventHandler(MouseEvent.MOUSE_CLICKED, changeValueHandler));
    }


}