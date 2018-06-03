package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiView;
import javafx.stage.Stage;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;

public class GUIManager {
    private static final double RATIO = 0.76;
    private static final double RELATIVE_DIMENSION = 0.8;
    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static LobbyGuiView initLobbyGuiView(Stage stage) {
        return LobbyGuiView.init(stage);
    }

    public static WindowChoiceGuiView initWindowChoiceGuiView(WindowResponse windowResponse) {
        return WindowChoiceGuiView.getInstance(windowResponse);
    }

    public static GameView initGameView(List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        return GameView.getInstance(players, diceResponse, constraints);
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
        return (perc * getWindowHeight() / 100);
    }

    public static double getWidthPixel(int perc) {
        return (perc * getWindowWidth() / 100);
    }

}
