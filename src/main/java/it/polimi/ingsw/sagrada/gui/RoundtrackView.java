package it.polimi.ingsw.sagrada.gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class RoundtrackView extends GridPane {

    private Resizer resizer;
    private ArrayList<ArrayList<CellView>> cellViewList;
    private static final int roundNumber = 10;

    public RoundtrackView(){
        this.resizer = new Resizer();
        this.cellViewList = new ArrayList<ArrayList<CellView>>();
        this.resize(resizer.getWidthPixel(30), resizer.getHeightPixel(20));
        for (int i = 0; i<roundNumber; i++){
            ArrayList<CellView> roundDiceList = new ArrayList<>();
            cellViewList.add(roundDiceList);
        }
    }


    public void setImage(List<DiceView> diceView, int currentRound){
         for(int i = 0; i< diceView.size(); i++){
            CellView cell = new CellView();
            cellViewList.get(currentRound).add(cell);
            cellViewList.get(currentRound).get(i).setImageCell(diceView.get(i));
            this.add(cell, currentRound, i);

        }
    }

    public void setClickHandler(EventHandler<MouseEvent> clickHandler){
        for(int i = 0; i<cellViewList.size(); i++){
            for (int j = 0; j<cellViewList.get(i).size(); j++){
                cellViewList.get(i).get(j).setOnMouseClicked(clickHandler);

            }
        }

    }
}
