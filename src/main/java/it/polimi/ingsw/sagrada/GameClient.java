package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.gui.LoginGuiView;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameClient extends Application {

    public static void main(String[] args) {
        launch(LoginGuiView.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //path is relative to target because getResource is called

    }
}
