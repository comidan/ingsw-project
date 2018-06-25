package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FrameView extends StackPane {

    public FrameView(){
        ImageView imageView = new ImageView(new Image(FrameView.class.getResourceAsStream("/images/frame.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(GUIManager.getGameWidthPixel(30));
        getChildren().add(imageView);
        setAlignment(imageView, Pos.BOTTOM_CENTER);
    }

    public void addWindowToFrame(WindowView windowView){

        setAlignment(windowView, Pos.BOTTOM_CENTER);
        setMargin(windowView, new Insets(GUIManager.getGameHeightPixel(34),GUIManager.getGameWidthPixel(2),GUIManager.getGameHeightPixel(1),GUIManager.getGameWidthPixel(2)));
        getChildren().add(windowView);
    }


}
