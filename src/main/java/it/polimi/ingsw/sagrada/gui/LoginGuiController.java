package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.network.client.ClientManager;
import it.polimi.ingsw.sagrada.network.security.Security;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGuiController {
    private static final Logger LOGGER = Logger.getLogger(LoginGuiController.class.getName());
    private static String username;
    private static String password;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox socketCheckBox;
    @FXML
    private CheckBox rmiCheckBox;
    @FXML
    private TextField usernameField;
    @FXML
    private Label errorText;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void initLoginGui() {
        socketCheckBox.selectedProperty().addListener(
                (observable, oldvalue, newvalue) -> {if(socketCheckBox.isSelected()) rmiCheckBox.setSelected(false);});
        rmiCheckBox.selectedProperty().addListener(
                (observable, oldvalue, newvalue) -> {if(rmiCheckBox.isSelected()) socketCheckBox.setSelected(false);});
        loginButton.armedProperty().addListener(
                (observable, oldvalue, newvalue) -> {
                    username = usernameField.getText();
                    password = passwordField.getText();
                    startConnection();
                }
        );
    }

    private void startConnection() {
        if(username.length()!=0 && password.length() != 0) {
            if (socketCheckBox.isSelected()) {
                executorService.submit(() -> {
                    try {
                        ClientManager.getSocketClient();
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error creating socket communication");
                    }
                });

            }
            else if (rmiCheckBox.isSelected()) {
                executorService.submit(() -> {
                    try {
                        ClientManager.getRMIClient();
                    } catch (RemoteException e) {
                        LOGGER.log(Level.SEVERE, "Error creating RMI communication");
                    }
                });

            }
            else errorText.setText("Please select a type of communication");
        }
        else errorText.setText("Invalid username or password");
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return Security.generateMD5Hash(password);
    }
}
