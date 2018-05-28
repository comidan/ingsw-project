package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView extends Application {

    private static Map<String, WindowView> windows;
    private DraftView draftView;
    private VBox verticalBox;
    private HBox horizontalBox;
    private BorderPane borderPane;
    private static String username;
    private static DiceResponse diceResponse;
    private static List<String> players;
    private static Constraint[][] constraints;
    private Button endTurn;

    public void setDicesClickListener(EventHandler eventHandler) {
        windows.get(username).setWindowDiceListener(eventHandler);
    }

    public void setDraftClickListener(EventHandler eventHandler) {
        draftView.setDraftListener(eventHandler);
    }

    public void setEndTurnListener(EventHandler eventHandler) {
        endTurn.setOnMouseClicked(eventHandler);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        verticalBox = new VBox();
        horizontalBox = new HBox();
        borderPane = new BorderPane();
        draftView = new DraftView(diceResponse);
        players.forEach(user -> windows.put(user, new WindowView(constraints)));
        for(int i = 1; i < players.size(); i++)
            verticalBox.getChildren().add(windows.get(players.get(i)));
        horizontalBox.getChildren().add(windows.get(players.get(0)));
        borderPane.setLeft(verticalBox);
        endTurn = new Button("End turn");
        horizontalBox.getChildren().add(endTurn);
        borderPane.setBottom(horizontalBox);
        borderPane.setCenter(draftView);
    }

    public static void startGameGUI(List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        username = players.get(0);
        windows = new HashMap<>();
        GameView.diceResponse = diceResponse;
        launch();
    }
}
