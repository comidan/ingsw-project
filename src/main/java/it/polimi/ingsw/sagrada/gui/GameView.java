package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView extends Application {

    private static Map<String, WindowView> windows;
    private DraftView draftView;
    private VBox verticalBox;
    private HBox horizontalBox;
    private BorderPane borderPane;
    private AnchorPane anchorPane;
    private static String username;
    private static DiceResponse diceResponse;
    private static List<String> players;
    private static Constraint[][] constraints;
    private Button endTurn;
    private double windowHeight;
    private double windowWidth;
    private ClickedObject clickedObject;
    private ClickHandler clickHandler;

    private void setDicesClickListener() {
        windows.get(username).setWindowDiceListener();
    }

    private void setDraftClickListener( ) {
        draftView.setDraftListener();
    }

    private void setEndTurnListener(EventHandler eventHandler) {
        endTurn.setOnMouseClicked(eventHandler);
    }


    public void setClickables(){
        setDicesClickListener();
        setDraftClickListener();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.clickedObject = new ClickedObject();
        verticalBox = new VBox();
        horizontalBox = new HBox();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowWidth = gd.getDisplayMode().getWidth();
        windowHeight = gd.getDisplayMode().getHeight();
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MainGameBoard.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        anchorPane.resize(windowWidth, windowHeight);
        clickHandler = ClickHandler.getDiceButtonController(clickedObject);
        draftView = new DraftView(diceResponse, clickedObject);
        clickHandler.setDraft(draftView);
        players.forEach(user -> windows.put(user, new WindowView(constraints, clickedObject)));
        verticalBox.setSpacing(15);
        for(int i = 1; i < players.size(); i++)
            verticalBox.getChildren().add(windows.get(players.get(i)));
        anchorPane.setBottomAnchor(verticalBox, getHeightPixel(10));
        anchorPane.setRightAnchor(verticalBox, getWidthPixel(10));
        anchorPane.getChildren().addAll(verticalBox);
        endTurn = new Button("End turn");
        horizontalBox.getChildren().add(endTurn);
        horizontalBox.setAlignment(Pos.BOTTOM_CENTER);
        horizontalBox.getChildren().add(windows.get(players.get(0)));
        anchorPane.setBottomAnchor(horizontalBox, getHeightPixel(11));
        anchorPane.setLeftAnchor(horizontalBox, getWidthPixel(10));
        anchorPane.getChildren().addAll(horizontalBox);
        draftView.setAlignment(Pos.CENTER);
        anchorPane.setBottomAnchor(draftView, getHeightPixel(50));
        anchorPane.setRightAnchor(draftView, getWidthPixel(40));
        anchorPane.getChildren().addAll(draftView);
        setClickables();
        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void terminate() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public static GameView startGameGUI(List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        username = players.get(0);
        windows = new HashMap<>();
        GameView.diceResponse = diceResponse;
        launch();
        return new GameView();
    }

    private double getHeightPixel(int perc) {
        return (perc * windowHeight / 100);
    }

    private double getWidthPixel(int perc) {
        return (perc * windowWidth / 100);
    }


}
