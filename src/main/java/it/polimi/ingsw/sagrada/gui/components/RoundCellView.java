package it.polimi.ingsw.sagrada.gui.components;

import javafx.scene.layout.VBox;

import java.util.List;

public class RoundCellView extends VBox{

    private int roundNumber;

    private List<DiceView> diceViewList;


    RoundCellView(List<DiceView> diceViewList) {
        this.diceViewList = diceViewList;
        addDicePerRound(diceViewList);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }


    void addDicePerRound(List<DiceView> diceViews) {
        diceViewList.forEach(diceView -> getChildren().remove(diceView));
        diceViewList = diceViews;
        addDicePerRound();
    }

    private void addDicePerRound() {
        for(int i = 0; i<diceViewList.size(); i++ ){
            getChildren().add(diceViewList.get(i));
            diceViewList.get(i).setRoundNumber(roundNumber);
        }
    }

    public void removeDice(){
        for(int i = 0; i< diceViewList.size(); i++){
            this.getChildren().remove(diceViewList.get(i));
            diceViewList.remove(diceViewList.get(i));
        }
    }


}
