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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WindowChoiceGuiView extends Application {
    private static final String BASE_PATH = "/images/window_images/window";

    private double windowHeight;
    private double windowWidth;
    private static WindowResponse windowResponse;
    private static WindowChoiceGuiView instance;

    private AnchorPane anchorPane;
    private Label title;
    private Label notification;
    private List<WindowImage> imageViewList;

    public void setWindowCellListener(EventHandler<MouseEvent> handler) {
        imageViewList.forEach(img -> img.setOnMouseClicked(handler));
    }

    private static void startGameGui(WindowResponse windowResponse, Stage stage) {
        WindowChoiceGuiView.windowResponse = windowResponse;
        new WindowChoiceGuiView().start(stage);
    }

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

    private static void setWIndowChoiceViewInstance(WindowChoiceGuiView wIndowChoiceViewInstance) {
        WindowChoiceGuiView.instance = wIndowChoiceViewInstance;
    }

    private void initialize() {
        imageViewList = new ArrayList<>();

        //GUI styling
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MatchLobbyBackground.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
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

    public void setNotificationMessage(String message) {
        notification.setText(message);
    }

    public Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }
}
