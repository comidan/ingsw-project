package it.polimi.ingsw.sagrada.gui.lobby;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class LobbyGuiView.
 */
public class LobbyGuiView {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LobbyGuiView.class.getName());
    
    /** The Constant WAITING_PLAYER. */
    private static final String WAITING_PLAYER = "Waiting player...";

    /** The first player. */
    @FXML
    private Label firstPlayer;
    
    /** The second player. */
    @FXML
    private Label secondPlayer;
    
    /** The third player. */
    @FXML
    private Label thirdPlayer;
    
    /** The fourth player. */
    @FXML
    private Label fourthPlayer;
    
    /** The timer. */
    @FXML
    private Label timer;

    /** The player shown. */
    private List<String> playerShown = new ArrayList<>();
    
    /** The stage. */
    private static Stage stage;

    /**
     * Sets the player.
     *
     * @param username the new player
     */
    public void setPlayer(String username) {
        playerShown.add(username);
        int position = playerShown.indexOf(username);
        switch (position) {
            case 0 : setFirstPlayer(username); break;
            case 1 : setSecondPlayer(username); break;
            case 2 : setThirdPlayer(username); break;
            case 3:  setFourthPlayer(username); break;
            default: break;
        }
    }

    /**
     * Removes the player.
     *
     * @param username the username
     */
    public void removePlayer(String username) {
        int position = playerShown.indexOf(username);
        playerShown.remove(position);
        switch (position) {
            case 0 : setFirstPlayer(WAITING_PLAYER); break;
            case 1 : setSecondPlayer(WAITING_PLAYER); break;
            case 2 : setThirdPlayer(WAITING_PLAYER); break;
            case 3:  setFourthPlayer(WAITING_PLAYER); break;
            default: break;
        }
    }

    /**
     * Sets the first player.
     *
     * @param message the new first player
     */
    private void setFirstPlayer(String message) {
        Platform.runLater(() -> firstPlayer.setText(message));
    }

    /**
     * Sets the second player.
     *
     * @param message the new second player
     */
    private void setSecondPlayer(String message) {
        Platform.runLater(() -> secondPlayer.setText(message));
    }

    /**
     * Sets the third player.
     *
     * @param message the new third player
     */
    private void setThirdPlayer(String message) {
        Platform.runLater(() -> thirdPlayer.setText(message));
    }

    /**
     * Sets the fourth player.
     *
     * @param message the new fourth player
     */
    private void setFourthPlayer(String message) {
        Platform.runLater(() -> fourthPlayer.setText(message));
    }

    /**
     * Sets the timer.
     *
     * @param message the new timer
     */
    public void setTimer(String message) {
        Platform.runLater(() -> timer.setText(message));
    }

    /**
     * Inits the.
     *
     * @param stage the stage
     * @return the lobby gui view
     */
    public static LobbyGuiView init(Stage stage) {
        try {
            FXMLLoader loaderLobby = new FXMLLoader(LobbyGuiView.class.getResource("/templates/MatchLobbyGui.fxml"));
            Parent lobby = loaderLobby.load();
            Scene scene = new Scene(lobby, GUIManager.getWindowWidth(), GUIManager.getWindowHeight());
            ImageView image = (ImageView) scene.lookup("#background");
            image.setFitHeight(GUIManager.getWindowHeight());
            image.setFitWidth(GUIManager.getWindowWidth());
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
            LobbyGuiView.stage = stage;
            stage.setScene(scene);
            return loaderLobby.getController();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
            return null;
        }
    }

    /**
     * Gets the height pixel.
     *
     * @param perc the perc
     * @return the height pixel
     */
    private static double getHeightPixel(int perc) {
        return (perc * GUIManager.getWindowHeight() / 100);
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return (Stage) timer.getScene().getWindow();
    }
}
