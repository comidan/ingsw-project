package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
    private int currentRound;

    public GameGuiManager(GameView gameView, Client client) {
        this.clickedObject = new ClickedObject();
        this.gameView = gameView;
        Platform.runLater(() -> {
            this.draftView = this.gameView.getDraftView();
            this.roundtrackView = this.gameView.getRoundtrackView();
            this.gameView.setEndTurnHandler(event -> {
                EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
                try {
                    client.sendRemoteMessage(endTurnEvent);
                    gameView.notifyEndTurn();
                    System.out.println("Notified end turn");
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }

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

                        String username = this.gameView.getUsername();
                        int idDice = diceView.getDiceID();
                        int row = cellView.getRow();
                        int col = cellView.getCol();
                        Position position = new Position(row, col);
                        DiceEvent diceEvent = new DiceEvent(username, idDice, position, "draft");
                        try {
                            client.sendRemoteMessage(diceEvent);
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
            setToken(3);
        });
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

    private void setDraft(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            gameView.setDraft(diceResponse);
            setDraftListener();
        });
    }

    public void setDiceList(DiceResponse diceResponse) {
        if(diceResponse.getDst().equals(CommandKeyword.DRAFT))
            setDraft(diceResponse);
        else if(diceResponse.getDst().equals(CommandKeyword.ROUND_TRACK))
            setRoundTrack(diceResponse);
    }

    private void setRoundTrack(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            List<DiceView> diceViews = new ArrayList<>();
            diceResponse.getDiceList().forEach(dice -> diceViews.add(new DiceView(Constraint.getColorConstraint(dice.getColor()), Constraint.getValueConstraint(dice.getValue()), dice.getId())));
            gameView.setRoundtrackImage(diceViews, currentRound);
            setDraftListener();
        });
    }

    public void setPrivateObjective(int id){
        Platform.runLater(() -> gameView.setPrivateObjective(id));
    }

    public void setPublicObjectives(List<Integer> publicObjectives) {
        Platform.runLater(() -> gameView.setPublicObjectives(publicObjectives));
    }

    public void setToolCards(List<Integer> toolCards) {
        Platform.runLater(() -> gameView.setToolCards(toolCards));
    }

    public void notifyTurn() {
        Platform.runLater(gameView::notifyTurn);
    }

    public void notifyEndTurn() {
        Platform.runLater(gameView::notifyEndTurn);
    }

    public void notifyMoveResponse(RuleResponse ruleResponse) {
        Platform.runLater(() -> {
            if(!ruleResponse.isMoveValid()) {
                System.out.println("Removing Dice");
                lastMove.removeMistakenDice();
            }
        });
    }

    public void setOpponentDiceResponse(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        gameView.setOpponentWindow(opponentDiceMoveResponse.getIdPlayer(),
                                   opponentDiceMoveResponse.getDice(),
                                   opponentDiceMoveResponse.getPosition());
    }

    public void setRound(int round) {
        currentRound = round;
    }
}