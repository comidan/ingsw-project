package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.game.playables.Dice;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DicePrevContainer.
 * @author Valentina
 */

public class DicePrevContainer extends VBox{
    /**
     * the list of dice previews
     */
    List<DicePrev> dicePrevList;

    /**
     * the instance of the dice preview container
     */
    private static DicePrevContainer dicePrevContainer;

    /**
     * instantiates a new dice preview container
     */
    private DicePrevContainer(){
        dicePrevList = new ArrayList<>();
        for(int i = 0; i<6; i++){
            DicePrev dicePrev = new DicePrev(i +1);
            dicePrevList.add(dicePrev);
            this.getChildren().add(dicePrev);
        }

    }

    /**
     * @return the instance of the dice preview container
     */
    public static DicePrevContainer getDicePrevContainer(){
        if(dicePrevContainer == null)
            dicePrevContainer = new DicePrevContainer();
        return dicePrevContainer;
    }

    /**
     * Sets the click handler on the preview
     *
     * @param dicePrevHandler the handler to handler click to choose a value
     */
    public void setDicePrevHandler(EventHandler<MouseEvent> dicePrevHandler){
        for(int i = 0; i<6; i++){
            dicePrevList.get(i).setOnMouseClicked(dicePrevHandler);
        }
    }



}
