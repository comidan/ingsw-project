package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    }

    /**
     * Sets the image.
     *
     * @param diceView the dice view
     * @param currentRound the current round
     */
    public void setImage(List<DiceView> diceView, int currentRound) {
         for(int i = 0; i< diceView.size(); i++){
            CellView cell = new CellView();
            cellViewList.get(currentRound).add(cell);
            cellViewList.get(currentRound).get(i).setImageCell(diceView.get(i));
            grid.add(cell, currentRound, i);
            this.setMargin(grid, new Insets(50,8,8,10));

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
