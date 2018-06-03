package it.polimi.ingsw.sagrada.gui.window_choice;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;
import it.polimi.ingsw.sagrada.network.client.ClientBase;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowChoiceGuiController {
    private static final Logger LOGGER = Logger.getLogger(WindowChoiceGuiController.class.getName());
    private WindowChoiceGuiView view;
    private ClientBase client;

    public WindowChoiceGuiController(WindowChoiceGuiView view, ClientBase client) {
        this.view = view;
        this.client = client;
        this.view.setWindowCellListener(event -> {
            WindowImage windowImage = (WindowImage)event.getSource();
            try {
                this.view.setTitleMessage("Wait other players");
                client.sendResponse(new WindowEvent(client.getId(), windowImage.getWindowId(), windowImage.getSide()));
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, "error sending window event to server");
            }
        });
    }
}