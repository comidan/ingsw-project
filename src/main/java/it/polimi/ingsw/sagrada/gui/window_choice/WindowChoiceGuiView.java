package it.polimi.ingsw.sagrada.gui.window_choice;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private static final String BASE_PATH = "src/main/resources/images/window_images/window";

    private double windowHeight;
    private double windowWidth;

    private AnchorPane anchorPane;
    private Label title;
    private List<WindowImage> imageViewList;

    public void setWindowCellListener(EventHandler<MouseEvent> handler) {
        imageViewList.forEach(img -> img.setOnMouseClicked(handler));
    }

    public WindowChoiceGuiView(WindowResponse windowResponse) {
        imageViewList = new ArrayList<>();

        windowResponse.getIds().forEach(id -> {
            String path = BASE_PATH+id.toString();
            imageViewList.add(new WindowImage(
                    new File(path+"Front.jpg").toURI().toString(),
                    id, WindowSide.FRONT));
            imageViewList.add(new WindowImage(
                    new File(path+"Rear.jpg").toURI().toString(),
                    id, WindowSide.REAR));
        });
        launch(WindowChoiceGuiView.class);
    }

    @Override
    public void start(Stage primaryStage) {
        initialize();
        primaryStage.setTitle("Window chooser");
        primaryStage.setResizable(false);

        AnchorPane.setTopAnchor(imageViewList.get(0), getHeightPixel(35));
        AnchorPane.setTopAnchor(imageViewList.get(1), getHeightPixel(35));
        AnchorPane.setLeftAnchor(imageViewList.get(0), getWidthPixel(10));
        AnchorPane.setRightAnchor(imageViewList.get(1), getWidthPixel(10));
        AnchorPane.setBottomAnchor(imageViewList.get(2), getHeightPixel(10));
        AnchorPane.setLeftAnchor(imageViewList.get(2), getWidthPixel(10));
        AnchorPane.setBottomAnchor(imageViewList.get(3), getHeightPixel(10));
        AnchorPane.setRightAnchor(imageViewList.get(3), getWidthPixel(10));
        imageViewList.forEach(img -> anchorPane.getChildren().add(img));
        AnchorPane.setTopAnchor(title, getHeightPixel(13));
        AnchorPane.setLeftAnchor(title, getWidthPixel(20));
        AnchorPane.setRightAnchor(title, getWidthPixel(20));
        anchorPane.getChildren().add(title);

        Scene scene = new Scene(anchorPane, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialize() {
        anchorPane = new AnchorPane();
        anchorPane.setStyle(
                "-fx-background-image: url(" +
                        "images/MatchLobbyBackground.png" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowHeight = gd.getDisplayMode().getHeight()*0.8;
        windowWidth = windowHeight*0.76;
        anchorPane.resize(windowWidth, windowHeight);

        imageViewList.forEach(img -> {
            img.setPreserveRatio(true);
            img.setFitWidth(getWidthPixel(40));
        });

        title = new Label();
        title.setText("Choose a window");
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setFont(Font.font("System", FontWeight.BOLD, 36*gd.getDisplayMode().getHeight()/1080));
        title.setStyle("-fx-background-color: #d57322;" +
                "-fx-border-color: #000000"
        );
    }

    private double getHeightPixel(int perc) {
        return (windowHeight*perc)/100;
    }

    private double getWidthPixel(int perc) {
        return (windowWidth*perc)/100;
    }

    public void setTitleMessage(String message) {
        title.setText(message);
    }
}
