package it.polimi.ingsw.sagrada.gui.login;

import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class LoginGuiView.
 */
public class LoginGuiView extends Application {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LoginGuiView.class.getName());

    /** The socket radio button. */
    @FXML
    private RadioButton socketRadioButton;
    
    /** The window height. */
    private double windowHeight;
    
    /** The window width. */
    private double windowWidth;

    /** The rmi radiobutton. */
    @FXML
    private RadioButton rmiRadiobutton;
    
    /** The login button. */
    @FXML
    private Button loginButton;
    
    /** The password field. */
    @FXML
    private PasswordField passwordField;
    
    /** The username field. */
    @FXML
    private TextField usernameField;
    
    /** The error text. */
    @FXML
    private Label errorText;

    /** The window. */
    private Stage window;

    /**
     * Instantiates a new login gui view.
     */
    public LoginGuiView() {
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
            errorText.setText(message);
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
        if (socketRadioButton.isSelected()) return "Socket";
        else return "RMI";
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

    /**
     * Sets the radio group.
     */
    public void setRadioGroup() {
        ToggleGroup group = new ToggleGroup();
        socketRadioButton.setToggleGroup(group);
        socketRadioButton.setSelected(true);
        rmiRadiobutton.setToggleGroup(group);
    }

    /**
     * Gets the window.
     *
     * @return the window
     */
    public Stage getWindow() {
        return (Stage) loginButton.getScene().getWindow();
    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;

        //Login GUI
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/templates/LoginGui.fxml"));
        Parent root = loaderLogin.load();
        LoginGuiView loginGuiView = loaderLogin.getController();
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
        new LoginGuiAdapter(loginGuiView);
    }

    /**
     * Change scene.
     *
     * @return the lobby gui view
     */
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

    /**
     * Gets the height pixel.
     *
     * @param perc the perc
     * @return the height pixel
     */
    private double getHeightPixel(int perc) {
        return (perc * windowHeight / 100);
    }

}
