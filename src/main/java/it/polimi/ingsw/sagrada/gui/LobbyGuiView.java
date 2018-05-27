package it.polimi.ingsw.sagrada.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyGuiView implements LobbyGuiViewInterface {
    @FXML
    private Label firstPlayer;
    @FXML
    private Label secondPlayer;
    @FXML
    private Label thirdPlayer;
    @FXML
    private Label fourthPlayer;
    @FXML
    private Label timer;

    @Override
    public void setFirstPlayer(String message) {
        firstPlayer.setText(message);
    }
    @Override
    public void setSecondPlayer(String message) {
        secondPlayer.setText(message);
    }
    @Override
    public void setThirdPlayer(String message) {
        thirdPlayer.setText(message);
    }
    @Override
    public void setFourthPlayer(String message) {
        fourthPlayer.setText(message);
    }
    @Override
    public void setTimer(String message) {
        timer.setText(message);
    }
}
