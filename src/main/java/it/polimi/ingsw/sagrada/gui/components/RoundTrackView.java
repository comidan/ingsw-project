package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;



/**
 * The Class RoundTrackView.
 */
public class RoundTrackView extends GridPane {

    /** The constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    /** The path constant MAX_ROUND. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";

    /** The list of dice view lists. */
    private List<List<DiceView>> diceViewList;

    /**  the list of round boxes. */
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



    /**
     * Sets the dice on the roundtrack at the end of the turn.
     *
     * @param diceViews the dice view to be added
     */

    public void setRoundTrackEndTurn(List<DiceView> diceViews) {
        RoundCellView roundCellView = new RoundCellView(diceViews);
        roundCellView.setRoundNumber(roundCellViewList.size());
        if(roundCellViewList.size()<diceViewList.size()) {
            for (int i = 0; i < diceViews.size(); i++) {
                DiceView diceView = diceViews.get(i);
                diceViewList.get(roundCellViewList.size()).add(diceView);
                diceViewList.get(roundCellViewList.size()).get(i).setImage(new Image(RoundTrackView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));
            }
            roundCellViewList.add(roundCellView);
            this.add(roundCellView, roundCellViewList.size(), 1);
        }
    }

    /**
     * sets the dice on toolcard usage.
     *
     * @param diceViews the list of dice views to be added, roundNum the number of the round to add dice views on
     * @param roundNum the round num
     */
    public void setDiceTool(List<DiceView> diceViews, int roundNum) {
        substituteDice(diceViews, roundNum);
    }

    /**
     * Sets the dice reconnection.
     *
     * @param roundTrack the new dice reconnection
     */
    public void setDiceReconnection(List<List<DiceView>> roundTrack) {
        roundTrack.forEach(list->{
            int index = roundTrack.indexOf(list);
            if(index<roundCellViewList.size()) substituteDice(list, index + 1);
            else setRoundTrackEndTurn(list);
        });
    }

    /**
     *  the private methos to set dice views on toolcard usage.
     *
     * @param diceViews the dice views
     * @param roundNum the round num
     */
    private void substituteDice(List<DiceView> diceViews, int roundNum){
        RoundCellView choseRound = roundCellViewList.get(roundNum - 1);
        choseRound.addDicePerRound(diceViews);
    }


    /**
     * Sets the handler to handle click on dice on the roundtrack.
     *
     * @param clickHandler the new click handler
     */
    public void setClickHandler(EventHandler<MouseEvent> clickHandler) {
        for(int i = 0; i< diceViewList.size(); i++){
            for (int j = 0; j< diceViewList.get(i).size(); j++){
                diceViewList.get(i).get(j).setDisable(false);
                diceViewList.get(i).get(j).setOnMouseClicked(clickHandler);
            }
        }

    }

    /**
     * disables the handler to handle click on the round track dice.
     */
    public void disableClick() {
        for(int i = 0; i< diceViewList.size(); i++){
            for (int j = 0; j< diceViewList.get(i).size(); j++){
                diceViewList.get(i).get(j).setDisable(true);
            }
        }

    }

}