package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.network.client.Client;

import java.rmi.RemoteException;

public class GameGuiController {

    private GameView gameView;
    private ClickedObject clickedObject;
    private DraftView draftView;

    public GameGuiController(GameView gameView, Client client) {
        this.clickedObject = new ClickedObject();
        this.gameView = gameView;
        this.draftView = this.gameView.getDraftView();
        this.gameView.setEndTurnHandler(event -> {
            EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
            try {
                client.sendResponse(endTurnEvent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        });

        this.gameView.setDraftClickHandler(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);


        });

        this.gameView.setCellClickListener(event ->
        {    DiceView diceView = clickedObject.getClickedDice();
             draftView.removeDiceView(diceView);

             String username = this.gameView.getUsername();
             int idDice = diceView.getDiceID();
             CellView cellView = (CellView) event.getSource();
             int row = cellView.getRow();
             int col = cellView.getCol();
             Position position = new Position(row, col);

             //WHAT IS SOURCE???

             DiceEvent diceEvent = new DiceEvent(username, idDice, position, null);
            try {
                client.sendResponse(diceEvent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        });

    }



}
