package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiView;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowImage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewLoginView extends Application {


    private double windowHeight;
    private double windowWidth;

    private AnchorPane anchorPane;
    private PasswordField passwordField;
    private TextField usernameField;
    private Button loginButton;
    private TextField errorField;
    private RadioButton rmiButton;
    private RadioButton socketButton;
    private Label devLabel;
    private Stage primaryStage;


    public NewLoginView() {
        windowWidth = GUIManager.getWindowWidth();
        windowHeight = GUIManager.getWindowHeight();
    }

    public void addLoginButtonListener(EventHandler<ActionEvent> listener) {
        loginButton.setOnAction(listener);
    }

    public void setErrorText(String message) {
        Platform.runLater(() -> {
            errorField.setText(message);
        });
    }


    public boolean isCredentialCorrect() {
        return (usernameField.getText().length() != 0 && passwordField.getText().length() != 0);
    }

    public String getSelectedCommunication() {
        if (socketButton.isSelected()) return "Socket";
        else return "RMI";
    }


    public void setRadioGroup() {
        ToggleGroup group = new ToggleGroup();
        socketButton.setToggleGroup(group);
        socketButton.setSelected(true);
        rmiButton.setToggleGroup(group);
    }

    public Stage getWindow() {
        return (Stage) loginButton.getScene().getWindow();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }


    @Override
    public void start(Stage stage) {
        initialize();
        this.primaryStage = stage;
        primaryStage.setResizable(false);
        Platform.runLater(() -> this.setRadioGroup());
        primaryStage.setTitle("SagradaClient");
        primaryStage.setResizable(false);
        devLabel = new Label();
        devLabel.setText("Developed by: Daniele Comi, Valentina Deda, Valerio Colombo");
        anchorPane.setBottomAnchor(devLabel, GUIManager.getHeightPixel(15));
        anchorPane.setRightAnchor(devLabel, GUIManager.getWidthPixel(30));
        anchorPane.setLeftAnchor(devLabel, GUIManager.getWidthPixel(30));
        devLabel.setWrapText(true);
        devLabel.setAlignment(Pos.CENTER);
        devLabel.setTextAlignment(TextAlignment.CENTER);
        anchorPane.getChildren().add(devLabel);
        loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setStyle("-fx-background-color: #d57322");
        loginButton.setMaxWidth(GUIManager.getWidthPixel(30));
        anchorPane.setBottomAnchor(loginButton, GUIManager.getHeightPixel(23));
        anchorPane.setLeftAnchor(loginButton, GUIManager.getWidthPixel(19));
        loginButton.setMinWidth(GUIManager.getWidthPixel(60));
        anchorPane.getChildren().add(loginButton);
        passwordField = new PasswordField();
        passwordField.setMinSize( GUIManager.getWidthPixel(60),GUIManager.getHeightPixel(4) );
        anchorPane.setBottomAnchor(passwordField, GUIManager.getHeightPixel(37));
        anchorPane.setLeftAnchor(passwordField, GUIManager.getWidthPixel(19));
        anchorPane.getChildren().add(passwordField);
        usernameField = new TextField();
        usernameField.setMaxWidth( GUIManager.getWidthPixel(55));
        anchorPane.setBottomAnchor(usernameField, GUIManager.getHeightPixel(46));
        anchorPane.setLeftAnchor(usernameField, GUIManager.getWidthPixel(19));
        usernameField.setMinSize( GUIManager.getWidthPixel(60),GUIManager.getHeightPixel(4) );
        anchorPane.getChildren().add(usernameField);
        socketButton = new RadioButton();
        anchorPane.setBottomAnchor(socketButton, GUIManager.getHeightPixel(31));
        anchorPane.setLeftAnchor(socketButton, GUIManager.getWidthPixel(19));
        anchorPane.getChildren().add(socketButton);
        socketButton.setText("Socket");
        rmiButton = new RadioButton();
        anchorPane.setBottomAnchor(rmiButton, GUIManager.getHeightPixel(31));
        anchorPane.setLeftAnchor(rmiButton, GUIManager.getWidthPixel(35));
        rmiButton.setText("RMI");
        anchorPane.getChildren().add(rmiButton);
        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialize() {

        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/LogInGui.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        windowHeight = GUIManager.getWindowHeight();
        windowWidth = GUIManager.getWindowWidth();

        anchorPane.resize(windowWidth, windowHeight);

    }


}
