package it.polimi.ingsw.sagrada.gui;


import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardBoard extends GridPane {
    List<ToolCardView> toolCardViewList;
    List<ObjectiveCardView> objectiveCardViewList;
    Resizer resizer;
    private List<Integer> usedToolId;
    private List<Integer> usedObjId;

    public CardBoard() {
        usedToolId = new ArrayList<>();
        usedObjId = new ArrayList<>();
        toolCardViewList = new ArrayList<>();
        objectiveCardViewList = new ArrayList<>();
        resizer = new Resizer();
        Random rand = new Random();
        int id = 0;
        for (int i = 1; i < 4; i++) {
            if(usedToolId.size()==0) {
                id = rand.nextInt(6)+1;
            }
            else{
            while(usedToolId.contains(id))
                id = rand.nextInt(6)+1;
            }
            usedToolId.add(id);
            ToolCardView toolCardView = new ToolCardView(id);
            this.add(toolCardView, i, 1);
            toolCardViewList.add(toolCardView);

        }
        id = 0;
        for (int i = 1; i < 4; i++) {

            if(usedToolId.size()==0) {
                id = 6 + rand.nextInt(4);
            }
            else{
                while(usedToolId.contains(id))
                    id = 6 + rand.nextInt(4);
            }
            usedToolId.add(id);
            ObjectiveCardView objectiveCardView = new ObjectiveCardView(id);
            this.add(objectiveCardView, i, 2);
            objectiveCardViewList.add(objectiveCardView);

        }



    }

    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        toolCardViewList.forEach(toolCardView -> {toolCardView.setToolClickHandler(toolClickHandler);});

        }


        public void setPrivateObjective(){

            Random rand = new Random();
            PrivateObjectiveView privateObjectiveView = new PrivateObjectiveView(rand.nextInt(5)+1);

        }



}
