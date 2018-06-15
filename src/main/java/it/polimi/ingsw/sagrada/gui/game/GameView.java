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


/**
 * The Class GameView.
 */
public class GameView extends Application {

    /** The windows. */
    private static Map<String, WindowView> windows;
    
    /** The draft view. */
    private DraftView draftView;
    
    /** The vertical box. */
    private VBox verticalBox;
    
    /** The horizontal box. */
    private HBox horizontalBox;
    
    /** The anchor pane. */
    private AnchorPane anchorPane;
    
    /** The username. */
    private static String username;
    
    /** The players. */
    private static List<String> players;
    
    /** The constraints. */
    private static List<Constraint[][]> constraints;
    
    /** The end turn. */
    private Button endTurn;
    
    /** The resizer. */
    private Resizer resizer;
    
    /** The roundtrack view. */
    private RoundtrackView roundtrackView;
    
    /** The game view. */
    private static GameView gameView = null;
    
    /** The card board. */
    private CardBoard cardBoard;
    
    /** The token grid. */
    private GridPane tokenGrid;
    
    /** The components. */
    private List<Node> components;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the cell click listener.
     *
     * @param cellClickEventHandler the new cell click listener
     */
    void setCellClickListener(EventHandler<MouseEvent> cellClickEventHandler) {
        windows.get(username).setWindowDiceListener(cellClickEventHandler);
    }

    /**
     * Sets the roundtrack click handler.
     *
     * @param diceClickHandler the new roundtrack click handler
     */
    void setRoundtrackClickHandler(EventHandler<MouseEvent> diceClickHandler){
        roundtrackView.setClickHandler(diceClickHandler);
    }

    /**
     * Sets the tool click handler.
     *
     * @param toolClickHandler the new tool click handler
     */
    void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        cardBoard.setToolClickHandler(toolClickHandler);
    }

    /**
     * Sets the draft click handler.
     *
     * @param draftClickHandler the new draft click handler
     */
    void setDraftClickHandler(EventHandler<MouseEvent> draftClickHandler) {
        draftView.setDraftListener(draftClickHandler);
    }

    /**
     * Sets the end turn handler.
     *
     * @param endTurnEventHandler the new end turn handler
     */
    void setEndTurnHandler(EventHandler<ActionEvent> endTurnEventHandler){
        endTurn.setOnAction(endTurnEventHandler);
    }

    /**
     * Gets the draft view.
     *
     * @return the draft view
     */
    DraftView getDraftView() {
        return draftView;
    }

    /**
     * Gets the roundtrack view.
     *
     * @return the roundtrack view
     */
    RoundtrackView getRoundtrackView(){
        return this.roundtrackView;
    }

    /**
     * Sets the roundtrack image.
     *
     * @param diceViews the dice views
     * @param currentRound the current round
     */
    void setRoundtrackImage(List<DiceView> diceViews, int currentRound){
        this.roundtrackView.setImage(diceViews, currentRound);
    }

    /**
     * Gets the single instance of GameView.
     *
     * @param username the username
     * @param players the players
     * @param constraints the constraints
     * @return single instance of GameView
     */
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

    /**
     * Sets the private objective.
     *
     * @param id the new private objective
     */
    void setPrivateObjective(int id) {
        cardBoard.setPrivateObjective(id);
    }

    /**
     * Sets the public objectives.
     *
     * @param publicObjectives the new public objectives
     */
    void setPublicObjectives(List<Integer> publicObjectives) {
        cardBoard.setPublicObjectives(publicObjectives);
    }

    /**
     * Sets the tool cards.
     *
     * @param toolCards the new tool cards
     */
    void setToolCards(List<Integer> toolCards) {
        cardBoard.setToolCards(toolCards);
    }

    /**
     * Removes the mistaken dice.
     *
     * @param row the row
     * @param col the col
     */
    void removeMistakenDice(int row, int col){
        windows.get(username).removeMistakenDice(row, col);
    }


    /**
     * Removes the token.
     *
     * @param number the number
     */
    void removeToken(int number){
        tokenGrid.getChildren().remove(0, number);
    }



    /**
     * Initialize.
     */
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

    /**
     * Sets the token.
     *
     * @param tokenNumber the new token
     */
    public void setToken(int tokenNumber) {
        tokenGrid = new GridPane();
        for (int i = 0; i < tokenNumber; i++) {
            tokenGrid.add(new TokenView(), i, 1);
        }

        AnchorPane.setBottomAnchor(tokenGrid, resizer.getHeightPixel(47));
        AnchorPane.setLeftAnchor(tokenGrid, resizer.getWidthPixel(10));
        anchorPane.getChildren().addAll(tokenGrid);

    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) {
        initialize();
        createScene(primaryStage);
        setGameViewInstance(this);
    }

    /**
     * Sets the game view instance.
     *
     * @param gameViewInstance the new game view instance
     */
    private static void setGameViewInstance(GameView gameViewInstance) {
        gameView = gameViewInstance;
    }

    /**
     * Terminate.
     */
    public void terminate() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Start game GUI.
     *
     * @param username the username
     * @param stage the stage
     * @param players the players
     * @param constraints the constraints
     */
    private static void startGameGUI(String username, Stage stage, List<String> players, List<Constraint[][]> constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        new GameView().start(stage);
    }

    /**
     * Start game GUI.
     *
     * @param username the username
     * @param players the players
     * @param constraints the constraints
     */
    private static void startGameGUI(String username, List<String> players, List<Constraint[][]> constraints) {
        GameView.constraints = constraints;
        GameView.players = players;
        GameView.username = username;
        windows = new HashMap<>();
        GameView.launch(GameView.class);
    }

    /**
     * Gets the single instance of GameView.
     *
     * @param username the username
     * @param stage the stage
     * @param players the players
     * @param constraints the constraints
     * @return single instance of GameView
     */
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

    /**
     * Creates the scene.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Sets the draft.
     *
     * @param diceResponse the new draft
     */
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

    /**
     * Notify turn.
     */
    void notifyTurn() {
        components.forEach(node -> node.setDisable(false));
    }

    /**
     * Removes the player.
     *
     * @param playerId the player id
     */
    void removePlayer(String playerId) {
        players.remove(playerId);
        verticalBox.getChildren().removeAll(windows.get(playerId));
    }

    /**
     * Notify end turn.
     */
    void notifyEndTurn() {
        components.forEach(node -> node.setDisable(true));
    }

    /**
     * Sets the opponent window.
     *
     * @param username the username
     * @param dice the dice
     * @param position the position
     */
    void setOpponentWindow(String username, Dice dice, Position position) {
        windows.get(username).setDice(dice, position);
    }
}