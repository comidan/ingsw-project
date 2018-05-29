package it.polimi.ingsw.sagrada.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginGuiView extends Application {
    @FXML
    private RadioButton socketRadioButton;
    @FXML
    private RadioButton rmiRadiobutton;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label errorText;

    private Stage window;

    public void addLoginButtonListener(EventHandler<ActionEvent> listener) {
        loginButton.setOnAction(listener);
    }

    public void setErrorText(String message) {
        Platform.runLater(() -> {
            errorText.setText(message);
        });
    }

    public boolean isCredentialCorrect() {
        return (usernameField.getText().length()!=0 && passwordField.getText().length()!=0);
    }

    public String getSelectedCommunication() {
        if(socketRadioButton.isSelected()) return "Socket";
        else return "RMI";
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setRadioGroup() {
        ToggleGroup group = new ToggleGroup();
        socketRadioButton.setToggleGroup(group);
        socketRadioButton.setSelected(true);
        rmiRadiobutton.setToggleGroup(group);
    }

    public Stage getWindow() {
        return (Stage)loginButton.getScene().getWindow();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;

        //Login GUI
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/templates/LoginGui.fxml"));
        Parent root = loaderLogin.load();
        LoginGuiView loginGuiView = loaderLogin.getController();
        new LoginGuiController(loginGuiView);
        loginGuiView.setRadioGroup();

        window.setTitle("SagradaClient");
        window.setResizable(false);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        int windowWidth = width * 590 / 1920;
        int windowHeight = height * 776 / 1080;
        window.setScene(new Scene(root, windowWidth, windowHeight));
        window.show();
    }

    public LobbyGuiView changeScene() {
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("/templates/MatchLobbyGui.fxml"));
        Parent lobby = null;
        try {
            lobby = loaderLobby.load();
            window = (Stage) loginButton.getScene().getWindow();
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            int windowWidth = width * 590 / 1920;
            int windowHeight = height * 776 / 1080;
            window.setScene(new Scene(lobby, windowWidth, windowHeight));
            return loaderLobby.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
