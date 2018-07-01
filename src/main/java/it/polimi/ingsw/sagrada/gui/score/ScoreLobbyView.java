package it.polimi.ingsw.sagrada.gui.score;

import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreLobbyView extends Application{

    private static ScoreLobbyView scoreLobbyView = null;

    private Map<String, Integer> ranking;
    private AnchorPane anchorPane;
    private Stage primaryStage;
    private Label firstPlayer;
    private Label secondPlayer;
    private Label thirdPlayer;
    private Label fourthPlayer;
    /** The window height. */
    private double windowHeight;
    private Label title;

    /** The window width. */
    private double windowWidth;

    private final List<String> playerShown = new ArrayList<>();

    private ScoreLobbyView(Map<String, Integer> ranking) {
        this.ranking = ranking;
    }

    public void initialize(){

        this.anchorPane = new AnchorPane();
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
        setTitle();
        anchorPane.getChildren().add(title);
        firstPlayer = new Label();
        firstPlayer.setFont(Font.font("System", FontWeight.NORMAL, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        secondPlayer = new Label();
        secondPlayer.setFont(Font.font("System", FontWeight.NORMAL, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        thirdPlayer = new Label();
        thirdPlayer.setFont(Font.font("System", FontWeight.NORMAL, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        fourthPlayer = new Label();
        fourthPlayer.setFont(Font.font("System", FontWeight.NORMAL, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        ImageView dice1 = new ImageView();
        dice1.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice1B.png"), 70, 70, true, true));
        AnchorPane.setBottomAnchor(dice1, GUIManager.getHeightPixel(62));
        AnchorPane.setLeftAnchor(dice1, GUIManager.getWidthPixel(20));
        AnchorPane.setLeftAnchor(firstPlayer, GUIManager.getWidthPixel(40));
        AnchorPane.setBottomAnchor(firstPlayer, GUIManager.getHeightPixel(65));
        ImageView dice2 = new ImageView();
        dice2.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice2Y.png"), 70, 70, true, true));
        AnchorPane.setBottomAnchor(dice2, GUIManager.getHeightPixel(47));
        AnchorPane.setLeftAnchor(dice2, GUIManager.getWidthPixel(20));
        AnchorPane.setLeftAnchor(secondPlayer, GUIManager.getWidthPixel(40));
        AnchorPane.setBottomAnchor(secondPlayer, GUIManager.getHeightPixel(50));
        ImageView dice3 = new ImageView();
        dice3.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice3P.png"), 70, 70, true, true));
        AnchorPane.setBottomAnchor(dice3, GUIManager.getHeightPixel(32));
        AnchorPane.setBottomAnchor(thirdPlayer, GUIManager.getHeightPixel(35));
        AnchorPane.setLeftAnchor(dice3, GUIManager.getWidthPixel(20));
        AnchorPane.setLeftAnchor(thirdPlayer, GUIManager.getWidthPixel(40));
        ImageView dice4 = new ImageView();
        dice4.setImage(new Image(ScoreLobbyView.class.getResourceAsStream("/images/DiceImages/Dice4G.png"), 70, 70, true, true));
        AnchorPane.setBottomAnchor(dice4, GUIManager.getHeightPixel(17));
        AnchorPane.setBottomAnchor(fourthPlayer, GUIManager.getHeightPixel(20));
        AnchorPane.setLeftAnchor(dice4, GUIManager.getWidthPixel(20));
        AnchorPane.setLeftAnchor(fourthPlayer, GUIManager.getWidthPixel(40));
        anchorPane.getChildren().addAll(dice1, firstPlayer, dice2, secondPlayer, dice3, thirdPlayer, dice4, fourthPlayer);
        setScores();

    }

    public void start(Stage stage){
        initialize();
        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage = stage;
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ranking");
        primaryStage.show();
        scoreLobbyView = this;
    }

    private void setScores(){
        ranking.entrySet().forEach(entry -> {
            String username = entry.getKey();
            int score = entry.getValue();
            setPlayer(username, score);
        });
    }

    private void setPlayer(String username, int score) {
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

    private void setTitle(){
        title = new Label("Rankings");
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setFont(Font.font("System", FontWeight.BOLD, GUIManager.getResizedFont(GUIManager.MAIN_TITLE)));
        title.setStyle("-fx-background-color: #d57322;" +
                "-fx-border-color: #000000"
        );
        AnchorPane.setTopAnchor(title, GUIManager.getHeightPixel(13));
        AnchorPane.setLeftAnchor(title, GUIManager.getWidthPixel(20));
        AnchorPane.setRightAnchor(title, GUIManager.getWidthPixel(20));
    }

    private void setFirstPlayer(String message, int score) {
        Platform.runLater(() -> firstPlayer.setText(message + " did " + score + " points"));
    }

    private void setSecondPlayer(String message, int score) {
        Platform.runLater(() -> secondPlayer.setText(message + " did " + score + " points"));
    }

    private void setThirdPlayer(String message, int score) {
        Platform.runLater(() -> thirdPlayer.setText(message + " did " + score + " points"));
    }

    private void setFourthPlayer(String message, int score) {
        Platform.runLater(() -> fourthPlayer.setText(message + " did " + score + " points"));
    }

    private static void startScoreView(Map<String, Integer> ranking, Stage stage) {
        new ScoreLobbyView(ranking).start(stage);
    }

    /**
     * Gets the single instance of ScoreView.
     *
     * @param ranking the ranks
     * @param stage the stage
     * @return single instance of ScoreView
     */
    public static ScoreLobbyView getInstance(Map<String, Integer> ranking, Stage stage) {
        if (scoreLobbyView == null) {
            Platform.runLater(() -> startScoreView(ranking, stage));
            while (scoreLobbyView == null)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                    return scoreLobbyView;
                }
        }
        return scoreLobbyView;
    }
}
