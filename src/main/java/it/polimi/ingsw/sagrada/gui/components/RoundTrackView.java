package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class RoundTrackView.
 */
public class RoundTrackView extends HBox {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";
    
    /** The cell view list. */
    private ArrayList<ArrayList<DiceView>> diceViewList;

    private List<RoundCellView> roundCellViewList;


    /**
     * Instantiates a new roundtrack view.
     */
    public RoundTrackView() {
        roundCellViewList = new ArrayList<>();

        diceViewList = new ArrayList<>();
        for (int i = 0; i< MAX_ROUND; i++){
            ArrayList<DiceView> roundDiceList = new ArrayList<>();
            diceViewList.add(roundDiceList);

        }
       setMinSize(GUIManager.getGameWidthPixel(35), GUIManager.getGameHeightPixel(23));

    }

    public void setShowRoundDicesHandler(EventHandler<MouseEvent> showRoundDicesHandler){
        for(RoundCellView roundCellView : roundCellViewList){
            roundCellView.setOnMouseClicked(showRoundDicesHandler);
        }
    }

    /**
     * Sets the dice.
     *
     * @param diceViews the dice view
     * @param currentRound the current round
     */
    public void setDice(List<DiceView> diceViews, int currentRound) {
        RoundCellView roundCellView = new RoundCellView(diceViews);
        roundCellView.setRoundNumber(currentRound);
        roundCellView.saveDice(diceViews);

        for(int i = 0; i< diceViews.size(); i++){
            DiceView diceView = diceViews.get(i);
            diceViewList.get(currentRound - 1).add(diceView);
            diceViewList.get(currentRound - 1).get(i).setImage(new Image(RoundTrackView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));

        }
        roundCellViewList.add(roundCellView);
        this.getChildren().add(roundCellView);

    }

    /**
     * Sets the click handler.
     *
     * @param clickHandler the new click handler
     */
    public void setClickHandler(EventHandler<MouseEvent> clickHandler) {
        for(int i = 0; i< diceViewList.size(); i++){
            for (int j = 0; j< diceViewList.get(i).size(); j++){
                diceViewList.get(i).get(j).setOnDragDetected(clickHandler);
            }
        }

    }


}
