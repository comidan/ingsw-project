package it.polimi.ingsw.sagrada.gui.game;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.cards.ToolCardView;
import it.polimi.ingsw.sagrada.gui.components.CellView;
import it.polimi.ingsw.sagrada.gui.components.DicePrev;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.utils.ClickedObject;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.network.CommandKeyword;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class GameGuiAdapter.
 */
public class GameGuiAdapter {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(GameGuiAdapter.class.getName());

    /**
     * The game view.
     */
    private GameView gameView;

    /**
     * The clicked object where the dragged dice is saved.
     */
    private ClickedObject clickedObject;


    /**
     * The last move.
     */
    private CellView lastMove;

    /** The source of the clicked dice. */
    private String diceSource;


    /**
     * The current round.
     */
    private int currentRound;

    /**
     * Instantiates a new game gui adapter.
     *
     * @param gameView the game view
     * @param client   the client
     */
    public GameGuiAdapter(GameView gameView, Client client) {
        this.clickedObject = new ClickedObject();
        this.gameView = gameView;
        setEndTurnHandler(client);
        setCellHandler(client);
        setWindowButtonHandler();
        setCardPreviewListener();
    }


    /**
     * Sets the end turn handler to notify end of turn when end turn button is clicked.
     *
     * @param client the new end turn handler
     */
    private void setEndTurnHandler(Client client) {
        Platform.runLater(() -> {
            this.gameView.setEndTurnHandler(event -> {
                disableGuiElement();
                EndTurnEvent endTurnEvent = new EndTurnEvent(this.gameView.getUsername());
                try {
                    client.sendRemoteMessage(endTurnEvent);
                    client.setActive(false);
                    gameView.notifyEndTurn();
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Notified end turn");
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }

            });
        });
    }

    /**
     * Sets the window button handler to show other windows on preview click.
     */
    private void setWindowButtonHandler() {
        gameView.setWindowButtonHandler(event -> {
            gameView.showOtherWindows();
            gameView.setWindowButtonHandler(eventDone -> {
                gameView.hideOtherWindows();
                setWindowButtonHandler();
            });

        });
    }


        /**
         * Sets the cell handler to handle dice drop.
         *
         * @param client the new handler
         */
        private void setCellHandler(Client client) {

            GameGuiAdapter self = this;
            Platform.runLater(() -> {
                this.gameView.setCellClickListener(event ->
                        event.acceptTransferModes(TransferMode.COPY), event ->  {
                        DiceView diceView = clickedObject.getClickedDice();
                        if (diceView != null) {
                            CellView cellView = (CellView) event.getSource();
                            if (!cellView.isOccupied()) {
                                lastMove = cellView;
                                cellView.setImageCell(diceView);
                                clickedObject.setClickedDice(null);
                                String username = self.gameView.getUsername();
                                int idDice = diceView.getDiceID();
                                int row = cellView.getRow();
                                int col = cellView.getCol();
                                Position position = new Position(row, col);
                                Message message;
                                if(!diceSource.equals(CommandKeyword.VALUE_CHOOSER))
                                    message = new DiceEvent(username, idDice, position, diceSource);
                                else {
                                    message = new DiceValueEvent(new DiceEvent(username, idDice, position, diceSource),
                                            Constraint.getValueFromConstraint(diceView.getValue()));
                                    gameView.removeDicePrevContainer();
                                }
                                try {
                                    client.sendRemoteMessage(message);
                                } catch (RemoteException e) {
                                    LOGGER.log(Level.SEVERE, e::getMessage);
                                }

                                event.consume();
                            }
                        }

                });

            });
        }

    /**
     * Sets the draft listener to handle drag from draft.
     */
    private void setDraftListener() {
        Platform.runLater(() -> {
            this.gameView.setDraftClickHandler(event ->{

                    DiceView diceView = (DiceView) event.getSource();
                    clickedObject.setClickedDice(diceView);
                    Dragboard db = diceView.startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(diceView.getImage());
                    db.setContent(content);
                    event.consume();
                    diceSource = CommandKeyword.DRAFT;
                    Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Selected dice " + diceView.getValue() + " " + diceView.getColor());

                });
            });

    }

