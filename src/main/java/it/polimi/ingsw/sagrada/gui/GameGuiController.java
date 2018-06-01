package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.network.client.Client;

import java.rmi.RemoteException;

public class GameGuiController {

    private GameView gameView;

    public GameGuiController(GameView gameView, Client client){
        this.gameView = gameView;
        this.gameView.setEndTurnHandler(event -> {
            EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
            try {
                client.sendResponse(endTurnEvent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        });

    }

}
