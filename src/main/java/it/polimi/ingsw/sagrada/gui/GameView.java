package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane anchorPane;
    private static String username;
    private static DiceResponse diceResponse;
    private static List<String> players;
    private static Constraint[][] constraints;
    private Button endTurn;
    private ClickedObject clickedObject;
    private ClickHandler clickHandler;
    private Resizer resizer;
    private RoundtrackView roundtrackView;

    private void setDicesClickListener() {
        windows.get(username).setWindowDiceListener();
    }

    public String getUsername(){
        return username;
    }

    public void setDraftClickHandler() {
        draftView.setDraftListener();
    }


    public void setEndTurnHandler(EventHandler<ActionEvent> endTurnHandler) {
        endTurn.setOnAction(endTurnHandler);
      /*  endTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

                // signal end of turn to server

            }
        });*/
    }


    public void setClickables(){
        setDicesClickListener();
        setDraftClickHandler();
        //setEndTurnHandler();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.resizer = new Resizer();
        this.clickedObject = new ClickedObject();
        this.roundtrackView = new RoundtrackView();
        verticalBox = new VBox();
        horizontalBox = new HBox();
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MainGameBoard.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        anchorPane.resize(resizer.getWindowWidth(), resizer.getWindowHeight());
        clickHandler = ClickHandler.getDiceButtonController(clickedObject);
        draftView = new DraftView(diceResponse, clickedObject);
        clickHandler.setDraft(draftView);
        players.forEach(user -> windows.put(user, new WindowView(constraints, clickedObject)));
        verticalBox.setSpacing(15);
        for(int i = 1; i < players.size(); i++)
            verticalBox.getChildren().add(windows.get(players.get(i)));
        anchorPane.setBottomAnchor(verticalBox, resizer.getHeightPixel(10));
        anchorPane.setRightAnchor(verticalBox, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(verticalBox);
        endTurn = new Button("End turn");
        horizontalBox.getChildren().add(endTurn);
        horizontalBox.setAlignment(Pos.BOTTOM_CENTER);
        horizontalBox.getChildren().add(windows.get(players.get(0)));
        anchorPane.setBottomAnchor(horizontalBox, resizer.getHeightPixel(11));
        anchorPane.setLeftAnchor(horizontalBox, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(horizontalBox);
        draftView.setAlignment(Pos.CENTER);
        anchorPane.setBottomAnchor(draftView, resizer.getHeightPixel(50));
        anchorPane.setRightAnchor(draftView, resizer.getWidthPixel(40));
        anchorPane.getChildren().addAll(draftView);
        setClickables();
        Scene scene = new Scene(anchorPane, resizer.getWindowWidth(), resizer.getWindowHeight());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void terminate() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
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



}
