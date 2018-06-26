package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/*
public class NewRoundTrackView extends StackPane {

   private GUIManager guiManager;
   private ImageView imageView;
   private HBox hBox;
   private ArrayList<ArrayList<DiceView>> diceViewList;
   private final int MAX_ROUND = 10;
   private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";


    NewRoundTrackView() {
        this.guiManager = new GUIManager();
        imageView = new ImageView();
        hBox = new HBox();
        imageView.setImage(new Image(RoundTrackView.class.getResourceAsStream("/images/roundtrack.png"), guiManager.getFullWidthPixel(62), guiManager.getFullHeightPixel(20), true, false));
        this.getChildren().add(imageView);
        this.setMinSize(guiManager.getFullWidthPixel(62), guiManager.getFullHeightPixel(40));
        this.setAlignment(imageView, Pos.TOP_CENTER);
        this.diceViewList = new ArrayList<>();
        for (int i = 0; i< MAX_ROUND; i++){
            ArrayList<DiceView> roundDiceList = new ArrayList<>();
            diceViewList.add(roundDiceList);

        }
        this.getChildren().add(hBox);
        this.setAlignment(hBox,Pos.TOP_CENTER);

    }

    public void setDice(List<DiceView> diceViews, int currentRound) {
        for(int i = 0; i< diceViews.size(); i++){
            DiceView diceView = diceViews.get(i);
            diceViewList.get(currentRound).add(diceView);
            diceViewList.get(currentRound).get(i).setImage(new Image(RoundTrackView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(diceView.getColor(), diceView.getValue())), 50, 50, true, false));
            grid.add(diceView, currentRound, i);
        }
    }


}
*/