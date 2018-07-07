package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FrameView extends StackPane {


    /**
     * Instantiates a new frame view
     */
    public FrameView(){
        ImageView imageView = new ImageView(new Image(FrameView.class.getResourceAsStream("/images/gameGuiImages/frame.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(GUIManager.getGameWidthPixel(30));
        getChildren().add(imageView);
        setAlignment(imageView, Pos.BOTTOM_CENTER);
    }

    /**
     * sets the window view inside the frame image
     * @param windowView the window view
     */
    public void addWindowToFrame(WindowView windowView){
        setAlignment(windowView, Pos.BOTTOM_CENTER);
        setMargin(windowView, new Insets(GUIManager.getGameHeightPixel(36),GUIManager.getGameWidthPixel(2),GUIManager.getGameHeightPixel(4),GUIManager.getGameWidthPixel(2)));
        getChildren().add(windowView);
    }
}
