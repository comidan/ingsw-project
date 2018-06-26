package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.gui.game.GameView;
import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiView;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;


/**
 * The Class GUIManager.
 */
public class GUIManager {
    private GUIManager() {
        throw new IllegalStateException("Utility class");
    }
    
    /** The Constant RATIO. */
    private static final double RATIO = 0.76;

    private static final double GAME_RATIO = 1.778;
    
    /** The Constant RELATIVE_DIMENSION. */
    private static final double RELATIVE_DIMENSION = 0.9;
    
    /** The Constant MAIN_TITLE. */
    public static final double MAIN_TITLE = 36;
    
    /** The Constant TITLE_2. */
    public static final double TITLE_2 = 24;
    
    /** The Constant gd. */
    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    /**
     * Inits the lobby gui view.
     *
     * @param stage the stage
     * @return the lobby gui view
     */
    public static LobbyGuiView initLobbyGuiView(Stage stage) {
        return LobbyGuiView.init(stage);
    }

    /**
     * Inits the window choice gui view.
     *
     * @param windowResponse the window response
     * @param stage the stage
     * @return the window choice gui view
     */
    public static WindowChoiceGuiView initWindowChoiceGuiView(WindowResponse windowResponse, Stage stage) {
        return WindowChoiceGuiView.getInstance(windowResponse, stage);
    }

    /**
     * Inits the game view.
     *
     * @param username the username
     * @param stage the stage
     * @param players the players
     * @param constraints the constraints
     * @return the game view
     */
    public static GameView initGameView(String username, Stage stage, List<String> players, List<Constraint[][]> constraints) {
        return GameView.getInstance(username, stage, players, constraints);
    }

    /**
     * Gets the window height.
     *
     * @return the window height
     */
    public static double getWindowHeight() {
        return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION;
    }

    /**
     * Gets the window width.
     *
     * @return the window width
     */
    public static double getWindowWidth() {
        return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION * RATIO;
    }

    public static double getGameWindowWidth() { return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION * GAME_RATIO; }

    public static double getGameWindowHeight() { return gd.getDisplayMode().getHeight() * RELATIVE_DIMENSION; }

    /**
     * Gets the screen height.
     *
     * @return the screen height
     */
    public static double getScreenHeight() {
        return gd.getDisplayMode().getHeight();
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width
     */
    public static double getScreenWidth() {
        return gd.getDisplayMode().getWidth();
    }

    /**
     * Gets the height pixel.
     *
     * @param perc the perc
     * @return the height pixel
     */
    public static double getHeightPixel(int perc) {
        return perc * getWindowHeight() / 100;
    }

    /**
     * Gets the width pixel.
     *
     * @param perc the perc
     * @return the width pixel
     */
    public static double getWidthPixel(double perc) {
            return perc * getWindowWidth() / 100;
    }

    public static double getGameHeightPixel(double perc) {
        return perc * getGameWindowHeight() / 100;
    }

    public static double getGameWidthPixel(double perc) {
        return perc * getGameWindowWidth() / 100;
    }

    /**
     * Gets the resized font.
     *
     * @param dim the dim
     * @return the resized font
     */
    public static double getResizedFont(double dim) {
        return dim * getScreenHeight() / 1080;
    }
}