    /**
     * Sets the tool handler to handle tool buy
     *
     * @param client the new handler
     */
    public void setToolHandler(Client client) {
        Platform.runLater(() ->
            this.gameView.setToolClickHandler(event -> {
                ToolCardView toolCardView = (ToolCardView) event.getSource();
                ToolEvent toolEvent = new ToolEvent(gameView.getUsername(), toolCardView.getToolId());
                try {
                    client.sendRemoteMessage(toolEvent);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
                gameView.disableToolClickHandler();
            }));
    }

    /**
     * Sets the token associated to the window
     *
     * @param tokenNumber the new token window
     */
    private void setTokenWindow(int tokenNumber){
        setToken(tokenNumber);
    }



    /**
     * Sets the tool card prev listener to show the toolcard when the preview is clicked
     */
    private void setToolCardPrevListener(){
            gameView.setToolPreviewListener(event -> {
                gameView.showToolCard();
                gameView.setToolPreviewListener(eventDone -> {
                    gameView.hideToolCard();
                    setToolCardPrevListener();
                });}
            );

    }

    /**
     * Sets the private prev listener to show the private obj card when the preview is clicked
     */
    private void setPrivatePrevListener(){
            gameView.setPrivatePreviewListener(event -> {
                gameView.showPrivateCard();
                gameView.setPrivatePreviewListener(eventDone -> {
                    gameView.hidePrivateCard();
                    setPrivatePrevListener();
                });}
            );

    }

    /**
     * Sets the public prev listener to show the public obj card when the preview is clicked
     */
    private void setPublicPrevListener(){
            gameView.setPublicPreviewListener(event -> {
                gameView.showPublicCard();
                gameView.setPublicPreviewListener(eventDone -> {
                    gameView.hidePublicCard();
                    setPublicPrevListener();
                });}
            );
    }


    /**
     * Sets all card preview listener to show the cards when the preview is clicked
     */
    private void setCardPreviewListener(){
        Platform.runLater(() -> {
            setToolCardPrevListener();
            setPrivatePrevListener();
            setPublicPrevListener();
        });
    }

    /**
     * Enable draft click when relative toolcard is bought.
     * Tool effect: change dice value in draft adding one OR rolls again dice, according to value it gets;
     * can be used for toolcards: 1, 6, 10
     * @param client the client
     */
    private void enableDraftClick(Client client){
        this.gameView.enableDraftChangeValue(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            DiceDraftSelectionEvent diceDraftSelectionEvent = new DiceDraftSelectionEvent(gameView.getUsername(), diceView.getDiceID());
            try {
                client.sendRemoteMessage(diceDraftSelectionEvent);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
            disableGuiElement();
            // IMPORTANT : model must control that the used dice is actually the chosen dice for toolcard 1 and 10
        });
    }

    /**
     * Disable draft change value after relative tool has been used.
     */
    public void disableDraftChangeValue(){
        this.gameView.disableDraftClick();
    }


    /**
     * Enable window dice drag whenn toolcard is bought.
     * Tool effect: enable moving dice on your own window;
     * can be used for toolcards: 2, 3, 4, 12
     */
    private void enableWindowDiceDrag() {
        this.gameView.enableWindowDiceDrag(event -> {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);
            Dragboard db = diceView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(diceView.getImage());
            db.setContent(content);
            event.consume();
            diceSource = CommandKeyword.WINDOW;
            disableGuiElement();
        });
    }

    /**
     * Disable window dice drag.
     */
    private void disableWindowDiceDrag(){
            gameView.disableWindowDiceDrag();
        }

     /**
      * Sets the round track click when relative toolcard is used.
      * Tool effect: enable drag on dice in roundtrack
      * can be used for toolcards: 5
      *
      * @param client the client
      */

