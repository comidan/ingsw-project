package it.polimi.ingsw.sagrada.gui.score;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreLobbyView extends Application{
    Stage stage;
    AnchorPane anchorPane;
    Stage primaryStage;

    private Label firstPlayer;
    private Label secondPlayer;
    private Label thirdPlayer;
    private Label fourthPlayer;

    private List<String> playerShown = new ArrayList<>();


    /** The window height. */
    private double windowHeight;

    /** The window width. */
    private double windowWidth;

    public void initialize(Stage stage){

        this.anchorPane = new AnchorPane();
        primaryStage = stage;
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MatchLobbyBackground.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );


        windowHeight = GUIManager.getWindowHeight();
        windowWidth = GUIManager.getWindowWidth();
        anchorPane.resize(windowWidth, windowHeight);
        firstPlayer = new Label();
        secondPlayer = new Label();
        thirdPlayer = new Label();
        fourthPlayer = new Label();
        ImageView dice1 = new ImageView();
        dice1.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice1B.png"), 50, 50, true, true));
        anchorPane.setBottomAnchor(dice1, GUIManager.getHeightPixel(62));
        anchorPane.setLeftAnchor(dice1, GUIManager.getWidthPixel(20));
        anchorPane.setLeftAnchor(firstPlayer, GUIManager.getWidthPixel(30));
        anchorPane.setBottomAnchor(firstPlayer, GUIManager.getHeightPixel(65));
        ImageView dice2 = new ImageView();
        dice2.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice2Y.png"), 50, 50, true, true));
        anchorPane.setBottomAnchor(dice2, GUIManager.getHeightPixel(47));
        anchorPane.setLeftAnchor(dice2, GUIManager.getWidthPixel(20));
        anchorPane.setLeftAnchor(secondPlayer, GUIManager.getWidthPixel(30));
        anchorPane.setBottomAnchor(secondPlayer, GUIManager.getHeightPixel(50));
        ImageView dice3 = new ImageView();
        dice3.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice3P.png"), 50, 50, true, true));
        anchorPane.setBottomAnchor(dice3, GUIManager.getHeightPixel(32));
        anchorPane.setBottomAnchor(thirdPlayer, GUIManager.getHeightPixel(35));
        anchorPane.setLeftAnchor(dice3, GUIManager.getWidthPixel(20));
        anchorPane.setLeftAnchor(thirdPlayer, GUIManager.getWidthPixel(30));
        ImageView dice4 = new ImageView();
        dice4.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice4G.png"), 50, 50, true, true));
        anchorPane.setBottomAnchor(dice4, GUIManager.getHeightPixel(17));
        anchorPane.setBottomAnchor(fourthPlayer, GUIManager.getHeightPixel(20));
        anchorPane.setLeftAnchor(dice4, GUIManager.getWidthPixel(20));
        anchorPane.setLeftAnchor(fourthPlayer, GUIManager.getWidthPixel(30));
        anchorPane.getChildren().addAll(dice1, firstPlayer, dice2, secondPlayer, dice3, thirdPlayer, dice4, fourthPlayer);
        firstPlayer.setText("-");
        secondPlayer.setText("-");
        thirdPlayer.setText("-");
        fourthPlayer.setText("-");
    }

    public void start(Stage stage){
        initialize(stage);
        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void setScores(HashMap<String, Integer> playerOrder){
        for (Map.Entry<String, Integer> entry : playerOrder.entrySet()) {
            String username = entry.getKey();
            int score = entry.getValue();
            setPlayer(username, score);
        }
    }

    public void setPlayer(String username, int score) {
        playerShown.add(username);
        int position = playerShown.indexOf(username);
        switch (position) {
            case 0 : setFirstPlayer(username, score); break;
            case 1 : setSecondPlayer(username, score); break;
            case 2 : setThirdPlayer(username, score); break;
            case 3:  setFourthPlayer(username, score); break;
            default: break;
        }
    }



    private void setFirstPlayer(String message, int score) {
        Platform.runLater(() -> firstPlayer.setText(message + Integer.toString(score)));
    }

    private void setSecondPlayer(String message, int score) {
        Platform.runLater(() -> secondPlayer.setText(message + Integer.toString(score)));
    }

    private void setThirdPlayer(String message, int score) {
        Platform.runLater(() -> thirdPlayer.setText(message + Integer.toString(score)));
    }

    private void setFourthPlayer(String message, int score) {
        Platform.runLater(() -> fourthPlayer.setText(message + Integer.toString(score)));
    }


}
