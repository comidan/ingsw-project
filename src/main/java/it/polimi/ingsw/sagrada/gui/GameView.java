package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
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
    private Resizer resizer;
    private RoundtrackView roundtrackView;
    private static GameView gameView = null;
    private CardBoard cardBoard;
    private GridPane tokenGrid;
    private List<Node> components;

    public String getUsername(){
        return username;
    }

    public void setCellClickListener(EventHandler<MouseEvent> cellClickEventHandler) {
        windows.get(username).setWindowDiceListener(cellClickEventHandler);
    }

    public void setRoundtrackClickHandler(EventHandler<MouseEvent> diceClickHandler){
        roundtrackView.setClickHandler(diceClickHandler);
    }

    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        cardBoard.setToolClickHandler(toolClickHandler);
    }

    public void setDraftClickHandler(EventHandler<MouseEvent> draftClickHandler) {
        draftView.setDraftListener(draftClickHandler);
        System.out.println("Draft click handler set");
    }

    public void setEndTurnHandler(EventHandler<ActionEvent> endTurnEventHandler){
        endTurn.setOnAction(endTurnEventHandler);
    }

    public DraftView getDraftView() {
        return draftView;
    }

    public RoundtrackView getRoundtrackView(){
        return this.roundtrackView;
    }

    public void setRoundtrackImage(List<DiceView> diceViews, int currentround){
        this.roundtrackView.setImage(diceViews, currentround);
    }

    public void setToken(int tokenNumber){
        tokenGrid = new GridPane();
        for(int i = 0; i<tokenNumber; i++){
            tokenGrid.add(new TokenView(), i, 1);
        }

        anchorPane.setBottomAnchor(tokenGrid, resizer.getHeightPixel(47));
        anchorPane.setLeftAnchor(tokenGrid, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(tokenGrid);

    }

    public void setPrivateObjective(){
        cardBoard.setPrivateObjective();
    }

    public void removeMistakenDice(int row, int col){
        windows.get(username).removeMistakenDice(row, col);
    }


    public void removeToken(int number){
        tokenGrid.getChildren().remove(0, number);
    }



    private void initialize(){
        this.cardBoard = new CardBoard();
        this.resizer = new Resizer();
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
    }

    private void createScene(Stage primaryStage){

        players.forEach(user -> windows.put(user, new WindowView(constraints)));
        verticalBox.setSpacing(15);
        for(int i = 1; i < players.size(); i++)
            verticalBox.getChildren().add(windows.get(players.get(i)));
        anchorPane.setBottomAnchor(verticalBox, resizer.getHeightPixel(10));
        anchorPane.setRightAnchor(verticalBox, resizer.getWidthPixel(1));
        anchorPane.getChildren().addAll(verticalBox);
        endTurn = new Button("End turn");
        horizontalBox.getChildren().add(endTurn);
        horizontalBox.setAlignment(Pos.BOTTOM_CENTER);
        horizontalBox.getChildren().add(windows.get(username));
        anchorPane.setBottomAnchor(horizontalBox, resizer.getHeightPixel(11));
        anchorPane.setLeftAnchor(horizontalBox, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(horizontalBox);
        setDraft(diceResponse);
        setPrivateObjective();
        anchorPane.setBottomAnchor(cardBoard, resizer.getHeightPixel(7));
        anchorPane.setRightAnchor(cardBoard, resizer.getWidthPixel(32));
        anchorPane.getChildren().addAll(cardBoard);
        anchorPane.setTopAnchor(roundtrackView, resizer.getHeightPixel(17));
        anchorPane.setLeftAnchor(roundtrackView, resizer.getWidthPixel(68));
        anchorPane.getChildren().add(roundtrackView);
        components = new ArrayList<>();
        components.add(draftView);
        components.add(endTurn);
        components.add(windows.get(username));
        Scene scene = new Scene(anchorPane, resizer.getWindowWidth(), resizer.getWindowHeight());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        initialize();
        createScene(primaryStage);
        gameView = this;
    }

    public void terminate() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private static void startGameGUI(String username, Stage stage, List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        GameView.diceResponse = diceResponse;
        new GameView().start(stage);
    }

    private static void startGameGUI(String username, List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        GameView.diceResponse = diceResponse;
        GameView.launch(GameView.class);
    }

    public static GameView getInstance(String username, Stage stage, List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        if (gameView == null) {
           Platform.runLater(() -> startGameGUI(username, stage, players, diceResponse, constraints));
            while (gameView == null)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                    return gameView;
                }
        }
        return gameView;
    }

    public static GameView getInstance(String username, List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        if (gameView == null) {
            new Thread(() -> startGameGUI(username, players, diceResponse, constraints)).start(); //)Platform.runLater(() -> startGameGUI(username, stage, players, diceResponse, constraints));
            while (gameView == null)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                    return gameView;
                }
        }
        return gameView;
    }

    public void setDraft(DiceResponse diceResponse) {
        if(draftView != null)
            anchorPane.getChildren().removeAll(draftView);
        draftView = new DraftView(diceResponse);
        draftView.setAlignment(Pos.CENTER);
        anchorPane.setBottomAnchor(draftView, resizer.getHeightPixel(70));
        anchorPane.setRightAnchor(draftView, resizer.getWidthPixel(40));
        anchorPane.getChildren().addAll(draftView);
    }

    public void notifyTurn() {
        components.forEach(node -> node.setDisable(false));

    }

    public void notifyEndTurn() {
        components.forEach(node -> node.setDisable(true));
    }
}
