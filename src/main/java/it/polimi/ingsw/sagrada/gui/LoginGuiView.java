package it.polimi.ingsw.sagrada.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginGuiView extends Application {
    @FXML
    private RadioButton socketRadioButton;
    private double windowHeight;
    private double windowWidth;

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

    public LoginGuiView() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int height = gd.getDisplayMode().getHeight();
        this.windowHeight = height * 0.7;
        this.windowWidth = windowHeight * 0.76;
    }

    public void addLoginButtonListener(EventHandler<ActionEvent> listener) {
        loginButton.setOnAction(listener);
    }

    public void setErrorText(String message) {
        Platform.runLater(() -> {
            errorText.setText(message);
        });
    }

    public boolean isCredentialCorrect() {
        return (usernameField.getText().length() != 0 && passwordField.getText().length() != 0);
    }

    public String getSelectedCommunication() {
        if (socketRadioButton.isSelected()) return "Socket";
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
        return (Stage) loginButton.getScene().getWindow();
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
        Scene scene = new Scene(root, windowWidth, windowHeight);
        window.setScene(scene);
        ImageView image = (ImageView) scene.lookup("#background");
        image.setFitHeight(windowHeight);
        image.setFitWidth(windowWidth);
        image.setPreserveRatio(true);
        AnchorPane anchor = (AnchorPane) scene.lookup("#anchorPane");
        Label dev = (Label) scene.lookup("#developers");
        anchor.setBottomAnchor(dev, getHeightPixel(15));
        dev.setWrapText(true);
        Button loginButton = (Button) scene.lookup("#loginButton");
        anchor.setBottomAnchor(loginButton, getHeightPixel(23));
        PasswordField passwordField = (PasswordField) scene.lookup("#passwordField");
        anchor.setBottomAnchor(passwordField, getHeightPixel(37));
        TextField usernameField = (TextField) scene.lookup("#usernameField");
        anchor.setBottomAnchor(usernameField, getHeightPixel(46));
        RadioButton socketRadioButton = (RadioButton) scene.lookup("#socketRadioButton");
        anchor.setBottomAnchor(socketRadioButton, getHeightPixel(31));
        RadioButton rmiRadiobutton = (RadioButton) scene.lookup("#rmiRadiobutton");
        anchor.setBottomAnchor(rmiRadiobutton, getHeightPixel(31));


        window.show();
    }

    public LobbyGuiView changeScene() {
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("/templates/MatchLobbyGui.fxml"));
        Parent lobby = null;
        try {
            lobby = loaderLobby.load();
            window = (Stage) loginButton.getScene().getWindow();
            window.setScene(new Scene(lobby, windowWidth, windowHeight));
            return loaderLobby.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private double getHeightPixel(int perc) {
        return (perc * windowHeight / 100);
    }

}