    private void enableRoundTrackClick(Client client){
        Platform.runLater(() -> {
            this.gameView.enableDraftChangeValue(event ->
            {
                DiceView diceView = (DiceView) event.getSource();
                DiceDraftSelectionEvent diceDraftSelectionEvent = new DiceDraftSelectionEvent(gameView.getUsername(), diceView.getDiceID());
                try {
                    client.sendRemoteMessage(diceDraftSelectionEvent);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
                disableDraftClick();
            });

            this.gameView.setRoundtrackClickHandler(event ->  {
                    DiceView diceView = (DiceView) event.getSource();
                    DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent = new DiceRoundTrackSelectionEvent(
                            gameView.getUsername(),
                            diceView.getDiceID(),
                            diceView.getRoundNumber());
                    try {
                        client.sendRemoteMessage(diceRoundTrackSelectionEvent);
                    } catch (RemoteException e) {
                        LOGGER.log(Level.SEVERE, e::getMessage);
                    }
                    disableRoundTrackClick();
            });
        });
    }

    /**
     * Enable round track click to get color when allowed.
     * can be used for toolcards: 12
     * @param client the client
     */
    private void enableRoundTrack(Client client) {
        this.gameView.setRoundtrackClickHandler(event -> {
            DiceView diceView = (DiceView) event.getSource();
            DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent = new DiceRoundTrackColorSelectionEvent(
                    gameView.getUsername(),
                    Constraint.getColorFromConstraint(diceView.getColor()));
            try {
                client.sendRemoteMessage(diceRoundTrackColorSelectionEvent);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
            disableRoundTrackClick();
        });
    }

    /**
     * Disable round track click.
     */
    private void disableRoundTrackClick(){
        gameView.disableRoundTrackClick();
    }

    /**
     * Sets the round track reconnection.
     *
     * @param roundTrack the new round track reconnection
     */
    public void setRoundTrackReconnection(List<List<Dice>> roundTrack) {
        List<List<DiceView>> roundTrackView = new ArrayList<>();
        roundTrack.forEach(round -> {
            List<DiceView> roundView = new ArrayList<>();
            round.forEach(dice -> roundView.add(new DiceView(Constraint.getColorConstraint(dice.getColor()), Constraint.getValueConstraint(dice.getValue()), dice.getId())));
            roundTrackView.add(roundView);
        });
        Platform.runLater(() -> gameView.setRoundTrackReconnection(roundTrackView));
    }

    /**
     * Disable draft click.
     */
    private void disableDraftClick(){
        gameView.disableDraftClick();
    }

    /**
     * Enable choose value for dice when relative toolcard is used.
     * can be used for toolcards: 11
     * @param color the color of the chosen dice
     * @param diceId the dice id of the chosen dice
     */
    public void enableChooseValue(Colors color, int diceId){
    Platform.runLater(() -> {
        gameView.showDicePrevContainer(color, diceId);

        gameView.setChooseValue(event -> {
            DiceView diceView = (DiceView) event.getSource();
            clickedObject.setClickedDice(diceView);
            Dragboard db = diceView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(diceView.getImage());
            db.setContent(content);
            event.consume();
            diceSource = CommandKeyword.VALUE_CHOOSER;
            disableGuiElement();
        });
    });

    }

    /**
     * Sets the token.
     *
     * @param tokenNumber the token number
     */
    public void setToken(int tokenNumber){
        Platform.runLater(() -> this.gameView.setToken(tokenNumber));
    }

    /**
     * Removes the mistaken dice.
     *
     * @param row the row
     * @param col the col
     */
    public void removeMistakenDice(int row, int col){
        Platform.runLater(() -> this.gameView.removeMistakenDice(row, col));
    }

    /**
     * Sets the draft at the beginning of the turn or on events involving the draft
     *
     * @param diceResponse the new draft
     */
    private void setDraft(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            gameView.setDraft(diceResponse);
            setDraftListener();
        });
    }

    /**
     * Sets the dice list for draft or roundtrack
     *
     * @param diceResponse the new dice list
     */
    public void setDiceList(DiceResponse diceResponse) {
        if(diceResponse.getDst().equals(CommandKeyword.DRAFT))
            setDraft(diceResponse);
        else if(diceResponse.getDst().equals(CommandKeyword.ROUND_TRACK)) {
            setRoundTrack(diceResponse);
        }
    }


