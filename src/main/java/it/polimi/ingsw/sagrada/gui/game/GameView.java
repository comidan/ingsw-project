package it.polimi.ingsw.sagrada.gui.game;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.cards.CardBoard;
import it.polimi.ingsw.sagrada.gui.components.*;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    private HBox hBox;

    /** The frame view. */
    private FrameView frame;

    /** The anchor pane. */
    private AnchorPane anchorPane;
    
    /** The username. */
    private static String username;
    
    /** The players. */
    private static List<String> players;

    /** The constraints. */
    private static List<Constraint[][]> constraints;
    
    /** The end turn button. */
    private EndTurn endTurn;
    
    /** The roundtrack view. */
    private RoundTrackView roundTrackView;
    
    /** The game view. */
    private static GameView gameView = null;
    
    /** The card board. */
    private CardBoard cardBoard;
    
    /** The pane containing all the player's token. */
    private GridPane tokenGrid;
    
    /** The components. */
    private List<Node> components;


    /** The preview of toolcards which are to be shown on click. */
    private ImageView toolcardPrev;

    /** The preview of public objective cards which are to be shown on click. */
    private ImageView publicObjPrev;

    /** The preview of private objective cards which are to be shown on click. */
    private ImageView privateObjPrev;

    private ImageView windowPrev;

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
     * @param cellDragOver, cellDragDone, the new cell click listener
     */
    void setCellClickListener(EventHandler<DragEvent> cellDragOver,EventHandler<DragEvent> cellDragDone) {
        windows.get(username).setWindowDiceListener(cellDragOver, cellDragDone);
    }



    /**
     * Sets the roundtrack click handler.
     *
     * @param diceClickHandler the new roundtrack click handler
     */
    void setRoundtrackClickHandler(EventHandler<MouseEvent> diceClickHandler){
        roundTrackView.setClickHandler(diceClickHandler);
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
    void setEndTurnHandler(EventHandler<MouseEvent> endTurnEventHandler){
        endTurn.setEndTurnHandler(endTurnEventHandler);
    }

    /**
     * Gets the draft view.
     *
     * @return the draft view
     */
    DraftView getDraftView() {
        return draftView;
    }

    CardBoard getCardBoard(){
        return cardBoard;
    }

    /**
     * Gets the roundtrack view.
     *
     * @return the roundtrack view
     */
    RoundTrackView getRoundTrackView(){
        return this.roundTrackView;
    }

    /**
     * Sets the roundtrack image.
     *
     * @param diceViews the dice views
     * @param currentRound the current round
     */
    public void setRoundtrackImage(List<DiceView> diceViews, int currentRound){
        this.roundTrackView.setDice(diceViews, currentRound);
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
        endTurn = new EndTurn();
        this.frame = new FrameView();
        this.cardBoard = new CardBoard();
        this.roundTrackView = new RoundTrackView();
        hBox = new HBox();
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MainGameBoard.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        anchorPane.resize(GUIManager.getGameWindowWidth(), GUIManager.getGameWindowHeight());
    }

    /**
     * Sets the token.
     *
     * @param tokenNumber the new token
     */
    public void setToken(int tokenNumber) {
        tokenGrid = new GridPane();
        for (int i = 0; i < tokenNumber; i++) {
            tokenGrid.add(new TokenView(), i, 1); //pay attention : manage click even when all tokens are removed : IndexOutOfBoundException
        }

        AnchorPane.setTopAnchor(tokenGrid, GUIManager.getGameHeightPixel(10));
        AnchorPane.setLeftAnchor(tokenGrid, GUIManager.getGameWidthPixel(9));
        anchorPane.getChildren().addAll(tokenGrid);

    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) {
        initialize();
        createScene(primaryStage);
        primaryStage.setTitle("Sagrada");
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
        hBox.setSpacing(15);
        players.forEach(plr -> {
            if(!plr.equals(username)) hBox.getChildren().add(windows.get(plr));
        });
        setHBox();
        setWindow();
        setCardBoard();
        setEndTurnButton();
        setCardPreviewButtons();
        setWindowButton();
        setRoundtrack();
        components = new ArrayList<>();
        components.add(endTurn);
        components.add(windows.get(username));
        Scene scene = new Scene(anchorPane, GUIManager.getGameWindowWidth(), GUIManager.getGameWindowHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
        AnchorPane.setBottomAnchor(draftView, GUIManager.getGameHeightPixel(70));
        AnchorPane.setRightAnchor(draftView, GUIManager.getGameWidthPixel(50));
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
        hBox.getChildren().removeAll(windows.get(playerId));
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

    public void setWindowButtonHandler(EventHandler<MouseEvent> windowButtonHandler){
      windowPrev.setOnMouseClicked(windowButtonHandler);
    }


    private void setHBox(){
        AnchorPane.setBottomAnchor(hBox, GUIManager.getGameHeightPixel(30));
        AnchorPane.setRightAnchor(hBox, GUIManager.getGameWidthPixel(6));
    }

    private void setWindow(){
        frame.addWindowToFrame(windows.get(username));
        AnchorPane.setBottomAnchor(frame, GUIManager.getGameHeightPixel(8));
        AnchorPane.setLeftAnchor(frame, GUIManager.getGameWidthPixel(4));
        anchorPane.getChildren().addAll(frame);
    }

    private void setRoundtrack(){
        AnchorPane.setTopAnchor(roundTrackView, GUIManager.getGameHeightPixel(2.3));
        AnchorPane.setRightAnchor(roundTrackView, GUIManager.getGameWidthPixel(3));
        anchorPane.getChildren().add(roundTrackView);

    }

    private void setWindowButton() {
        windowPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/windowPrev.png")));
        windowPrev.setPreserveRatio(true);
        windowPrev.setFitHeight(GUIManager.getGameHeightPixel(40));
        AnchorPane.setRightAnchor(windowPrev, GUIManager.getGameHeightPixel(4));
        AnchorPane.setBottomAnchor(windowPrev, GUIManager.getGameHeightPixel(30));
        anchorPane.getChildren().add(windowPrev);
    }

    private void setCardBoard(){
        AnchorPane.setBottomAnchor(cardBoard, GUIManager.getGameHeightPixel(0));
        AnchorPane.setRightAnchor(cardBoard, GUIManager.getGameWidthPixel(0));
        anchorPane.getChildren().add(cardBoard);
    }



    void showOtherWindows(){
        anchorPane.getChildren().addAll(hBox);
    }

    void hideOtherWindows(){
        anchorPane.getChildren().remove(hBox);
    }

    private void setCardPreviewButtons(){
        toolcardPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/toolCard.png")));
        toolcardPrev.setPreserveRatio(true);
        toolcardPrev.setFitWidth(GUIManager.getGameWidthPixel(8));
        privateObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/privateObj.png")));
        privateObjPrev.setPreserveRatio(true);
        privateObjPrev.setFitWidth(GUIManager.getGameWidthPixel(8));
        publicObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/publicObj.png")));
        publicObjPrev.setPreserveRatio(true);
        publicObjPrev.setFitWidth(GUIManager.getGameWidthPixel(8));
        AnchorPane.setBottomAnchor(toolcardPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(toolcardPrev, GUIManager.getGameWidthPixel(10));
        AnchorPane.setBottomAnchor(privateObjPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(privateObjPrev, GUIManager.getGameWidthPixel(20));
        AnchorPane.setBottomAnchor(publicObjPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(publicObjPrev, GUIManager.getGameWidthPixel(30));
        anchorPane.getChildren().addAll(toolcardPrev, privateObjPrev, publicObjPrev);

    }

    void setToolPreviewListener(EventHandler<MouseEvent> cardHandler) {
        toolcardPrev.setOnMouseClicked(cardHandler);
    }

    void setPrivatePreviewListener(EventHandler<MouseEvent> cardHandler){
        privateObjPrev.setOnMouseClicked(cardHandler);

    }
    void setPublicPreviewListener(EventHandler<MouseEvent> cardHandler){
        publicObjPrev.setOnMouseClicked(cardHandler);

    }

    void showPublicCard(){
        cardBoard.showPublicCards();
    }

    void showPrivateCard(){
        cardBoard.showPrivateCards();
    }

    void showToolCard(){
        cardBoard.showToolCards();
    }

    void hidePublicCard(){
        cardBoard.hidePublicCards();
    }

    void hidePrivateCard(){
        cardBoard.hidePrivateCards();
    }

    void hideToolCard(){
        cardBoard.hideToolCards();
    }

    public Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    void setDraftChangeValue(EventHandler<MouseEvent> changeValueHandler){
        draftView.setDraftChangeValue(changeValueHandler);
    }

    void enableWindowDiceDrag(EventHandler<MouseEvent> enableWindowDragHandler){
        windows.get(username).enableWindowDiceDrag(enableWindowDragHandler);
    }

    void setEndTurnButton(){
        AnchorPane.setLeftAnchor(endTurn, GUIManager.getGameWidthPixel(40));
        AnchorPane.setBottomAnchor(endTurn, GUIManager.getGameHeightPixel(10));
        anchorPane.getChildren().add(endTurn);
    }

}