package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.gui.LoginGuiController;
import it.polimi.ingsw.sagrada.gui.LoginGuiView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameClient extends Application {
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //path is relative to target because getResource is called
        this.window = primaryStage;

        //Login GUI
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/templates/LoginGui.fxml"));
        Parent root = loaderLogin.load();
        LoginGuiView loginGuiView = loaderLogin.getController();
        new LoginGuiController(loginGuiView, window);
        loginGuiView.setRadioGroup();

        window.setTitle("SagradaClient");
        window.setResizable(false);
        window.setScene(new Scene(root, 590, 776));
        window.show();
    }
}
