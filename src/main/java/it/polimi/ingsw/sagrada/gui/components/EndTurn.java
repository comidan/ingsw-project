package it.polimi.ingsw.sagrada.gui.components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * The Class EndTurn.
 *
 * @author Valentina
 */

public class EndTurn extends StackPane {

    /**
     * instantiates a new endTurn
     */
    public EndTurn(){
        ImageView buttonBackground = new ImageView(new Image(EndTurn.class.getResourceAsStream("/images/gameGuiImages/button5.png")));
        Button button = new Button("END TURN");
        button.setStyle("-fx-background-color: transparent;");
        button.setMouseTransparent(true);
        buttonBackground.setMouseTransparent(true);
        this.getChildren().addAll(buttonBackground, button);
        prefWidthProperty().bind(button.prefWidthProperty());
        prefHeightProperty().bind(button.prefHeightProperty());
    }


    /**
     * sets the handler to handle click on the end turn button
     * @param endTurnEventHandler the handler
     */
    public void setEndTurnHandler(EventHandler<MouseEvent> endTurnEventHandler){
        setOnMouseClicked(endTurnEventHandler);
    }

}
