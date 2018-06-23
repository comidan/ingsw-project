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

    private static Stage stage;
    
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

    private Button windowButton;

    /** The constraints. */
    private static List<Constraint[][]> constraints;
    
    /** The end turn button. */
    private EndTurn endTurn;

    /** The guiManager. */
    private GUIManager guiManager;
    
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
        windowButton = new Button();
        endTurn = new EndTurn();
        this.frame = new FrameView();
        this.cardBoard = new CardBoard();
        this.guiManager = new GUIManager();
        this.roundTrackView = new RoundTrackView();
        hBox = new HBox();
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MainGameBoard.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        anchorPane.resize(guiManager.getWindowWidth(), guiManager.getWindowHeight());
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

        AnchorPane.setBottomAnchor(tokenGrid, guiManager.getFullHeightPixel(72));
        AnchorPane.setLeftAnchor(tokenGrid, guiManager.getFullWidthPixel(9));
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
        for (int i = 0; i < players.size(); i++)
            if (!players.get(i).equals(username))
                hBox.getChildren().add(windows.get(players.get(i)));
        setHBox();
        setWindow();
        setCardBoard();
        setCardPreviewButtons();
        setButtons();
        setRoundtrack();
        components = new ArrayList<>();
        components.add(endTurn);
        components.add(windows.get(username));
        Scene scene = new Scene(anchorPane, guiManager.getScreenWidth(), guiManager.getScreenHeight());
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
        AnchorPane.setBottomAnchor(draftView, guiManager.getHeightPixel(70));
        AnchorPane.setRightAnchor(draftView, guiManager.getFullWidthPixel(50));
        anchorPane.getChildren().addAll(draftView);
        components.add(draftView);
    }

    public void removeDiceView(DiceView diceView){
        draftView.removeDiceView(diceView);
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
      windowButton.setOnMouseClicked(windowButtonHandler);
    }


    public void setHBox(){
        anchorPane.setBottomAnchor(hBox, guiManager.getFullHeightPixel(30));
        anchorPane.setRightAnchor(hBox, guiManager.getFullWidthPixel(6));
    }

    public void setWindow(){
        frame.addWindowToFrame(windows.get(username));
        anchorPane.setBottomAnchor(frame, guiManager.getFullHeightPixel(6));
        anchorPane.setLeftAnchor(frame, guiManager.getFullWidthPixel(7));
        anchorPane.getChildren().addAll(frame);
    }

    public void setRoundtrack(){
        AnchorPane.setTopAnchor(roundTrackView, guiManager.getFullHeightPixel(5));
        AnchorPane.setRightAnchor(roundTrackView, guiManager.getFullWidthPixel(3));
        anchorPane.getChildren().add(roundTrackView);

    }
    public void setButtons(){
        Image image = new Image(GameView.class.getResourceAsStream("/images/windowPrev.png"));
        windowButton.setMinHeight(image.getHeight());
        windowButton.setMinWidth(image.getWidth());
        Background background = new Background(new BackgroundImage( image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
        windowButton.setBackground(background);
        anchorPane.setBottomAnchor(windowButton, guiManager.getFullHeightPixel(30));
        anchorPane.setRightAnchor(windowButton, guiManager.getFullWidthPixel(3));
        anchorPane.setBottomAnchor(endTurn, guiManager.getFullHeightPixel(6));
        anchorPane.setLeftAnchor(endTurn, guiManager.getFullWidthPixel(36));
        anchorPane.getChildren().addAll(endTurn, windowButton);
    }

    public void setCardBoard(){
        anchorPane.setBottomAnchor(cardBoard, guiManager.getFullHeightPixel(0));
        anchorPane.setRightAnchor(cardBoard, guiManager.getFullWidthPixel(0));
        anchorPane.getChildren().add(cardBoard);
    }



    void showOtherWindows(){
        anchorPane.getChildren().addAll(hBox);
    }

    void hideOtherWindows(){
        anchorPane.getChildren().remove(hBox);
    }

    private void setCardPreviewButtons(){
        toolcardPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/toolCard.jpg")));
        privateObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/privateObj.jpg")));
        publicObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/publicObj.png")));
        anchorPane.setBottomAnchor(toolcardPrev, guiManager.getFullHeightPixel(6));
        anchorPane.setRightAnchor(toolcardPrev, guiManager.getFullWidthPixel(20));
        anchorPane.setBottomAnchor(privateObjPrev, guiManager.getFullHeightPixel(6));
        anchorPane.setRightAnchor(privateObjPrev, guiManager.getFullWidthPixel(30));
        anchorPane.setBottomAnchor(publicObjPrev, guiManager.getFullHeightPixel(6));
        anchorPane.setRightAnchor(publicObjPrev, guiManager.getFullWidthPixel(40));
        anchorPane.getChildren().addAll(toolcardPrev, privateObjPrev, publicObjPrev);

    }

    void setToolPreviewListener(EventHandler<MouseEvent> cardHandler){
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

}