package it.polimi.ingsw.sagrada.gui.game;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.cards.CardBoard;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.components.DraftView;
import it.polimi.ingsw.sagrada.gui.components.RoundtrackView;
import it.polimi.ingsw.sagrada.gui.components.TokenView;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
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
    private static List<String> players;
    private static List<Constraint[][]> constraints;
    private Button endTurn;
    private Resizer resizer;
    private RoundtrackView roundtrackView;
    private static GameView gameView = null;
    private CardBoard cardBoard;
    private GridPane tokenGrid;
    private List<Node> components;

    public String getUsername(){
        return username;
    }

    void setCellClickListener(EventHandler<MouseEvent> cellClickEventHandler) {
        windows.get(username).setWindowDiceListener(cellClickEventHandler);
    }

    void setRoundtrackClickHandler(EventHandler<MouseEvent> diceClickHandler){
        roundtrackView.setClickHandler(diceClickHandler);
    }

    void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        cardBoard.setToolClickHandler(toolClickHandler);
    }

    void setDraftClickHandler(EventHandler<MouseEvent> draftClickHandler) {
        draftView.setDraftListener(draftClickHandler);
    }

    void setEndTurnHandler(EventHandler<ActionEvent> endTurnEventHandler){
        endTurn.setOnAction(endTurnEventHandler);
    }

    DraftView getDraftView() {
        return draftView;
    }

    RoundtrackView getRoundtrackView(){
        return this.roundtrackView;
    }

    void setRoundtrackImage(List<DiceView> diceViews, int currentRound){
        this.roundtrackView.setImage(diceViews, currentRound);
    }

    public static GameView getInstance(String username, List<String> players, List<Constraint[][]> constraints) {
        if (gameView == null) {
            new Thread(() -> startGameGUI(username, players, constraints)).start(); //)Platform.runLater(() -> startGameGUI(username, stage, player, diceResponse, constraints));
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

    void setPrivateObjective(int id) {
        cardBoard.setPrivateObjective(id);
    }

    void setPublicObjectives(List<Integer> publicObjectives) {
        cardBoard.setPublicObjectives(publicObjectives);
    }

    void setToolCards(List<Integer> toolCards) {
        cardBoard.setToolCards(toolCards);
    }

    void removeMistakenDice(int row, int col){
        windows.get(username).removeMistakenDice(row, col);
    }


    void removeToken(int number){
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

    public void setToken(int tokenNumber) {
        tokenGrid = new GridPane();
        for (int i = 0; i < tokenNumber; i++) {
            tokenGrid.add(new TokenView(), i, 1);
        }

        AnchorPane.setBottomAnchor(tokenGrid, resizer.getHeightPixel(47));
        AnchorPane.setLeftAnchor(tokenGrid, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(tokenGrid);

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

    private static void startGameGUI(String username, Stage stage, List<String> players, List<Constraint[][]> constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        new GameView().start(stage);
    }

    private static void startGameGUI(String username, List<String> players, List<Constraint[][]> constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        GameView.launch(GameView.class);
    }

    public static GameView getInstance(String username, Stage stage, List<String> players, List<Constraint[][]> constraints) {
        if (gameView == null) {
           Platform.runLater(() -> startGameGUI(username, stage, players, constraints));
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

    private void createScene(Stage primaryStage) {

        players.forEach(user -> windows.put(user, new WindowView(constraints.get(players.indexOf(user)))));
        verticalBox.setSpacing(15);
        for (int i = 0; i < players.size(); i++)
            if (!players.get(i).equals(username))
                verticalBox.getChildren().add(windows.get(players.get(i)));
        AnchorPane.setBottomAnchor(verticalBox, resizer.getHeightPixel(10));
        AnchorPane.setRightAnchor(verticalBox, resizer.getWidthPixel(1));
        anchorPane.getChildren().addAll(verticalBox);
        endTurn = new Button("End turn");
        horizontalBox.getChildren().add(endTurn);
        horizontalBox.setAlignment(Pos.BOTTOM_CENTER);
        horizontalBox.getChildren().add(windows.get(username));
        AnchorPane.setBottomAnchor(horizontalBox, resizer.getHeightPixel(11));
        AnchorPane.setLeftAnchor(horizontalBox, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(horizontalBox);
        AnchorPane.setBottomAnchor(cardBoard, resizer.getHeightPixel(7));
        AnchorPane.setRightAnchor(cardBoard, resizer.getWidthPixel(32));
        anchorPane.getChildren().addAll(cardBoard);
        AnchorPane.setTopAnchor(roundtrackView, resizer.getHeightPixel(17));
        AnchorPane.setLeftAnchor(roundtrackView, resizer.getWidthPixel(68));
        anchorPane.getChildren().add(roundtrackView);
        components = new ArrayList<>();
        components.add(endTurn);
        components.add(windows.get(username));
        Scene scene = new Scene(anchorPane, resizer.getWindowWidth(), resizer.getWindowHeight());
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void setDraft(DiceResponse diceResponse) {
        if(draftView != null)
            anchorPane.getChildren().removeAll(draftView);
        draftView = new DraftView(diceResponse);
        draftView.setAlignment(Pos.CENTER);
        AnchorPane.setBottomAnchor(draftView, resizer.getHeightPixel(70));
        AnchorPane.setRightAnchor(draftView, resizer.getWidthPixel(40));
        anchorPane.getChildren().addAll(draftView);
        components.add(draftView);
    }

    void notifyTurn() {
        components.forEach(node -> node.setDisable(false));
    }

    void removePlayer(String playerId) {
        players.remove(playerId);
        verticalBox.getChildren().removeAll(windows.get(playerId));
    }

    void notifyEndTurn() {
        components.forEach(node -> node.setDisable(true));
    }

    void setOpponentWindow(String username, Dice dice, Position position) {
        windows.get(username).setDice(dice, position);
    }
}