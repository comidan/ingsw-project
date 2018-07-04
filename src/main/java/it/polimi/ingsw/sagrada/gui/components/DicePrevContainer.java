package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DicePrevContainer extends VBox{

    List<DicePrev> dicePrevList;
    private static DicePrevContainer dicePrevContainer;

    private DicePrevContainer(){
        dicePrevList = new ArrayList<>();
        for(int i = 0; i<6; i++){
            DicePrev dicePrev = new DicePrev(i +1);
            dicePrevList.add(dicePrev);
            this.getChildren().add(dicePrev);
        }

    }

    public static DicePrevContainer getDicePrevContainer(){
        if(dicePrevContainer == null)
            dicePrevContainer = new DicePrevContainer();
        return dicePrevContainer;
    }

    public void setDicePrevHandler(EventHandler<MouseEvent> dicePrevHandler){
        for(int i = 0; i<6; i++){
            dicePrevList.get(i).setOnMouseClicked(dicePrevHandler);
        }
    }



}
