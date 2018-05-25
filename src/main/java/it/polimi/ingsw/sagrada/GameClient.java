package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.gui.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //path is relative to target because getResource is called
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/loginGui.fxml"));
        Parent root = loader.load();
        GuiController guiController = loader.getController();
        guiController.initLoginGui();
        primaryStage.setTitle("SagradaClient");
        primaryStage.setScene(new Scene(root, 590, 776));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
