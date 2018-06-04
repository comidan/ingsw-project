package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameGuiController {

    private static final Logger LOGGER = Logger.getLogger(GameGuiController.class.getName());

    private GameView gameView;
    private ClickedObject clickedObject;
    private DraftView draftView;
    private RoundtrackView roundtrackView;

    public GameGuiController(GameView gameView, Client client) {
        this.clickedObject = new ClickedObject();
        this.gameView = gameView;
        this.draftView = this.gameView.getDraftView();
        this.roundtrackView = this.gameView.getRoundtrackView();
        this.gameView.setEndTurnHandler(event -> {
            EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
            try {
                client.sendResponse(endTurnEvent);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }

        });

        this.gameView.setDraftClickHandler(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);
        });

        this.gameView.setCellClickListener(event ->
        {
            DiceView diceView = clickedObject.getClickedDice();
            if(diceView !=null) {
                CellView cellView = (CellView) event.getSource();
                cellView.setImageCell(diceView);
                draftView.removeDiceView(diceView);

                String username = this.gameView.getUsername();
                int idDice = diceView.getDiceID();
                int row = cellView.getRow();
                int col = cellView.getCol();
                Position position = new Position(row, col);
                DiceEvent diceEvent = new DiceEvent(username, idDice, position, "draft");
                try {
                    client.sendResponse(diceEvent);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
                clickedObject.setClickedDice(null);
            }
        });

        this.gameView.setToolClickHandler(event -> {
            ToolCardView toolCardView = (ToolCardView) event.getSource();
            int tokenNumber;
            if(toolCardView.getTokenNumber() == 0)
                tokenNumber = 1;
            else tokenNumber = 2;
            gameView.removeToken(tokenNumber);
            toolCardView.addToken();
            //client.sendResponse(ToolEvent(toolCardView.getToolId()));

        });
        setRoundTrackClick();

        Platform.runLater(() -> setToken(3));
    }


    private void setRoundTrackClick(){
        this.gameView.setRoundtrackClickHandler(event -> {
            DiceView clickedDice = (DiceView) event.getSource();
            clickedObject.setClickedDice(clickedDice);
        });
    }

    private void addDiceRoundtrack(List<DiceView> diceViewList, int roundNumber){

        this.gameView.setRoundtrackImage(diceViewList, roundNumber);
    }

    public void setToken(int tokenNumber){
        this.gameView.setToken(tokenNumber);
    }

}