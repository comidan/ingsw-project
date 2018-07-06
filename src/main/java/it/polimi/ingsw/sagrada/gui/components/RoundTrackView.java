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
public class RoundTrackView extends GridPane {

    /** The constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    /** The path constant MAX_ROUND. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";

    /** The list of dice view lists. */
    private ArrayList<ArrayList<DiceView>> diceViewList;

    /** the list of round boxes*/
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
     * Sets the dice on the roundtrack at the end of the turn
     *
     * @param diceViews the dice view to be added
     * @param currentRound the round to add the dices on
     */

    public void setRoundTrackEndTurn(List<DiceView> diceViews, int currentRound) {
        System.out.println("Setting round dices at round number : " + roundCellViewList.size());
        RoundCellView roundCellView = new RoundCellView(diceViews);
        roundCellView.setRoundNumber(roundCellViewList.size());
        for (int i = 0; i < diceViews.size(); i++) {
            DiceView diceView = diceViews.get(i);
            diceViewList.get(roundCellViewList.size()).add(diceView);
            diceViewList.get(roundCellViewList.size()).get(i).setImage(new Image(RoundTrackView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));
        }
        roundCellViewList.add(roundCellView);
        this.add(roundCellView, roundCellViewList.size(), 1);
        System.out.println("metto:" + roundCellView.getRoundNumber());

    }

    /**
     * sets the dice on toolcard usage
     * @param diceViews the list of dice views to be added, roundNum the number of the round to add dice views on
     */
    public void setDiceTool(List<DiceView> diceViews, int roundNum) {
        substituteDice(diceViews, roundNum);
    }

    /** the private methos to set dice views on toolcard usage */
    private void substituteDice(List<DiceView> diceViews, int roundNum){
        RoundCellView choseRound = roundCellViewList.get(roundNum - 1);
        choseRound.addDicePerRound(diceViews);
    }


    /**
     * Sets the handler to handle click on dice on the roundtrack
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
     * disables the handler to handle click on the round track dice
     */
    public void disableClick() {
        for(int i = 0; i< diceViewList.size(); i++){
            for (int j = 0; j< diceViewList.get(i).size(); j++){
                diceViewList.get(i).get(j).setDisable(true);
            }
        }

    }

}