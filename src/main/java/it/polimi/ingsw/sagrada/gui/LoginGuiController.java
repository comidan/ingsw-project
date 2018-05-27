package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.ClientManager;
import it.polimi.ingsw.sagrada.network.security.Security;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGuiController implements Channel<LoginState, Message> {
    private static final Logger LOGGER = Logger.getLogger(LoginGuiView.class.getName());
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static LoginGuiView loginGuiView;
    private static final DynamicRouter dynamicRouter= new MessageDispatcher();

    public LoginGuiController(LoginGuiView loginGuiView) {
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

    public static void notifyError(String message) {
        loginGuiView.setErrorText(message);
    }

    public static DynamicRouter getDynamicRouter() {
        return dynamicRouter;
    }

    private void changeScene() {
        loginGuiView.changeScene();
    }

    @Override
    public void dispatch(LoginState message) {
        switch (message) {
            case AUTH_FAILED_USER_ALREADY_LOGGED: loginGuiView.setErrorText("User already logged"); break;
            case AUTH_OK: changeScene(); break;
            case AUTH_FATAL_ERROR: loginGuiView.setErrorText("Unexpected error"); break;
            case AUTH_WRONG_PASSWORD: loginGuiView.setErrorText("Wrong password"); break;
            case AUTH_FAILED_USER_NOT_EXIST: {
                loginGuiView.setErrorText("Registering...");
                executorService.submit(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        changeScene();
                    }
                    changeScene();
                });
                changeScene(); break;
            }
        }
    }

    @Override
    public void sendMessage(Message message) {

    }
}
