package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class RoundtrackView.
 */
public class RoundtrackView extends StackPane {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";

    /** The GUIManager. */
    private GUIManager guiManager;
    
    /** The cell view list. */
    private ArrayList<ArrayList<CellView>> cellViewList;

    private GridPane grid;
    private ImageView imageView;

    /**
     * Instantiates a new roundtrack view.
     */
    public RoundtrackView() {
        imageView = new ImageView();
        imageView.setImage(new Image("/images/roundtrack.png", guiManager.getFullWidthPixel(62), guiManager.getFullHeightPixel(20), true, false));
        this.getChildren().add(imageView);
        this.grid = new GridPane();
        this.guiManager = new GUIManager();
        this.cellViewList = new ArrayList<>();
        for (int i = 0; i< MAX_ROUND; i++){
            ArrayList<CellView> roundDiceList = new ArrayList<>();
            cellViewList.add(roundDiceList);

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
            CellView cell = new CellView();
            cellViewList.get(currentRound).add(cell);
            DiceView diceView = diceViews.get(i);
            cellViewList.get(currentRound).get(i).setImage(new Image(CellView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));
            grid.add(cell, currentRound, i);
        }
    }

    /**
     * Sets the click handler.
     *
     * @param clickHandler the new click handler
     */
    public void setClickHandler(EventHandler<MouseEvent> clickHandler) {
        for(int i = 0; i<cellViewList.size(); i++){
            for (int j = 0; j<cellViewList.get(i).size(); j++){
                cellViewList.get(i).get(j).setOnMouseClicked(clickHandler);
            }
        }

    }


}
