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
        //diceResponse.getDiceList().forEach(dice -> draft.add(new DiceView(dice.getColor(), dice.getValue(), dice.getId())));
    }

    public void setDraftListener(EventHandler eventHandler) {
        draft.forEach(diceView -> diceView.setOnMouseClicked(eventHandler));
    }
}
