package it.polimi.ingsw.sagrada.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WindowChoiceGuiView extends Application {
    private final static int NUM_WINDOW = 4;
    private double windowHeight;
    private double windowWidth;

    private AnchorPane anchorPane;
    private Label title;
    private List<ImageView> imageViewList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();
        primaryStage.setTitle("Window chooser");
        primaryStage.setResizable(false);
        title = new Label();
        title.setText("Choose a window");
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setFont(Font.font("System", FontWeight.BOLD, 36));
        title.setStyle("-fx-background-color: #d57322;" +
                "-fx-border-color: #000000"
        );
        anchorPane.setTopAnchor(imageViewList.get(0), getHeightPixel(35));
        anchorPane.setTopAnchor(imageViewList.get(1), getHeightPixel(35));
        anchorPane.setLeftAnchor(imageViewList.get(0), getWidthPixel(10));
        anchorPane.setRightAnchor(imageViewList.get(1), getWidthPixel(10));
        anchorPane.setBottomAnchor(imageViewList.get(2), getHeightPixel(10));
        anchorPane.setLeftAnchor(imageViewList.get(2), getWidthPixel(10));
        anchorPane.setBottomAnchor(imageViewList.get(3), getHeightPixel(10));
        anchorPane.setRightAnchor(imageViewList.get(3), getWidthPixel(10));
        imageViewList.forEach(img -> anchorPane.getChildren().add(img));
        anchorPane.setTopAnchor(title, getHeightPixel(13));
        anchorPane.setLeftAnchor(title, 105.0);
        anchorPane.setRightAnchor(title, 105.0);
        anchorPane.getChildren().add(title);

        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialize() {
        imageViewList = new ArrayList<>();
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MatchLobbyBackground.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowHeight = gd.getDisplayMode().getHeight()*0.7;
        windowWidth = windowHeight*0.76;
        anchorPane.resize(windowWidth, windowHeight);
        imageViewList.add(new ImageView(new File("src/main/resources/images/window_images/window1.jpg").toURI().toString()));
        imageViewList.add(new ImageView(new File("src/main/resources/images/window_images/window1.jpg").toURI().toString()));
        imageViewList.add(new ImageView(new File("src/main/resources/images/window_images/window1.jpg").toURI().toString()));
        imageViewList.add(new ImageView(new File("src/main/resources/images/window_images/window1.jpg").toURI().toString()));
        imageViewList.forEach(img -> {
            img.setPreserveRatio(true);
            img.setFitWidth(getWidthPixel(40));
        });
    }

    private double getHeightPixel(int perc) {
        return (windowHeight*perc)/100;
    }

    private double getWidthPixel(int perc) {
        return (windowWidth*perc)/100;
    }
}
