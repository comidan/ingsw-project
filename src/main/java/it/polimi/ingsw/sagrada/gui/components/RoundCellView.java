package it.polimi.ingsw.sagrada.gui.components;

import javafx.scene.layout.VBox;

import java.util.List;

public class RoundCellView extends VBox{


    /**
     * the number of the round corresponding to the dice in this box
     */
    private int roundNumber;

    /**
     * the list of dice existing in this box
     */
    private List<DiceView> diceViewList;

    /**
     * Instantiates a new Round Cell View
     * @param diceViewList the list of dice to be added to this box
     */
    RoundCellView(List<DiceView> diceViewList) {
        this.diceViewList = diceViewList;
        addDicePerRound(diceViewList);
    }

    /**
     * gets the number of the round corresponding to this box
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * sets the number of the round corresponding to this box
     * @param roundNumber the round number
     */
    void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * removes old dice and call method to add new ones
     */
    void addDicePerRound(List<DiceView> diceViews) {
        diceViewList.forEach(diceView -> getChildren().remove(diceView));
        diceViewList = diceViews;
        addDicePerRound();
    }

    /**
     * adds dices for the round corresponding to the box
     */
    private void addDicePerRound() {
        for(int i = 0; i<diceViewList.size(); i++ ){
            getChildren().add(diceViewList.get(i));
            diceViewList.get(i).setRoundNumber(roundNumber);
        }
    }

    /**
     * removes a dice from the list and from the box
     */
    public void removeDice(){
        for(int i = 0; i< diceViewList.size(); i++){
            this.getChildren().remove(diceViewList.get(i));
            diceViewList.remove(diceViewList.get(i));
        }
    }


}
