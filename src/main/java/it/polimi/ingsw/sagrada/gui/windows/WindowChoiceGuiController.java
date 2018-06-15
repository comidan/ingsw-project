package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class WindowChoiceGuiController.
 */
public class WindowChoiceGuiController {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WindowChoiceGuiController.class.getName());
    
    /** The view. */
    private WindowChoiceGuiView view;
    
    /** The client. */
    private Client client;
    
    /** The chosen. */
    private boolean chosen;
    
    /** The window id. */
    private int windowId;
    
    /** The window side. */
    private WindowSide windowSide;

    /**
     * Instantiates a new window choice gui controller.
     *
     * @param view the view
     * @param client the client
     */
    public WindowChoiceGuiController(WindowChoiceGuiView view, Client client) {
        this.chosen = false;
        this.view = view;
        this.client = client;
        this.view.setWindowCellListener(event -> {
            if(!chosen) {
                WindowImage windowImage = (WindowImage)event.getSource();
                this.view.setNotificationMessage("Window selected, wait for the other player");
                try {
                    client.sendRemoteMessage(new WindowEvent(client.getId(), windowImage.getWindowId(), windowImage.getSide()));
                    windowId = windowImage.getWindowId();
                    windowSide = windowImage.getSide();
                    System.out.println("Window player " + client.getId() + " : " + windowImage.getSide() + " " + windowImage.getWindowId());
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "error sending windows event to server");
                }
            }
            else {
                this.view.setNotificationMessage("Window already selected, wait for the other player");
            }
            chosen = true;
        });
    }

    /**
     * Gets the window id.
     *
     * @return the window id
     */
    public int getWindowId() {
        return windowId;
    }

    /**
     * Gets the window side.
     *
     * @return the window side
     */
    public WindowSide getWindowSide() {
        return windowSide;
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return view.getStage();
    }
}
