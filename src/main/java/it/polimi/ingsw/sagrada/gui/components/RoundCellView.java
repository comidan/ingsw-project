package it.polimi.ingsw.sagrada.gui.components;

import javafx.scene.layout.VBox;

import java.util.List;

public class RoundCellView extends VBox{

    private int roundNumber;

    private List<DiceView> diceViewList;


    public RoundCellView(List<DiceView> diceViewList) {
        this.diceViewList = diceViewList;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }


    public void addDice(List<DiceView> diceViews){
       diceViewList = diceViews;
        for(int i = 0; i<diceViewList.size(); i++ ){
            getChildren().add(diceViewList.get(i));
            diceViewList.get(i).setRoundNumber(roundNumber);
        }
    }


}
