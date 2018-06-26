package it.polimi.ingsw.sagrada.gui.login;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * The Class NewLoginView.
 */
public class NewLoginView extends Application {


    /** The window height. */
    private double windowHeight;
    
    /** The window width. */
    private double windowWidth;

    /** The anchor pane. */
    private AnchorPane anchorPane;
    
    /** The password field. */
    private PasswordField passwordField;
    
    /** The username field. */
    private TextField usernameField;
    
    /** The login button. */
    private Button loginButton;
    
    /** The error field. */
    private TextField errorField;
    
    /** The rmi button. */
    private RadioButton rmiButton;
    
    /** The socket button. */
    private RadioButton socketButton;
    
    /** The dev label. */
    private Label devLabel;
    
    /** The primary stage. */
    private Stage primaryStage;


    /**
     * Instantiates a new new login view.
     */
    public NewLoginView() {
        windowWidth = GUIManager.getWindowWidth();
        windowHeight = GUIManager.getWindowHeight();
    }

    /**
     * Adds the login button listener.
     *
     * @param listener the listener
     */
    public void addLoginButtonListener(EventHandler<ActionEvent> listener) {
        loginButton.setOnAction(listener);
    }

    /**
     * Sets the error text.
     *
     * @param message the new error text
     */
    public void setErrorText(String message) {
        Platform.runLater(() -> {
            errorField.setText(message);
        });
    }


    /**
     * Checks if is credential correct.
     *
     * @return true, if is credential correct
     */
    public boolean isCredentialCorrect() {
        return (usernameField.getText().length() != 0 && passwordField.getText().length() != 0);
    }

    /**
     * Gets the selected communication.
     *
     * @return the selected communication
     */
    public String getSelectedCommunication() {
        if (socketButton.isSelected()) return "Socket";
        else return "RMI";
    }


    /**
     * Sets the radio group.
     */
    public void setRadioGroup() {
        ToggleGroup group = new ToggleGroup();
        socketButton.setToggleGroup(group);
        socketButton.setSelected(true);
        rmiButton.setToggleGroup(group);
    }

    /**
     * Gets the window.
     *
     * @return the window
     */
    public Stage getWindow() {
        return (Stage) loginButton.getScene().getWindow();
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return passwordField.getText();
    }


    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) {
        initialize();
        this.primaryStage = stage;
        primaryStage.setResizable(false);
        Platform.runLater(() -> setRadioGroup());
        primaryStage.setTitle("SagradaClient");
        primaryStage.setResizable(false);
        devLabel = new Label();
        devLabel.setText("Developed by: Daniele Comi, Valentina Deda, Valerio Colombo");
        AnchorPane.setBottomAnchor(devLabel, GUIManager.getHeightPixel(15));
        AnchorPane.setRightAnchor(devLabel, GUIManager.getWidthPixel(30));
        AnchorPane.setLeftAnchor(devLabel, GUIManager.getWidthPixel(30));
        devLabel.setWrapText(true);
        devLabel.setAlignment(Pos.CENTER);
        devLabel.setTextAlignment(TextAlignment.CENTER);
        anchorPane.getChildren().add(devLabel);
        loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setStyle("-fx-background-color: #d57322");
        loginButton.setMaxWidth(GUIManager.getWidthPixel(30));
        AnchorPane.setBottomAnchor(loginButton, GUIManager.getHeightPixel(23));
        AnchorPane.setLeftAnchor(loginButton, GUIManager.getWidthPixel(19));
        loginButton.setMinWidth(GUIManager.getWidthPixel(60));
        anchorPane.getChildren().add(loginButton);
        passwordField = new PasswordField();
        passwordField.setMinSize( GUIManager.getWidthPixel(60),GUIManager.getHeightPixel(4) );
        AnchorPane.setBottomAnchor(passwordField, GUIManager.getHeightPixel(37));
        AnchorPane.setLeftAnchor(passwordField, GUIManager.getWidthPixel(19));
        anchorPane.getChildren().add(passwordField);
        usernameField = new TextField();
        usernameField.setMaxWidth( GUIManager.getWidthPixel(55));
        AnchorPane.setBottomAnchor(usernameField, GUIManager.getHeightPixel(46));
        AnchorPane.setLeftAnchor(usernameField, GUIManager.getWidthPixel(19));
        usernameField.setMinSize( GUIManager.getWidthPixel(60),GUIManager.getHeightPixel(4) );
        anchorPane.getChildren().add(usernameField);
        socketButton = new RadioButton();
        AnchorPane.setBottomAnchor(socketButton, GUIManager.getHeightPixel(31));
        AnchorPane.setLeftAnchor(socketButton, GUIManager.getWidthPixel(19));
        anchorPane.getChildren().add(socketButton);
        socketButton.setText("Socket");
        rmiButton = new RadioButton();
        AnchorPane.setBottomAnchor(rmiButton, GUIManager.getHeightPixel(31));
        AnchorPane.setLeftAnchor(rmiButton, GUIManager.getWidthPixel(35));
        rmiButton.setText("RMI");
        anchorPane.getChildren().add(rmiButton);
        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initialize.
     */
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
