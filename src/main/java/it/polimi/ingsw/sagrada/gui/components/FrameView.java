package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FrameView extends StackPane {
    private ImageView imageView;
    private GUIManager guiManager;


    public FrameView(){
        guiManager = new GUIManager();
        imageView = new ImageView();
        imageView.setImage(new Image(("/images/frame.png"), guiManager.getFullWidthPixel(30), guiManager.getFullHeightPixel(65), true, false));
        getChildren().add(imageView);
        setAlignment(imageView, Pos.BOTTOM_CENTER);
    }

    public void addWindowToFrame(WindowView windowView){

        setAlignment(windowView, Pos.BOTTOM_CENTER);
        this.setMargin(windowView, new Insets(guiManager.getFullHeightPixel(31),guiManager.getFullWidthPixel(2.3),guiManager.getFullHeightPixel(3),guiManager.getFullWidthPixel(2.3)));
        getChildren().add(windowView);
    }


}
