package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.gui.game.GameView;
import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiView;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;

public class GUIManager {
    private static final double RATIO = 0.76;
    private static final double RELATIVE_DIMENSION = 0.8;
    public static final double MAIN_TITLE = 36;
    public static final double TITLE_2 = 24;
    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static LobbyGuiView initLobbyGuiView(Stage stage) {
        return LobbyGuiView.init(stage);
    }

    public static WindowChoiceGuiView initWindowChoiceGuiView(WindowResponse windowResponse, Stage stage) {
        return WindowChoiceGuiView.getInstance(windowResponse, stage);
    }

    public static GameView initGameView(String username, Stage stage, List<String> players, List<Constraint[][]> constraints) {
        return GameView.getInstance(username, stage, players, constraints);
    }

    public static double getWindowHeight() {
        return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION;
    }

    public static double getWindowWidth() {
        return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION * RATIO;
    }

    public static double getScreenHeight() {
        return gd.getDisplayMode().getHeight();
    }

    public static double getScreenWidth() {
        return gd.getDisplayMode().getWidth();
    }

    public static double getHeightPixel(int perc) {
        return perc * getWindowHeight() / 100;
    }

    public static double getWidthPixel(int perc) {
        return perc * getWindowWidth() / 100;
    }

    public static double getResizedFont(double dim) {
        return dim * getScreenHeight() / 1080;
    }
}
