package it.polimi.ingsw.sagrada.gui.cards;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class CardBoard.
 */
public class CardBoard extends GridPane {
    
    /** The tool card view list. */
    List<ToolCardView> toolCardViewList;
    
    /** The objective card view list. */
    List<ObjectiveCardView> objectiveCardViewList;

    /**
     * Instantiates a new card board.
     */
    public CardBoard() {
        toolCardViewList = new ArrayList<>();
        objectiveCardViewList = new ArrayList<>();
    }

    /**
     * Sets the tool click handler.
     *
     * @param toolClickHandler the new tool click handler
     */
    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler) {
        toolCardViewList.forEach(toolCardView -> toolCardView.setToolClickHandler(toolClickHandler));
    }

    /**
     * Sets the tool cards.
     *
     * @param toolCards the new tool cards
     */
    public void setToolCards(List<Integer> toolCards) {
        toolCards.forEach(id -> {
            ToolCardView toolCardView = new ToolCardView(id);
            add(toolCardView, toolCardViewList.size(), 1);
            toolCardViewList.add(toolCardView);
        });
    }

    /**
     * Sets the public objectives.
     *
     * @param publicObjectives the new public objectives
     */
    public void setPublicObjectives(List<Integer> publicObjectives) {
        publicObjectives.forEach(id -> {
            ObjectiveCardView objectiveCardView = new ObjectiveCardView(id);
            add(objectiveCardView, objectiveCardViewList.size(), 2);
            objectiveCardViewList.add(objectiveCardView);
        });
    }

    /**
     * Sets the private objective.
     *
     * @param id the new private objective
     */
    public void setPrivateObjective(int id) {
        PrivateObjectiveView privateObjectiveView = new PrivateObjectiveView(id);
        add(privateObjectiveView, 2, 3);
    }
}
