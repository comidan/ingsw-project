package it.polimi.ingsw.sagrada.gui.window_choice;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientBase;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowChoiceGuiController {
    private static final Logger LOGGER = Logger.getLogger(WindowChoiceGuiController.class.getName());
    private WindowChoiceGuiView view;
    private Client client;
    private boolean chosen;
    private int windowId;
    private WindowSide windowSide;

    public WindowChoiceGuiController(WindowChoiceGuiView view, Client client) {
        this.chosen = false;
        this.view = view;
        this.client = client;
        this.view.setWindowCellListener(event -> {
            if(!chosen) {
                WindowImage windowImage = (WindowImage)event.getSource();
                this.view.setNotificationMessage("Window selected, wait for the other players");
                try {
                    client.sendRemoteMessage(new WindowEvent(client.getId(), windowImage.getWindowId(), windowImage.getSide()));
                    windowId = windowImage.getWindowId();
                    windowSide = windowImage.getSide();
                    System.out.println("Window player " + client.getId() + " : " + windowImage.getSide() + " " + windowImage.getWindowId());
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "error sending window event to server");
                }
            }
            else {
                this.view.setNotificationMessage("Window already selected, wait for the other players");
            }
            chosen = true;
        });
    }

    public int getWindowId() {
        return windowId;
    }

    public WindowSide getWindowSide() {
        return windowSide;
    }

    public Stage getStage() {
        return view.getStage();
    }
}
