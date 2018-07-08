package it.polimi.ingsw.sagrada.gui.game;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.cards.CardBoard;
import it.polimi.ingsw.sagrada.gui.cards.ToolCardView;
import it.polimi.ingsw.sagrada.gui.components.*;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.gui.windows.WindowView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class GameView.
 */
public class GameView extends Application {
    
    /** The Constant NOTIFICATION_TIME. */
    private static final int NOTIFICATION_TIME = 3000;
    
    /** The notifying flag. */
    private boolean notifying = false;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GameView.class.getName());

    /** The map of the windows. */
    private static Map<String, WindowView> windows;
    
    /** The draft view. */
    private DraftView draftView;
    
    /** The horizontal box containing opponents' windows. */
    private HBox hBox;

    /** The frame view containing one's own window. */
    private FrameView frame;

    /** The anchor pane containing the main components. */
    private AnchorPane anchorPane;
    
    /** The username of the player seeing the view. */
    private static String username;
    
    /** The players. */
    private static List<String> players;

    /** The constraints. */
    private static Map<String, Constraint[][]> constraints;
    
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

    /** The window prev. */
    private ImageView windowPrev;

    /** The notification. */
    private Label notification;

    /** The timer. */
    private Label timer;

    /** The dice prev container. */
    private DicePrevContainer dicePrevContainer;

    /**
     * Gets the username of the player associated to the view.
     *
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the cell click listener to one's own window
     *
     * @param cellDragOver the cell drag over handler
     * @param cellDragDone the cell drag done handler
     */
    void setCellClickListener(EventHandler<DragEvent> cellDragOver,EventHandler<DragEvent> cellDragDone) {
        windows.get(username).setWindowDiceListener(cellDragOver, cellDragDone);
    }



    /**
     * Sets the roundtrack click handler on relative toolcard usage
     *
     * @param diceClickHandler the new roundtrack click handler
     */
    void setRoundtrackClickHandler(EventHandler<MouseEvent> diceClickHandler){
        roundTrackView.setClickHandler(diceClickHandler);
    }

    /**
     * Sets the tool click handler to handle tool buy
     *
     * @param toolClickHandler the tool click handler
     */
    void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        cardBoard.setToolClickHandler(toolClickHandler);
    }

    /**
     * Disable the tool click handler when a toolcard has already been bought in the current turn
     */

    void disableToolClickHandler() {
        cardBoard.disableToolClickHandler();
    }

    /**
     * Sets the draft click handler to handle drag from draft.
     *
     * @param draftClickHandler the draft click handler
     */
    void setDraftClickHandler(EventHandler<MouseEvent> draftClickHandler) {
        draftView.setDraftListener(draftClickHandler);
    }

    /**
     * Sets the end turn handler to handle end turn click.
     *
     * @param endTurnEventHandler the end turn handler
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


    /**
     * Gets the roundtrack view.
     *
     * @return the roundtrack view
     */
    RoundTrackView getRoundTrackView(){
        return this.roundTrackView;
    }

    /**
     * Sets the roundtrack background image.
     *
     * @param diceViews the dice views
     * @param currentRound the current round
     * @param isTool the is tool
     */
    public void setRoundTrackImage(List<DiceView> diceViews, int currentRound, boolean isTool) {
        if(isTool) this.roundTrackView.setDiceTool(diceViews, currentRound);
        else this.roundTrackView.setRoundTrackEndTurn(diceViews);
    }

    /**
     * Sets the round track reconnection.
     *
     * @param roundTrack the new round track reconnection
     */
    void setRoundTrackReconnection(List<List<DiceView>> roundTrack) {
        roundTrackView.setDiceReconnection(roundTrack);
    }

    /**
     * Show dice prev container when relative toolcard is used
     *
     * @param color the color
     * @param diceId the dice id
     */
    public void showDicePrevContainer(Colors color, int diceId){
        dicePrevContainer = new DicePrevContainer(color, diceId);
        AnchorPane.setRightAnchor(dicePrevContainer, GUIManager.getGameWidthPixel(50));
        AnchorPane.setBottomAnchor(dicePrevContainer, GUIManager.getGameHeightPixel(30));
        anchorPane.getChildren().add(dicePrevContainer);
    }

    /**
     * Removes the dice prev container on usage over.
     */
    public void removeDicePrevContainer() {
        anchorPane.getChildren().remove(dicePrevContainer);
    }

    /**
     * Sets the choose value when relative toolcard is used.
     *
     * @param chooseValueHandler the choose value handler
     */
    public void setChooseValue(EventHandler<MouseEvent> chooseValueHandler){
        dicePrevContainer.setDicePrevHandler(chooseValueHandler);
    }

    /**
     * Gets the single instance of GameView.
     *
     * @param username the username
     * @param players the players
     * @param constraints the constraints
     * @return single instance of GameView
     */
    public static GameView getInstance(String username, List<String> players, Map<String, Constraint[][]> constraints) {
        if (gameView == null) {
            new Thread(() -> startGameGUI(username, players, constraints)).start();
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
     * Sets the private objective cards.
     *
     * @param id the new private objective cards
     */
    void setPrivateObjective(int id) {
        cardBoard.setPrivateObjective(id);
    }

    /**
     * Sets the public objectives cards
     *
     * @param publicObjectives the new public objectives cards
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
     * Removes dice on mistaken placement
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
     * @param number the number of tokens to remove
     */
    void removeToken(int number){
        tokenGrid.getChildren().remove(0, number);

    }

    /**
     * Adds the token to tool
     *
     * @param num the num of token to add
     * @param toolCardView the tool card view
     */
    void addTokenTool(int num, ToolCardView toolCardView){
        toolCardView.addToken(num);
    }


    /**
     * Initialize game scene
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
                        "images/gameGuiImages/MainGameBoard.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        anchorPane.resize(GUIManager.getGameWindowWidth(), GUIManager.getGameWindowHeight());
    }

    /**
     * Sets the token associated to the window
     *
     * @param tokenNumber the new token
     */
    public void setToken(int tokenNumber) {
        tokenGrid = new GridPane();
        for (int i = 0; i < tokenNumber; i++) {
            tokenGrid.add(new TokenView(), 1, i); //pay attention : manage click even when all tokens are removed : IndexOutOfBoundException
        }

        AnchorPane.setTopAnchor(tokenGrid, GUIManager.getGameHeightPixel(20));
        AnchorPane.setLeftAnchor(tokenGrid, GUIManager.getGameWidthPixel(35));
        anchorPane.getChildren().addAll(tokenGrid);

    }

    /*
     * starts the game scene
     */
    @Override
    public void start(Stage primaryStage) {
        initialize();
        if(primaryStage == null)
            primaryStage = new Stage();
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
    private static void startGameGUI(String username, Stage stage, List<String> players, Map<String, Constraint[][]> constraints) {
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
    private static void startGameGUI(String username, List<String> players, Map<String, Constraint[][]> constraints) {
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
    public static GameView getInstance(String username, Stage stage, List<String> players, Map<String, Constraint[][]> constraints) {
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
     * Creates the scene, adding main graphic elements.
     *
     * @param primaryStage the primary stage
     */
    private void createScene(Stage primaryStage) {

        players.forEach(user -> windows.put(user, new WindowView(constraints.get(user))));
        hBox.setSpacing(15);
        players.forEach(plr -> {
            if(!plr.equals(username)) {
                windows.get(plr).windowResize();
                hBox.getChildren().add(windows.get(plr));
            }

        });
        setHBox();
        setWindow();
        setRoundtrack();
        setCardBoard();
        setEndTurnButton();
        setCardPreviewButtons();
        setWindowButton();
        setNotification();
        setTimer();
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
     * Notify turn begun
     */
    void notifyTurn() {
        components.forEach(node -> node.setDisable(false));
        cardBoard.enableToolBuy();
        timer.setVisible(true);
    }

    /**
     * Notify player is offline.
     *
     * @param playerId the player id
     */
    void removePlayer(String playerId) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Game status update");
        alert.setHeaderText("Player disconnect");
        alert.setContentText(playerId + " disconnected");
        alert.showAndWait();
    }

    /**
     * Notify end of turn.
     */
    void notifyEndTurn() {
        timer.setText("");
        timer.setVisible(false);
        components.forEach(node -> node.setDisable(true));
        disableToolBuy();
        removeDicePrevContainer();
    }

    /**
     * Sets the dice on window.
     *
     * @param position the new dice window
     */
    void setDiceWindow(Position position) {
        windows.get(getUsername()).removeMistakenDice(position.getRow(), position.getCol());
    }

    /**
     * Sets the dice on opponent window when opponent places dice.
     *
     * @param username the username
     * @param dice the dice
     * @param position the position
     */
    void setOpponentWindow(String username, Dice dice, Position position) {
        windows.get(username).setDice(dice, position);
    }

    /**
     * Sets the window button handler to show opponents' windows on click
     *
     * @param windowButtonHandler the new window button handler
     */
    void setWindowButtonHandler(EventHandler<MouseEvent> windowButtonHandler){
      windowPrev.setOnMouseClicked(windowButtonHandler);
    }


    /**
     * Sets the horizontal box containing opponents' windows.
     */
    private void setHBox(){
        AnchorPane.setBottomAnchor(hBox, GUIManager.getGameHeightPixel(30));
        AnchorPane.setRightAnchor(hBox, GUIManager.getGameWidthPixel(6));
    }

    /**
     * Sets the window associated to the player.
     */
    private void setWindow(){
        frame.addWindowToFrame(windows.get(username));
        AnchorPane.setBottomAnchor(frame, GUIManager.getGameHeightPixel(8));
        AnchorPane.setLeftAnchor(frame, GUIManager.getGameWidthPixel(4));
        anchorPane.getChildren().addAll(frame);
    }

    /**
     * Sets the roundtrack graphic elements.
     */
    private void setRoundtrack(){
        ImageView roundImage = new ImageView();
        roundImage.setImage(new Image(RoundTrackView.class.getResourceAsStream("/images/gameGuiImages/roundtrack.png"), GUIManager.getGameWidthPixel(45), GUIManager.getGameHeightPixel(13), true, false));
        AnchorPane.setRightAnchor(roundImage, GUIManager.getGameWidthPixel(4) );
        AnchorPane.setTopAnchor(roundImage, GUIManager.getGameHeightPixel(6));
        anchorPane.getChildren().add(roundImage);
        AnchorPane.setTopAnchor(roundTrackView, GUIManager.getGameHeightPixel(9.2));
        AnchorPane.setRightAnchor(roundTrackView, GUIManager.getGameWidthPixel(4.7));
        anchorPane.getChildren().add(roundTrackView);

    }

    /**
     * Sets the window preview button.
     */
    private void setWindowButton() {
        windowPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/gameGuiImages/windowPrev.png")));
        windowPrev.setPreserveRatio(true);
        windowPrev.setFitHeight(GUIManager.getGameHeightPixel(40));
        AnchorPane.setRightAnchor(windowPrev, GUIManager.getGameHeightPixel(4));
        AnchorPane.setBottomAnchor(windowPrev, GUIManager.getGameHeightPixel(30));
        anchorPane.getChildren().add(windowPrev);
    }

    /**
     * Sets the card board containing the various cards.
     */
    private void setCardBoard(){
        AnchorPane.setBottomAnchor(cardBoard, GUIManager.getGameHeightPixel(0));
        AnchorPane.setRightAnchor(cardBoard, GUIManager.getGameWidthPixel(0));
        anchorPane.getChildren().add(cardBoard);
    }



    /**
     * Shows opponents' windows.
     */
    void showOtherWindows(){
        anchorPane.getChildren().addAll(hBox);
    }

    /**
     * Hide opponents' windows.
     */
    void hideOtherWindows(){
        anchorPane.getChildren().remove(hBox);
    }

    /**
     * Sets the card preview buttons.
     */
    private void setCardPreviewButtons(){
        toolcardPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/gameGuiImages/toolCard.png")));
        toolcardPrev.setPreserveRatio(true);
        privateObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/gameGuiImages/privateObj.png")));
        privateObjPrev.setPreserveRatio(true);
        publicObjPrev = new ImageView(new Image(GameView.class.getResourceAsStream("/images/gameGuiImages/publicObj.png")));
        publicObjPrev.setPreserveRatio(true);
        AnchorPane.setBottomAnchor(toolcardPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(toolcardPrev, GUIManager.getGameWidthPixel(10));
        AnchorPane.setBottomAnchor(privateObjPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(privateObjPrev, GUIManager.getGameWidthPixel(22));
        AnchorPane.setBottomAnchor(publicObjPrev, GUIManager.getGameHeightPixel(6));
        AnchorPane.setRightAnchor(publicObjPrev, GUIManager.getGameWidthPixel(34));
        anchorPane.getChildren().addAll(toolcardPrev, privateObjPrev, publicObjPrev);

    }

    /**
     * Sets the notification graphic style.
     */
    private void setNotification() {
        notification = new Label();
        notification.setAlignment(Pos.CENTER);
        notification.setTextFill(Color.web("#000000"));
        notification.setFont(Font.font("System", FontWeight.BOLD, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        notification.setStyle("-fx-background-color: #d57322;"
        );
        AnchorPane.setTopAnchor(notification, GUIManager.getGameHeightPixel(7));
        AnchorPane.setLeftAnchor(notification, GUIManager.getGameWidthPixel(4));
        anchorPane.getChildren().add(notification);
    }

    /**
     * Sets the timer graphic style.
     */
    private void setTimer() {
        timer = new Label();
        timer.setAlignment(Pos.CENTER);
        timer.setTextFill(Color.web("#000000"));
        timer.setFont(Font.font("System", FontWeight.BOLD, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        timer.setStyle("-fx-background-color: #d57322;");
        AnchorPane.setTopAnchor(timer, GUIManager.getGameHeightPixel(11));
        AnchorPane.setLeftAnchor(timer, GUIManager.getGameWidthPixel(4));
        anchorPane.getChildren().add(timer);
    }

    /**
     * Sets the remaining time.
     *
     * @param time the current remaining time
     */
    void setTimeRemaining(int time) {
        if(timer != null)
            timer.setText("Remaining time : " + time);
    }

    /**
     * Sets the notification.
     *
     * @param message the new notification
     */
    void setNotification(String message) {
        notification.setText(message);
        if(!notifying) {
            notifying=true;
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(NOTIFICATION_TIME),
                    ae -> setNotification("")));
            timeline.play();
        }
        else notifying=false;
    }

    /**
     * Sets the tool preview listener to show toolcards on click
     *
     * @param cardHandler the tool preview listener
     */
    void setToolPreviewListener(EventHandler<MouseEvent> cardHandler) {
        toolcardPrev.setOnMouseClicked(cardHandler);
    }

    /**
     * Sets the private preview listener to show private objectives on click
     *
     * @param cardHandler the private preview listener
     */
    void setPrivatePreviewListener(EventHandler<MouseEvent> cardHandler){
        privateObjPrev.setOnMouseClicked(cardHandler);

    }
    
    /**
     * Sets the public preview listener to show public objectives on click
     *
     * @param cardHandler the public preview listener
     */
    void setPublicPreviewListener(EventHandler<MouseEvent> cardHandler){
        publicObjPrev.setOnMouseClicked(cardHandler);

    }

    /**
     * Show public objectives card.
     */
    void showPublicCard(){
        cardBoard.showPublicCards();
    }

    /**
     * Show private objectives card.
     */
    void showPrivateCard(){
        cardBoard.showPrivateCards();
    }

    /**
     * Show tool card.
     */
    void showToolCard(){
        cardBoard.showToolCards();
    }

    /**
     * Hide public objectives card.
     */
    void hidePublicCard(){
        cardBoard.hidePublicCards();
    }

    /**
     * Hide private objectives card.
     */
    void hidePrivateCard(){
        cardBoard.hidePrivateCards();
    }

    /**
     * Hide tool card.
     */
    void hideToolCard(){
        cardBoard.hideToolCards();
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    /**
     * Enable draft change value on relative toolcard usage
     *
     * @param changeValueHandler the change value handler
     */
    void enableDraftChangeValue(EventHandler<MouseEvent> changeValueHandler){
        draftView.enableDraftChangeValue(changeValueHandler);
    }

    /**
     * Disable draft click on already placed dice
     */
    public void disableDraftClick(){
        draftView.disableDraftClick();
    }

    /**
     * Enable window dice drag on relative toolcard usage
     *
     * @param enableWindowDragHandler the enable window drag handler
     */
    void enableWindowDiceDrag(EventHandler<MouseEvent> enableWindowDragHandler){
        windows.get(username).enableWindowDiceDrag(enableWindowDragHandler);
    }

    /**
     * Disable round track click on toolcard usage ended
     */
    void disableRoundTrackClick(){
        roundTrackView.disableClick();
    }

    /**
     * Disable window dice drag on toolcard usage ended.
     */
    void disableWindowDiceDrag(){
         windows.get(username).disableDiceDrag();
    }

    /**
     * Disable tool buy.
     */
    void disableToolBuy(){
        this.cardBoard.disableToolBuy();
    }

    /**
     * Sets the end turn button.
     */
    private void setEndTurnButton(){
        AnchorPane.setLeftAnchor(endTurn, GUIManager.getGameWidthPixel(35));
        AnchorPane.setBottomAnchor(endTurn, GUIManager.getGameHeightPixel(7));
        anchorPane.getChildren().add(endTurn);
    }

    /**
     * Gets the window as byte array.
     *
     * @return the window as byte array
     */
    byte[] getWindowAsByteArray() {
        File file = new File("window_temp.png");
        if (!file.exists())
            try {
                if (!file.createNewFile())
                    return new byte[0];
            } catch (IOException exc) {
                return new byte[0];
            }
        WritableImage writableImage = new WritableImage((int) windows.get(username).getWidth(), (int) windows.get(username).getHeight());
        Platform.runLater(() -> {
            synchronized (writableImage) {
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                windows.get(username).snapshot(params, writableImage);
            }
        });
        Platform.runLater(() -> {
            synchronized (writableImage) {
                try {
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                }
                catch (IOException exc) {
                    LOGGER.log(Level.SEVERE, exc::getMessage);
                }
            }
        });
        synchronized (writableImage) {
            try (FileInputStream fis = new FileInputStream(file)) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                for (int readNum; (readNum = fis.read(buff)) != -1; ) {
                    baos.write(buff, 0, readNum);
                }
                return baos.toByteArray();
            } catch (IOException exc) {
                return new byte[0];
            }
        }
    }


}