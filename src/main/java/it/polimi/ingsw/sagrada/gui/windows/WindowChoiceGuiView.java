package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class WindowChoiceGuiView.
 */
public class WindowChoiceGuiView extends Application {
    
    /** The Constant BASE_PATH. */
    private static final String BASE_PATH = "/images/window_images/window";

    /** The window height. */
    private double windowHeight;
    
    /** The window width. */
    private double windowWidth;
    
    /** The window response. */
    private static WindowResponse windowResponse;
    
    /** The instance. */
    private static WindowChoiceGuiView instance;

    /** The anchor pane. */
    private AnchorPane anchorPane;
    
    /** The title. */
    private Label title;
    
    /** The notification. */
    private Label notification;
    
    /** The image view list. */
    private List<WindowImage> imageViewList;

    /**
     * Sets the window cell listener.
     *
     * @param handler the new window cell listener
     */
    public void setWindowCellListener(EventHandler<MouseEvent> handler) {
        imageViewList.forEach(img -> img.setOnMouseClicked(handler));
    }

    /**
     * Start game gui.
     *
     * @param windowResponse the window response
     * @param stage the stage
     */
    private static void startGameGui(WindowResponse windowResponse, Stage stage) {
        WindowChoiceGuiView.windowResponse = windowResponse;
        new WindowChoiceGuiView().start(stage);
    }

    /**
     * Gets the single instance of WindowChoiceGuiView.
     *
     * @param windowResponse the window response
     * @param stage the stage
     * @return single instance of WindowChoiceGuiView
     */
    public static WindowChoiceGuiView getInstance(WindowResponse windowResponse, Stage stage) {
        if (instance == null) {
            Platform.runLater(() -> startGameGui(windowResponse, stage));
            while (instance == null)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                    return instance;
                }
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) {
        initialize();
        setWIndowChoiceViewInstance(this);
        primaryStage.setTitle("Window chooser");
        primaryStage.setResizable(false);

        //Window
        AnchorPane.setTopAnchor(imageViewList.get(0), GUIManager.getHeightPixel(35));
        AnchorPane.setTopAnchor(imageViewList.get(1), GUIManager.getHeightPixel(35));
        AnchorPane.setLeftAnchor(imageViewList.get(0), GUIManager.getWidthPixel(10));
        AnchorPane.setRightAnchor(imageViewList.get(1), GUIManager.getWidthPixel(10));
        AnchorPane.setBottomAnchor(imageViewList.get(2), GUIManager.getHeightPixel(10));
        AnchorPane.setLeftAnchor(imageViewList.get(2), GUIManager.getWidthPixel(10));
        AnchorPane.setBottomAnchor(imageViewList.get(3), GUIManager.getHeightPixel(10));
        AnchorPane.setRightAnchor(imageViewList.get(3), GUIManager.getWidthPixel(10));
        imageViewList.forEach(img -> anchorPane.getChildren().add(img));
        //Title
        AnchorPane.setTopAnchor(title, GUIManager.getHeightPixel(13));
        AnchorPane.setLeftAnchor(title, GUIManager.getWidthPixel(20));
        AnchorPane.setRightAnchor(title, GUIManager.getWidthPixel(20));
        anchorPane.getChildren().add(title);
        //Notification
        AnchorPane.setTopAnchor(notification, GUIManager.getHeightPixel(20));
        AnchorPane.setLeftAnchor(notification, GUIManager.getWidthPixel(20));
        AnchorPane.setRightAnchor(notification, GUIManager.getWidthPixel(20));
        anchorPane.getChildren().add(notification);

        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sets the w indow choice view instance.
     *
     * @param wIndowChoiceViewInstance the new w indow choice view instance
     */
    private static void setWIndowChoiceViewInstance(WindowChoiceGuiView wIndowChoiceViewInstance) {
        WindowChoiceGuiView.instance = wIndowChoiceViewInstance;
    }

    /**
     * Initialize.
     */
    private void initialize() {
        imageViewList = new ArrayList<>();
        anchorPane = new AnchorPane();
        Image image = new Image(WindowChoiceGuiView.class.getResourceAsStream("/images/MatchLobbyBackground.png"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        anchorPane.setBackground(new Background(backgroundImage));
        windowHeight = GUIManager.getWindowHeight();
        windowWidth = GUIManager.getWindowWidth();

        anchorPane.resize(windowWidth, windowHeight);

        //windows upload
        windowResponse.getIds().forEach(id -> {
            InputStream pathFront = WindowChoiceGuiView.class.getResourceAsStream(BASE_PATH + id.toString() + "Front.jpg");
            InputStream pathRear = WindowChoiceGuiView.class.getResourceAsStream(BASE_PATH + id.toString() + "Rear.jpg");
            imageViewList.add(new WindowImage(pathFront, id, WindowSide.FRONT));
            imageViewList.add(new WindowImage(pathRear, id, WindowSide.REAR));
        });

        //Window styling
        imageViewList.forEach(img -> {
            img.setPreserveRatio(true);
            img.setFitWidth(GUIManager.getWidthPixel(38));
        });

        //Title styling
        title = new Label();
        title.setText("Choose a windows");
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setFont(Font.font("System", FontWeight.BOLD, GUIManager.getResizedFont(GUIManager.MAIN_TITLE)));
        title.setStyle("-fx-background-color: #d57322;" +
                "-fx-border-color: #000000"
        );

        //Notification styling
        notification = new Label();
        notification.setAlignment(Pos.CENTER);
        notification.setTextFill(Color.web("#000000"));
        notification.setFont(Font.font("System", FontWeight.BOLD, GUIManager.getResizedFont(GUIManager.TITLE_2)));
        notification.setTextAlignment(TextAlignment.CENTER);
        notification.setWrapText(true);
    }

    /**
     * Sets the notification message.
     *
     * @param message the new notification message
     */
    public void setNotificationMessage(String message) {
        notification.setText(message);
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }
}
