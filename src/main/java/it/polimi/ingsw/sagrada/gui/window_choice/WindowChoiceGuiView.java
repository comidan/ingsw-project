package it.polimi.ingsw.sagrada.gui.window_choice;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.GUIManager;
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

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WindowChoiceGuiView extends Application {
    private static final String BASE_PATH = "src/main/resources/images/window_images/window";

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

    private static void startGameGui(WindowResponse windowResponse) {
        WindowChoiceGuiView.windowResponse = windowResponse;
        new WindowChoiceGuiView().start(new Stage());
    }

    public static WindowChoiceGuiView getInstance(WindowResponse windowResponse) {
        if (instance == null) {
            Platform.runLater(() -> startGameGui(windowResponse));
            while (instance == null)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                    continue;
                }
        }
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        initialize();
        instance = this;
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

        //window upload
        windowResponse.getIds().forEach(id -> {
            String path = BASE_PATH+id.toString();
            imageViewList.add(new WindowImage(
                    new File(path+"Front.jpg").toURI().toString(),
                    id, WindowSide.FRONT));
            imageViewList.add(new WindowImage(
                    new File(path+"Rear.jpg").toURI().toString(),
                    id, WindowSide.REAR));
        });

        //Window styling
        imageViewList.forEach(img -> {
            img.setPreserveRatio(true);
            img.setFitWidth(GUIManager.getWidthPixel(40));
        });

        //Title styling
        title = new Label();
        title.setText("Choose a window");
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setFont(Font.font("System", FontWeight.BOLD, 36*GUIManager.getScreenHeight()/1080));
        title.setStyle("-fx-background-color: #d57322;" +
                "-fx-border-color: #000000"
        );
        //Notification styling
        notification = new Label();
        notification.setAlignment(Pos.CENTER);
        notification.setTextFill(Color.web("#000000"));
        notification.setFont(Font.font("System", FontWeight.BOLD, 24*GUIManager.getScreenHeight()/1080));
        notification.setTextAlignment(TextAlignment.CENTER);
        notification.setWrapText(true);
    }

    public void setNotificationMessage(String message) {
        notification.setText(message);
    }
}
