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
 * It contains the GUI elements of the three types of cards existing in the game

 * @author Valentina
 */
public class CardBoard extends AnchorPane {
    
    /**  The list of toolcards drawn for the current game. */
    private List<ToolCardView> toolCardViewList;

    /**  The VBox containing private objective cards. */
    private VBox privateCardBox;

    /**  The VBox containing public objective cards. */
    private VBox publicCardBox;

    /**  The VBox containing toolcards. */
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
     * Sets the tool click handler to make it possible to "buy" toolcards.
     *
     * @param toolClickHandler the event handler passed from the adapter
     */
    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler) {

        for(Node toolCardView : toolBox.getChildren())
        {
            ToolCardView toolCard = (ToolCardView) toolCardView;
            toolCard.enableToolClickHandler(toolClickHandler);
        }
    }

    public void disableToolClickHandler() {
        for(Node toolCardView : toolBox.getChildren()) {
            ToolCardView toolCard = (ToolCardView) toolCardView;
            toolCard.disableToolClickHandler();
        }
    }


    /**
     * Sets the toolcards drawn for the current game.
     *
     * @param toolCards the list of ids of toolcards extracted from model
     */
    public void setToolCards(List<Integer> toolCards) {
        toolCards.forEach(id -> {
            ToolCardView toolCardView = new ToolCardView(id);
            toolBox.getChildren().add(toolCardView);
            toolCardViewList.add(toolCardView);
        });
    }

    /**
     * Sets the public objectives drawn for the current game.
     *
     * @param publicObjectives the list of ids of public objectives extracted from model
     */
    public void setPublicObjectives(List<Integer> publicObjectives) {
        publicObjectives.forEach(id -> {
            ObjectiveCardView objectiveCardView = new ObjectiveCardView(id);
            publicCardBox.getChildren().add(objectiveCardView);
        });
    }

    /**
     * Sets the private objective drawn for the current game.
     *
     * @param id the id of the private objective extracted from model
     */
    public void setPrivateObjective(int id) {
        PrivateObjectiveView privateObjectiveView = new PrivateObjectiveView(id);
        privateCardBox.getChildren().add(privateObjectiveView);
    }

    /**
     * shows private objective cards on gui.
     */
    public void showPrivateCards(){
        this.getChildren().add(privateCardBox);
    }

    /**
     * shows public objective cards on gui.
     */
    public void showPublicCards(){
        this.getChildren().add(publicCardBox);
    }

    /**
     * shows toolcards cards on gui.
     */
    public void showToolCards(){
        this.getChildren().add(toolBox);
    }

    /**
     * hides private objective cards on gui.
     */
    public void hidePrivateCards(){
        this.getChildren().remove(privateCardBox);
    }

    /**
     * hides public objective cards on gui.
     */
    public void hidePublicCards(){
        this.getChildren().remove(publicCardBox);
    }

    /**
     * hides toolcards cards on gui.
     */
    public void hideToolCards(){
        this.getChildren().remove(toolBox);
    }


    /**
     * disables the possibility to uy toolcard (used when it's not one's turn).
     */
    public void disableToolBuy(){
        for( ToolCardView toolCardView : toolCardViewList)
        {
            toolCardView.setDisable(true);
        }
    }

    /**
     * enables the possibility to uy toolcard (used when it's one's turn).
     */
    public void enableToolBuy(){
        for( ToolCardView toolCardView : toolCardViewList)
        {
            toolCardView.setDisable(false);
        }
    }

}
