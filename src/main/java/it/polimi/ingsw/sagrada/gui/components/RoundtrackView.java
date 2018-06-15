package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class RoundtrackView.
 */
public class RoundtrackView extends GridPane {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    /** The resizer. */
    private Resizer resizer;
    
    /** The cell view list. */
    private ArrayList<ArrayList<CellView>> cellViewList;

    /**
     * Instantiates a new roundtrack view.
     */
    public RoundtrackView() {
        this.resizer = new Resizer();
        this.cellViewList = new ArrayList<>();
        this.resize(resizer.getWidthPixel(30), resizer.getHeightPixel(20));
        for (int i = 0; i< MAX_ROUND; i++){
            ArrayList<CellView> roundDiceList = new ArrayList<>();
            cellViewList.add(roundDiceList);
        }
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
            this.add(cell, currentRound, i);

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
