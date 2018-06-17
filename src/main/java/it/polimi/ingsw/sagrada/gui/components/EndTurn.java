package it.polimi.ingsw.sagrada.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import jdk.nashorn.internal.ir.EmptyNode;

public class EndTurn extends StackPane {

    Button button;

    public EndTurn(){
        ImageView buttonBackground = new ImageView(new Image("/images/button5.png"));
        button = new Button("END TURN");
        button.setStyle("-fx-background-color: transparent;");
        this.getChildren().addAll(buttonBackground, button);

    }

    public void setEndTurnHandler(EventHandler<MouseEvent> endTurnEventHandler){
        button.setOnMouseClicked(endTurnEventHandler);
    }

}
