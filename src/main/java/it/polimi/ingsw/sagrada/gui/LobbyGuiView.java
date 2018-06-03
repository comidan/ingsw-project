package it.polimi.ingsw.sagrada.gui;

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

public class LobbyGuiView {

    private static final Logger LOGGER = Logger.getLogger(LobbyGuiView.class.getName());

    @FXML
    private Label firstPlayer;
    @FXML
    private Label secondPlayer;
    @FXML
    private Label thirdPlayer;
    @FXML
    private Label fourthPlayer;
    @FXML
    private Label timer;

    private List<String> playerShown = new ArrayList<>();
    private static Stage stage;

    public void setPlayer(String username) {
        System.out.println("Setting player");
        playerShown.add(username);
        int position = playerShown.indexOf(username);
        System.out.println(position);
        switch (position) {
            case 0 : setFirstPlayer(username); break;
            case 1 : setSecondPlayer(username); break;
            case 2 : setThirdPlayer(username); break;
            case 3:  setFourthPlayer(username); break;
            default: break;
        }
    }

    public void removePlayer(String username) {
        int position = playerShown.indexOf(username);
        playerShown.remove(position);
        switch (position) {
            case 0 : setFirstPlayer("Waiting player..."); break;
            case 1 : setSecondPlayer("Waiting player..."); break;
            case 2 : setThirdPlayer("Waiting player..."); break;
            case 3:  setFourthPlayer("Waiting player..."); break;
            default: break;
        }
    }

    private void setFirstPlayer(String message) {
        Platform.runLater(() -> firstPlayer.setText(message));
    }

    private void setSecondPlayer(String message) {
        Platform.runLater(() -> secondPlayer.setText(message));
    }

    private void setThirdPlayer(String message) {
        Platform.runLater(() -> thirdPlayer.setText(message));
    }

    private void setFourthPlayer(String message) {
        Platform.runLater(() -> fourthPlayer.setText(message));
    }

    public void setTimer(String message) {
        Platform.runLater(() -> timer.setText(message));
    }

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
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, () -> e.getMessage());
            return null;
        }
    }

    private static double getHeightPixel(int perc) {
        return (perc * GUIManager.getWindowHeight() / 100);
    }

    public void closeWindow() {
        stage.hide();
    }

    public Stage getStage() {
        return (Stage) timer.getScene().getWindow();
    }
}
