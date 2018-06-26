package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class CardBoard.
 */
public class CardBoard extends AnchorPane {
    
    /** The tool card view list. */
    private List<ToolCardView> toolCardViewList;

    private VBox privateCardBox;

    private VBox publicCardBox;

    private VBox toolBox;

    /**
     * Instantiates a new card board.
     */
    public CardBoard() {
        toolCardViewList = new ArrayList<>();
        privateCardBox = new VBox();
        publicCardBox = new VBox();
        toolBox = new VBox();
        setBottomAnchor(toolBox, GUIManager.getGameHeightPixel(12));
        setRightAnchor(toolBox, GUIManager.getGameWidthPixel(10));
        setBottomAnchor(privateCardBox, GUIManager.getGameHeightPixel(12));
        setRightAnchor(privateCardBox, GUIManager.getGameWidthPixel(22));
        setBottomAnchor(publicCardBox, GUIManager.getGameHeightPixel(12));
        setRightAnchor(publicCardBox, GUIManager.getGameWidthPixel(34));

    }

    /**
     * Sets the tool click handler.
     *
     * @param toolClickHandler the new tool click handler
     */
    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler) {

        for(Node toolCardView : toolBox.getChildren())
        {
            ToolCardView toolCard = (ToolCardView) toolCardView;
            toolCard.setToolClickHandler(toolClickHandler);
        }

      //  ((ToolCardView)toolBox.getChildren()).forEach(toolCardView -> toolCardView.setToolClickHandler(toolClickHandler));
    }

    /**
     * Sets the tool cards.
     *
     * @param toolCards the new tool cards
     */
    public void setToolCards(List<Integer> toolCards) {
        toolCards.forEach(id -> {
            ToolCardView toolCardView = new ToolCardView(id);
            toolBox.getChildren().add(toolCardView);
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
            publicCardBox.getChildren().add(objectiveCardView);
        });
    }

    /**
     * Sets the private objective.
     *
     * @param id the new private objective
     */
    public void setPrivateObjective(int id) {
        PrivateObjectiveView privateObjectiveView = new PrivateObjectiveView(id);
        privateCardBox.getChildren().add(privateObjectiveView);
    }

    public void showPrivateCards(){
        this.getChildren().add(privateCardBox);
    }

    public void showPublicCards(){
        this.getChildren().add(publicCardBox);
    }

    public void showToolCards(){
        this.getChildren().add(toolBox);
    }

    public void hidePrivateCards(){
        this.getChildren().remove(privateCardBox);
    }

    public void hidePublicCards(){
        this.getChildren().remove(publicCardBox);
    }

    public void hideToolCards(){
        this.getChildren().remove(toolBox);
    }



}
