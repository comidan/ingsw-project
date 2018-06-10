package it.polimi.ingsw.sagrada.gui.cards;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class CardBoard extends GridPane {
    List<ToolCardView> toolCardViewList;
    List<ObjectiveCardView> objectiveCardViewList;

    public CardBoard() {
        toolCardViewList = new ArrayList<>();
        objectiveCardViewList = new ArrayList<>();
    }

    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler) {
        toolCardViewList.forEach(toolCardView -> toolCardView.setToolClickHandler(toolClickHandler));
    }

    public void setToolCards(List<Integer> toolCards) {
        toolCards.forEach(id -> {
            ToolCardView toolCardView = new ToolCardView(id);
            add(toolCardView, toolCardViewList.size(), 1);
            toolCardViewList.add(toolCardView);
        });
    }

    public void setPublicObjectives(List<Integer> publicObjectives) {
        publicObjectives.forEach(id -> {
            ObjectiveCardView objectiveCardView = new ObjectiveCardView(id);
            add(objectiveCardView, objectiveCardViewList.size(), 2);
            objectiveCardViewList.add(objectiveCardView);
        });
    }

    public void setPrivateObjective(int id) {
        PrivateObjectiveView privateObjectiveView = new PrivateObjectiveView(id);
        add(privateObjectiveView, 2, 3);
    }
}
