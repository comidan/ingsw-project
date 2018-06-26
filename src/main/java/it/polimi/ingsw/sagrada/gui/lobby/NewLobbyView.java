package it.polimi.ingsw.sagrada.gui.lobby;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewLobbyView extends Application {

    private static final Logger LOGGER = Logger.getLogger(LobbyGuiView.class.getName());
    private static final String WAITING_PLAYER = "Waiting player...";

    private Label firstPlayer;
    private Label secondPlayer;
    private Label thirdPlayer;
    private Label fourthPlayer;
    private Label timer;
    private double windowWidth;
    private double windowHeight;

    private List<String> playerShown = new ArrayList<>();
    private Stage stage;

    private AnchorPane anchorPane;

    private static NewLobbyView newLobbyView;


    private NewLobbyView() {
        windowWidth = GUIManager.getWindowWidth();
        windowHeight = GUIManager.getWindowHeight();
    }

    public NewLobbyView getInstance(){
        if(newLobbyView == null)
            newLobbyView = new NewLobbyView();
        return newLobbyView;
    }

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

    public void init(Stage stage) {
            initializeScene();
            Scene scene = new Scene(anchorPane, GUIManager.getWindowWidth(), GUIManager.getWindowHeight());
            Label gameLabel = new Label();
            gameLabel.setWrapText(true);
            gameLabel.setText("Game will start in");
            AnchorPane.setBottomAnchor(gameLabel, GUIManager.getHeightPixel(13));
            ImageView dice1 = new ImageView();
            dice1.setImage(new Image("/images/DiceImages/Dice1B.png", 50, 50, true, true));
            AnchorPane.setBottomAnchor(dice1, GUIManager.getHeightPixel(62));
            AnchorPane.setLeftAnchor(dice1, GUIManager.getWidthPixel(20));
            firstPlayer = new Label();
            firstPlayer.setText("Waiting player...");
            AnchorPane.setLeftAnchor(firstPlayer, GUIManager.getWidthPixel(30));
            AnchorPane.setBottomAnchor(firstPlayer, GUIManager.getHeightPixel(65));
            ImageView dice2 = new ImageView();
            dice2.setImage(new Image("/images/DiceImages/Dice2Y.png", 50, 50, true, true));
            AnchorPane.setBottomAnchor(dice2, GUIManager.getHeightPixel(47));
            AnchorPane.setLeftAnchor(dice2, GUIManager.getWidthPixel(20));
            secondPlayer = new Label();
            secondPlayer.setText("Waiting player...");
            AnchorPane.setLeftAnchor(secondPlayer, GUIManager.getWidthPixel(30));
            AnchorPane.setBottomAnchor(secondPlayer, GUIManager.getHeightPixel(50));
            ImageView dice3 = new ImageView();
            thirdPlayer = new Label();
            thirdPlayer.setText("Waiting player...");
            AnchorPane.setBottomAnchor(thirdPlayer, GUIManager.getHeightPixel(32));
            AnchorPane.setLeftAnchor(thirdPlayer, GUIManager.getHeightPixel(30));
            dice3.setImage(new Image("/images/DiceImages/Dice3P.png", 50, 50, true, true));
            AnchorPane.setLeftAnchor(dice3,  GUIManager.getWidthPixel(20));
            ImageView dice4 = new ImageView();
            dice4.setImage(new Image("/images/DiceImages/Dice4G.png", 50, 50, true, true));
            AnchorPane.setBottomAnchor(dice4, GUIManager.getHeightPixel(17));
            AnchorPane.setLeftAnchor(dice4, GUIManager.getWidthPixel(20));
            fourthPlayer = new Label();
            fourthPlayer.setText("Waiting player...");
            AnchorPane.setLeftAnchor(fourthPlayer, GUIManager.getWidthPixel(30));
            AnchorPane.setBottomAnchor(fourthPlayer, GUIManager.getHeightPixel(20));
            Label timerLabel = new Label();
            AnchorPane.setBottomAnchor(timerLabel, GUIManager.getHeightPixel(10));
            anchorPane.getChildren().addAll(firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, dice1, dice2, dice3, dice4);
            this.stage = stage;
            stage.setScene(scene);


    }


    private void initializeScene() {

        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "/images/MatchLobbyBackground.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        windowHeight = GUIManager.getWindowHeight();
        windowWidth = GUIManager.getWindowWidth();

        anchorPane.resize(windowWidth, windowHeight);

    }

    public void start(Stage stage){
        initializeScene();
        init(stage);
        stage.show();
    }


    public Stage getStage() {
        return (Stage) timer.getScene().getWindow();
    }
}
