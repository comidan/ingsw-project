package it.polimi.ingsw.sagrada.gui.login;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.gui.lobby.LobbyGuiView;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.ClientManager;
import it.polimi.ingsw.sagrada.network.client.protocols.application.CommandManager;
import it.polimi.ingsw.sagrada.network.security.Security;
import javafx.application.Platform;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class LoginGuiAdapter.
 */
public class LoginGuiAdapter implements Channel<LoginState, Message> {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LoginGuiView.class.getName());
    
    /** The executor service. */
    private ExecutorService executorService = Executors.newCachedThreadPool();
    
    /** The login gui view. */
    private static LoginGuiView loginGuiView;
    
    /** The Constant dynamicRouter. */
    private static final DynamicRouter dynamicRouter = new MessageDispatcher();

    /**
     * Instantiates a new login gui adapter.
     *
     * @param loginGuiView the login gui view
     */
    LoginGuiAdapter(LoginGuiView loginGuiView) {
        dynamicRouter.subscribeChannel(LoginState.class, this);
        CommandManager.setFutureStage(loginGuiView.getWindow());
        LoginGuiAdapter.loginGuiView = loginGuiView;
        LoginGuiAdapter.loginGuiView.addLoginButtonListener(event -> {
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

    /**
     * Gets the username.
     *
     * @return the username
     */
    public static String getUsername() {
        return loginGuiView.getUsername();
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public static String getPassword() {
        return Security.generateMD5Hash(loginGuiView.getPassword());
    }

    /**
     * Notify error on wrong login.
     *
     * @param message the message
     */
    public static void notifyError(String message) {
        loginGuiView.setErrorText(message);
    }

    /**
     * Changes scene, moving on to match lobby
     */
    private void changeScene() {
        Platform.runLater(() -> {
            LobbyGuiView lobbyGuiView = GUIManager.initLobbyGuiView(loginGuiView.getWindow());
            CommandManager.setLobbyGuiView(lobbyGuiView);
        });
    }

    /**
     * Gets the dynamic router.
     *
     * @return the dynamic router
     */
    public static DynamicRouter getDynamicRouter() {
        return dynamicRouter;
    }

    /*
     * handles login responses
     */
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
                        LOGGER.log(Level.SEVERE, e::getMessage);
                        Thread.currentThread().interrupt();
                    }
                    changeScene();
                });
                break;
            }
            default: break;
        }
    }

    /*
    *
     */
    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException();
    }
}
