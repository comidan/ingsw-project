package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class RoundTrackView.
 */
public class RoundTrackView extends StackPane {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";
    
    /** The cell view list. */
    private ArrayList<ArrayList<DiceView>> diceViewList;

    private GridPane grid;
    private ImageView imageView;

    /**
     * Instantiates a new roundtrack view.
     */
    public RoundTrackView() {
        imageView = new ImageView();
        imageView.setImage(new Image(RoundTrackView.class.getResourceAsStream("/images/roundtrack.png"), GUIManager.getGameWidthPixel(45), GUIManager.getGameHeightPixel(13), true, false));
        this.getChildren().add(imageView);
        this.grid = new GridPane();
        this.diceViewList = new ArrayList<>();
        for (int i = 0; i< MAX_ROUND; i++){
            ArrayList<DiceView> roundDiceList = new ArrayList<>();
            diceViewList.add(roundDiceList);

        }
        this.getChildren().add(grid);
        this.setMargin(grid, new Insets(60,8,2,10));
        ColumnConstraints colConstr = new ColumnConstraints();
        colConstr.setPercentWidth(10);
        grid.getColumnConstraints().addAll(colConstr);

    }

    /**
     * Sets the dice.
     *
     * @param diceViews the dice view
     * @param currentRound the current round
     */
    public void setDice(List<DiceView> diceViews, int currentRound) {
         for(int i = 0; i< diceViews.size(); i++){
            DiceView diceView = diceViews.get(i);
            diceViewList.get(currentRound).add(diceView);
            diceViewList.get(currentRound).get(i).setImage(new Image(RoundTrackView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));
            grid.add(diceView, currentRound, i);
        }
    }

    /**
     * Sets the click handler.
     *
     * @param clickHandler the new click handler
     */
    public void setClickHandler(EventHandler<MouseEvent> clickHandler) {
        for(int i = 0; i< diceViewList.size(); i++){
            for (int j = 0; j< diceViewList.get(i).size(); j++){
                diceViewList.get(i).get(j).setOnMouseClicked(clickHandler);
            }
        }

    }


}
