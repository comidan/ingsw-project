package it.polimi.ingsw.sagrada.gui.test;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//done for now

//ALL IDS AND IDLISTS MUST BE REPLACED WITH MODEL ELEMENT THAT CONTAINS ID OR DIRECTLY IMAGE REFERENCE

public class MainPane extends Application {
    private Stage primaryStage;
    private AnchorPane root = new AnchorPane();
    private List<Integer> idList; // to be removed and accessed as parameter
    private List<Integer> diceIdList;
    private int myId; // to be removed and accessed as parameter
    private List<WindowView> otherWindowViews;
    private List<WindowModel> otherWindowModels;
    private MyWindowView myWindow;
    private WindowModel myWindowModel;
    private WindowModelInterface windowModel;
    private GameModel gameModel;
    private PlayerModel myPlayer;

    public MainPane() {
        this.windowModel = new WindowModel();
        this.gameModel = new GameModel();
        this.otherWindowViews = new ArrayList<>();
        this.otherWindowModels = new ArrayList<>();
    }

    // to be removed, this must be done in controller
    private void setId() {
        idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        this.myId = 4;
        diceIdList = new ArrayList<>();
        diceIdList.add(36);
        diceIdList.add(21);
        diceIdList.add(56);
        diceIdList.add(21);
        diceIdList.add(56);
    }

    //this is okay
    @Override
    public void start(Stage primaryStage) {
        createScene(primaryStage);
        setId();
        setMyView();
        myWindow = new MyWindowView(windowModel, 1); //this windowmodel must be replaced with attribute mywindowmodel when ready, see setmyview for explaination about mywindowmodel
        root.setBottomAnchor(myWindow, 0.0);
        root.setRightAnchor(myWindow, 460.0);
        root.getChildren().add(myWindow);
        setOtherWindows(idList); //TO BE REPLACED WITH OTHERWINDOWMODELS LIST
        setDraft(diceIdList); //TO BE REPLACED WITH DICE MODEL OBJECT LIST


        primaryStage.show();
    }


    public void setMyView() {
        otherWindowModels = gameModel.getPlayerList().stream().filter(player -> !player.equals(myPlayer)).map(PlayerModel::getWindowModel).collect(Collectors.toList());

        //until playerList is empty will always throw null pointer exception, remove comment after passing playerlist from model


        /* myWindowModel = gameModel.getPlayerList().stream().filter(player -> player.equals(myPlayer)).map(PlayerModel::getWindowModel).reduce((a, b) -> {
            throw new IllegalStateException("Too many player instances:");
        }).get();*/

    }

    public void setOtherWindows(List<Integer> idList) {
        int size = idList.size();
        if (size == 1 || size == 3) {

            WindowView windowView = new WindowView(windowModel, idList.get(0), 300);
            root.setTopAnchor(windowView, 0.0);
            root.setRightAnchor(windowView, 565.0);
            root.getChildren().add(windowView);
        }

        if (size == 3) {
            WindowView windowView2 = new WindowView(windowModel, idList.get(1), 300);
            root.setTopAnchor(windowView2, 0.0);
            root.setLeftAnchor(windowView2, 80.0);
            root.getChildren().add(windowView2);

            WindowView windowView3 = new WindowView(windowModel, idList.get(2), 300);
            root.setTopAnchor(windowView3, 00.0);
            root.setRightAnchor(windowView3, 80.0);
            root.getChildren().add(windowView3);


        } else if (size == 2) {

            WindowView windowView = new WindowView(windowModel, idList.get(0), 300);
            root.setTopAnchor(windowView, 0.0);
            root.setRightAnchor(windowView, 300.0);
            root.getChildren().add(windowView);


            WindowView windowView2 = new WindowView(windowModel, idList.get(1), 300);
            root.setTopAnchor(windowView2, 0.0);
            root.setLeftAnchor(windowView2, 300.0);
            root.getChildren().add(windowView2);

        }


    }


    //this is okay
    public void createScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setFullScreen(true);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        root.setStyle(
                "-fx-background-image: url(" +
                        "images/shapes.jpg" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        primaryStage.setScene(new Scene(root));


    }


    //button must be placed somewhere else
    public void setDraft(List<Integer> Id) {


        DraftView draftView = new DraftView();
        draftView.setDraft(idList);
        root.setBottomAnchor(draftView, 30.0);
        root.setLeftAnchor(draftView, 80.0);
        root.getChildren().add(draftView);


    }

    // this is okay


}

