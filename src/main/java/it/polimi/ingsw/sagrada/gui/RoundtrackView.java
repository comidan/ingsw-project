package it.polimi.ingsw.sagrada.gui;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class RoundtrackView extends GridPane {

    private Resizer resizer;
    private ArrayList<ArrayList<CellView>> cellViewList;

    public RoundtrackView(){
        this.resizer = new Resizer();
        this.cellViewList = new ArrayList<ArrayList<CellView>>();
        this.resize(resizer.getWidthPixel(30), resizer.getHeightPixel(20));
    }


    public void setImage(List<DiceView> diceView, int currentRound){
         for(int i = 0; i< diceView.size(); i++){
            CellView cell = new CellView();
            cellViewList.get(currentRound).add(cell);
            cellViewList.get(currentRound).get(i).setImageCell(diceView.get(i));
            this.add(cell, currentRound, i);

        }

    }

}
