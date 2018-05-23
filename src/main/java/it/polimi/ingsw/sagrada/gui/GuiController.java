package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.network.client.ClientManager;
import it.polimi.ingsw.sagrada.network.security.Security;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiController {
    private static final Logger LOGGER = Logger.getLogger(GuiController.class.getName());
    private static String username;
    private static String password;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private CheckBox rmiCheckBox;
    @FXML
    private CheckBox socketCheckBox;


    @FXML
    private void handleLoginButtonAction() throws IOException { //check for valid username and password
        username = usernameField.getText();
        password = passwordField.getText();
        if(username.length()!=0 && password.length() != 0) {
            if (socketCheckBox.isSelected()) ClientManager.getSocketClient();
            else if (rmiCheckBox.isSelected()) ClientManager.getRMIClient();
            else LOGGER.log(Level.SEVERE, () -> "Something gone wrong in selecting type of connection");
        }
        else LOGGER.log(Level.SEVERE, () -> "Invalid username or password");
    }

    @FXML
    private void handleSocketCheckBoxAction() {
        if(socketCheckBox.isSelected()) rmiCheckBox.setSelected(false);
    }

    @FXML
    private void handleRmiCheckBoxAction() {
        if(rmiCheckBox.isSelected()) socketCheckBox.setSelected(false);
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return Security.generateMD5Hash(password);
    }
}
