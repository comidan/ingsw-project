package it.polimi.ingsw.sagrada.gui.components;

import javafx.scene.layout.VBox;

import java.util.List;

public class RoundCellView extends VBox{

    private int roundNumber;

    private List<DiceView> diceViewList;

    private  DiceView firstDice;

    public RoundCellView(List<DiceView> diceViewList) {
        this.diceViewList = diceViewList;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void addFirstDice(){
        getChildren().add(firstDice);
    }

    public void showAllDice(){
        for(int i = 1; i<diceViewList.size(); i++ ){
            getChildren().add(diceViewList.get(i));
        }
    }

    public void saveDice(List<DiceView> diceViews){
       diceViewList = diceViews;
       firstDice = diceViewList.get(0);
       addFirstDice();
    }

    public void hideDice(){
        for(int i = 1; i<diceViewList.size(); i++ ){
            getChildren().remove(diceViewList.get(i));
        }
    }


}
