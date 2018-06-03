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
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGuiView extends Application {

    private static final Logger LOGGER = Logger.getLogger(LoginGuiView.class.getName());

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
        windowWidth = GUIManager.getWindowWidth();
        windowHeight = GUIManager.getWindowHeight();
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
        AnchorPane.setBottomAnchor(dev, getHeightPixel(15));
        dev.setWrapText(true);
        loginButton = (Button) scene.lookup("#loginButton");
        AnchorPane.setBottomAnchor(loginButton, getHeightPixel(23));
        loginButton.setLayoutX(getHeightPixel(15));
        passwordField = (PasswordField) scene.lookup("#passwordField");
        AnchorPane.setBottomAnchor(passwordField, getHeightPixel(37));
        usernameField = (TextField) scene.lookup("#usernameField");
        AnchorPane.setBottomAnchor(usernameField, getHeightPixel(46));
        socketRadioButton = (RadioButton) scene.lookup("#socketRadioButton");
        AnchorPane.setBottomAnchor(socketRadioButton, getHeightPixel(31));
        rmiRadiobutton = (RadioButton) scene.lookup("#rmiRadiobutton");
        AnchorPane.setBottomAnchor(rmiRadiobutton, getHeightPixel(31));

        window.show();
    }

    public LobbyGuiView changeScene() {
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("/templates/MatchLobbyGui.fxml"));
        Parent lobby = null;
        try {
            lobby = loaderLobby.load();
            window = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(lobby, windowWidth, windowHeight);
            ImageView image = (ImageView) scene.lookup("#background");
            image.setFitHeight(windowHeight);
            image.setFitWidth(windowWidth);
            image.setPreserveRatio(true);
            AnchorPane anchor = (AnchorPane) scene.lookup("#anchorPane");
            Label gameLabel = (Label) scene.lookup("#game");
            gameLabel.setWrapText(true);
            AnchorPane.setBottomAnchor(gameLabel, getHeightPixel(13));
            ImageView dice1 = (ImageView) scene.lookup("#dice1");
            AnchorPane.setBottomAnchor(dice1, getHeightPixel(62));
            Label player1 = (Label) scene.lookup("#firstPlayer");
            AnchorPane.setBottomAnchor(player1, getHeightPixel(65));
            ImageView dice2 = (ImageView) scene.lookup("#dice2");
            AnchorPane.setBottomAnchor(dice2, getHeightPixel(47));
            Label player2 = (Label) scene.lookup("#secondPlayer");
            AnchorPane.setBottomAnchor(player2, getHeightPixel(50));
            ImageView dice3 = (ImageView) scene.lookup("#dice3");
            AnchorPane.setBottomAnchor(dice3, getHeightPixel(32));
            Label player3 = (Label) scene.lookup("#thirdPlayer");
            AnchorPane.setBottomAnchor(player3, getHeightPixel(35));
            ImageView dice4 = (ImageView) scene.lookup("#dice4");
            AnchorPane.setBottomAnchor(dice4, getHeightPixel(17));
            Label player4 = (Label) scene.lookup("#fourthPlayer");
            AnchorPane.setBottomAnchor(player4, getHeightPixel(20));
            Label timerLabel = (Label) scene.lookup("#timer");
            AnchorPane.setBottomAnchor(timerLabel, getHeightPixel(10));
            window.setScene(scene);


            return loaderLobby.getController();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, () -> e.getMessage());
            return null;
        }
    }

    private double getHeightPixel(int perc) {
        return (perc * windowHeight / 100);
    }

}