    /**
     * Sets the round track at the end of the round
     *
     * @param diceResponse the new round track
     */
    private void setRoundTrack(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            List<DiceView> diceViews = new ArrayList<>();
            diceResponse.getDiceList().forEach(dice -> diceViews.add(new DiceView(Constraint.getColorConstraint(dice.getColor()), Constraint.getValueConstraint(dice.getValue()), dice.getId())));
            gameView.setRoundTrackImage(diceViews, currentRound, false);
        });
    }

    /**
     * Sets the round track on tool use
     *
     * @param roundTrackToolResponse the new round track
     */
    public void setRoundTrack(RoundTrackToolResponse roundTrackToolResponse) {
        DiceResponse diceResponse = roundTrackToolResponse.getDiceResponse();
        Platform.runLater(() -> {
            List<DiceView> diceViews = new ArrayList<>();
            diceResponse.getDiceList().forEach(dice -> diceViews.add(new DiceView(Constraint.getColorConstraint(dice.getColor()), Constraint.getValueConstraint(dice.getValue()), dice.getId())));
            gameView.setRoundTrackImage(diceViews, roundTrackToolResponse.getRoundNumber(), true);
        });
    }



    /**
     * Sets the private objective cards drawn for the game.
     *
     * @param id the new private objective cards
     */
    public void setPrivateObjective(int id){
        Platform.runLater(() -> gameView.setPrivateObjective(id));
    }

    /**
     * Sets the public objectives cards drawn for the game.
     *
     * @param publicObjectives the new public objectives cards
     */
    public void setPublicObjectives(List<Integer> publicObjectives) {
        Platform.runLater(() -> gameView.setPublicObjectives(publicObjectives));
    }

    /**
     * Removes the player on disconnection.
     *
     * @param playerId the player id
     */
    public void removePlayer(String playerId) {
        Platform.runLater(() -> gameView.removePlayer(playerId));
    }

    /**
     * Sets the tool cards drawn for the game..
     *
     * @param toolCards the new tool cards
     * @param client the client
     */
    public void setToolCards(List<Integer> toolCards, Client client) {
        Platform.runLater(() -> gameView.setToolCards(toolCards));
        setToolHandler(client);
    }

    /**
     * Notify turn begun.
     */
    public void notifyTurn() {
        Platform.runLater(gameView::notifyTurn);
    }

    /**
     * Notify end turn over.
     */
    public void notifyEndTurn() {
        Platform.runLater(gameView::notifyEndTurn);
    }

    /**
     * Notify response to dice move.
     *
     * @param ruleResponse the rule response
     */
    public void notifyMoveResponse(RuleResponse ruleResponse) {
        Platform.runLater(() -> {
            if(!ruleResponse.isMoveValid()) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Invalid move removing dice");
                lastMove.removeMistakenDice();
            }
        });
    }

    /**
     * Sets the opponent dice response.
     *
     * @param opponentDiceMoveResponse the new opponent dice response
     */
    public void setOpponentDiceResponse(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        Platform.runLater(() -> {
            if(!opponentDiceMoveResponse.getIdPlayer().equals(gameView.getUsername())) {
                gameView.setOpponentWindow(opponentDiceMoveResponse.getIdPlayer(),
                        opponentDiceMoveResponse.getDice(),
                        opponentDiceMoveResponse.getPosition());
            }
            else {
                if(opponentDiceMoveResponse.getDice().getId()==(-1)) //Se è un dado da rimuovere manda anche a se stesso la notifica
                    gameView.setDiceWindow(opponentDiceMoveResponse.getPosition());
            }
        });

    }

    /**
     * Sets the notification message.
     *
     * @param message the new notification
     */
    public void setNotification(String message) {
        Platform.runLater(() -> gameView.setNotification(message));
    }

    /**
     * Removes the token when toolcard is bought.
     *
     * @param num the number of token to be removed
     */
    public void removeToken(int num) {
        Platform.runLater(()-> gameView.removeToken(num));
    }

    /**
     * Adds the token to the tool.
     *
     * @param num the num of token to be added
     * @param toolCardView the tool card view
     */
    public void addTokenTool(int num, ToolCardView toolCardView){
        Platform.runLater(()-> gameView.addTokenTool(num, toolCardView));
    }

    /**
     * Sets the current round.
     *
     * @param round the current round
     */
    public void setRound(int round) {
        currentRound = round;
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"New round: "+currentRound);
    }

    /**
     * Sets the remaining time .
     *
     * @param time the current remaining time
     */
    public void setTimeRemaining(int time) {
        Platform.runLater(() -> gameView.setTimeRemaining(time));
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return gameView.getStage();
    }

    /**
     * Gets the window as byte array.
     *
     * @return the window as byte array
     */
    public byte[] getWindowAsByteArray() {
        return gameView.getWindowAsByteArray();
    }

    /**
     * Enable gui element on toolcard usage.
     *
     * @param toolId the tool id
     * @param client the client
     */
    public void enableGuiElement(int toolId, Client client) {
        if(toolId==0 || toolId==5 || toolId==6 || toolId==9) enableDraftClick(client);
        if(toolId==1 || toolId==2 || toolId == 3 || toolId == 11) enableWindowDiceDrag();
        if(toolId==4) enableRoundTrackClick(client);
        if(toolId==10) enableDraftClick(client);
        if(toolId==11) enableRoundTrack(client);
    }

    /**
     * Disable gui element on toolcard used.
     */
    private void disableGuiElement() {
        disableDraftClick();
        disableWindowDiceDrag();
        disableRoundTrackClick();
    }
}