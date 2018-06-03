package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiView;
import javafx.stage.Stage;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;

class GUIManager {

    private double windowHeight;
    private double windowWidth;
    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    static LobbyGuiView initLobbyGuiView(Stage stage) {
        return LobbyGuiView.init(stage);
    }

    static WindowChoiceGuiView initWindowChoiceGuiView(WindowResponse windowResponse) {
        return WindowChoiceGuiView.getInstance(windowResponse);
    }

    static GameView initGameView(List<String> players, DiceResponse diceResponse, Constraint[][] constraints) {
        return GameView.getInstance(players, diceResponse, constraints);
    }

    static double getWindowHeight() {
        return gd.getDisplayMode().getHeight() * 0.7;
    }

     static double getWindowWidth() {
        return gd.getDisplayMode().getHeight() * 0.7 * 0.76;
    }
}
