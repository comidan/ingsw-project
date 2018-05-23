package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.IOException;

public class GuiController {
    @FXML
    private CheckBox socketCheckBox;

    @FXML
    private CheckBox rmiCheckBox;

    @FXML
    private void handleLoginButtonAction() throws IOException {
        // Button was clicked, do something...
        Client client;
        System.out.println("Login pressed");
    }

    @FXML
    private void handleSocketCheckBoxAction(ActionEvent event) {
        if(socketCheckBox.isSelected()) System.out.println("Socket1");
        System.out.println("Socket2");
    }
}
