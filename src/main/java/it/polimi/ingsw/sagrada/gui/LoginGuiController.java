package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.network.client.ClientManager;
import it.polimi.ingsw.sagrada.network.security.Security;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGuiController {
    private static final Logger LOGGER = Logger.getLogger(LoginGuiView.class.getName());
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static LoginGuiView loginGuiView;

    public LoginGuiController(LoginGuiView loginGuiView, Stage window) {
        this.loginGuiView = loginGuiView;
        this.loginGuiView.addLoginButtonListener( event -> {
            if(loginGuiView.isCredentialCorrect()) {
                if(loginGuiView.getSelectedCommunication().equals("Socket")) {
                    executorService.submit(() -> {
                        try {
                            ClientManager.getSocketClient();
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE, "Error creating socket communication");
                        }
                    });
                }
                else {
                    executorService.submit(() -> {
                        try {
                            ClientManager.getRMIClient();
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE, "Error creating socket communication");
                        }
                    });
                }
                //change scene
                System.out.println("Changing scene");
                loginGuiView.changeScene();
            }
            else loginGuiView.setErrorText("Invalid username or password");
        });
    }

    public static String getUsername() {
        return loginGuiView.getUsername();
    }

    public static String getPassword() {
        return Security.generateMD5Hash(loginGuiView.getPassword());
    }
}
