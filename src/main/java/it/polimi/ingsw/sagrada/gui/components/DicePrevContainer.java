package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DicePrevContainer extends VBox{

    List<DiceView> dicePrevList;

    public DicePrevContainer(Colors color, int diceId){
        dicePrevList = new ArrayList<>();
        for(int i = 0; i<6; i++){
            DiceView dicePrev = new DiceView(Constraint.getColorConstraint(color), Constraint.getValueConstraint(i+1), diceId);
            dicePrevList.add(dicePrev);
            this.getChildren().add(dicePrev);
        }
    }


    public void setDicePrevHandler(EventHandler<MouseEvent> dicePrevHandler){
        for(int i = 0; i<6; i++){
            dicePrevList.get(i).setDisable(false);
            dicePrevList.get(i).addEventHandler(MouseEvent.DRAG_DETECTED, dicePrevHandler);
        }
    }



}
