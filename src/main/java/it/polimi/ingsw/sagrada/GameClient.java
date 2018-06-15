package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.gui.login.LoginGuiView;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The Class GameClient.
 */
public class GameClient extends Application {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(LoginGuiView.class, args);
    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) {
        //path is relative to target because getResource is called

    }
}
