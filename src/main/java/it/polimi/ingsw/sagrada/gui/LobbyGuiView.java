package it.polimi.ingsw.sagrada.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class LobbyGuiView {
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

    private List<String> playerShown = new ArrayList<>();

    public void setPlayer(String username) {
        System.out.println("Setting player");
        playerShown.add(username);
        int position = playerShown.indexOf(username);
        System.out.println(position);
        switch (position) {
            case 0 : setFirstPlayer(username); break;
            case 1 : setSecondPlayer(username); break;
            case 2 : setThirdPlayer(username); break;
            case 3:  setFourthPlayer(username); break;
            default: break;
        }
    }

    public void removePlayer(String username) {
        int position = playerShown.indexOf(username);
        playerShown.remove(position);
        switch (position) {
            case 0 : setFirstPlayer("Waiting player..."); break;
            case 1 : setSecondPlayer("Waiting player..."); break;
            case 2 : setThirdPlayer("Waiting player..."); break;
            case 3:  setFourthPlayer("Waiting player..."); break;
            default: break;
        }
    }

    private void setFirstPlayer(String message) {
        firstPlayer.setText(message);
    }

    private void setSecondPlayer(String message) {
        secondPlayer.setText(message);
    }

    private void setThirdPlayer(String message) {
        thirdPlayer.setText(message);
    }

    private void setFourthPlayer(String message) {
        fourthPlayer.setText(message);
    }

    public void setTimer(String message) {
        timer.setText(message);
    }
}
