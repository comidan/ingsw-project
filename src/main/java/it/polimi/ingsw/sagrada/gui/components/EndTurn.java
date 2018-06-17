package it.polimi.ingsw.sagrada.gui.components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class EndTurn extends StackPane {

    Button button;

    public EndTurn(){
        ImageView buttonBackground = new ImageView(new Image(EndTurn.class.getResourceAsStream("/images/button5.png")));
        button = new Button("END TURN");
        button.setStyle("-fx-background-color: transparent;");
        button.setMouseTransparent(true);
        buttonBackground.setMouseTransparent(true);
        this.getChildren().addAll(buttonBackground, button);
        prefWidthProperty().bind(button.prefWidthProperty());
        prefHeightProperty().bind(button.prefHeightProperty());
    }

    public void setEndTurnHandler(EventHandler<MouseEvent> endTurnEventHandler){
        setOnMouseClicked(endTurnEventHandler);
    }

}
