package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.RuleResponse;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameGuiManager {

    private static final Logger LOGGER = Logger.getLogger(GameGuiManager.class.getName());

    private GameView gameView;
    private ClickedObject clickedObject;
    private DraftView draftView;
    private RoundtrackView roundtrackView;
    private CellView lastMove;

    public GameGuiManager(GameView gameView, Client client) {
        this.clickedObject = new ClickedObject();
        this.gameView = gameView;
        this.draftView = this.gameView.getDraftView();
        this.roundtrackView = this.gameView.getRoundtrackView();
        this.gameView.setEndTurnHandler(event -> {
            EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
            try {
                client.sendResponse(endTurnEvent);
                gameView.notifyEndTurn();
                System.out.println("Notified end turn");
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }

        });

        this.gameView.setDraftClickHandler(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);
            System.out.println("Selected dice " + diceView.getValue() + " " + diceView.getColor());
        });

        this.gameView.setCellClickListener(event ->
        {
            DiceView diceView = clickedObject.getClickedDice();
            System.out.println(diceView == null);
            if(diceView !=null) {
                CellView cellView = (CellView) event.getSource();
                System.out.println(cellView.isOccupied());
                if (!cellView.isOccupied()) {
                    lastMove = cellView;
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
                        System.out.println("Notified dice move");
                    } catch (RemoteException e) {
                        LOGGER.log(Level.SEVERE, e::getMessage);
                    }
                    clickedObject.setClickedDice(null);
                }
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
        gameView.notifyEndTurn();
    }

    private void setDraftListener() {
        this.gameView.setDraftClickHandler(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);
            System.out.println("Selected dice " + diceView.getValue() + " " + diceView.getColor());
        });
    }

    private void setRoundTrackClick(){
        this.gameView.setRoundtrackClickHandler(event -> {
            DiceView clickedDice = (DiceView) event.getSource();
            clickedObject.setClickedDice(clickedDice);
        });
    }


    //method to call this on server demand must be created
    private void addDiceRoundtrack(List<DiceView> diceViewList, int roundNumber){
        this.gameView.setRoundtrackImage(diceViewList, roundNumber);
    }

    public void setToken(int tokenNumber){
        this.gameView.setToken(tokenNumber);
    }

    //method to call this on server demand must be created
    public void removeMistakenDice(int row, int col){
        this.gameView.removeMistakenDice(row, col);
    }

    public void setDraft(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            gameView.setDraft(diceResponse);
            setDraftListener();
        });

    }

    public void notifyTurn() {
        gameView.notifyTurn();
    }

    public void notifyMoveResponse(RuleResponse ruleResponse) {
        Platform.runLater(() -> {
            if(!ruleResponse.isMoveValid()) {
                System.out.println("Removing Dice");
                lastMove.removeMistakenDice();
            }
        });

    }
}