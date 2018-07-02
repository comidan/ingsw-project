package it.polimi.ingsw.sagrada.gui.game;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.gui.cards.ToolCardView;
import it.polimi.ingsw.sagrada.gui.components.CellView;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.components.RoundCellView;
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
     * The clicked object.
     */
    private ClickedObject clickedObject;


    /**
     * The last move.
     */
    private CellView lastMove;

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
     * Sets the end turn handler.
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
                    gameView.notifyEndTurn();
                    System.out.println("Notified end turn");
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }

            });
        });
    }

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
         * Sets the cell handler.
         *
         * @param client the new cell handler
         */
    private void setCellHandler(Client client) {

        GameGuiAdapter self = this;
        Platform.runLater(() -> {
            this.gameView.setCellClickListener(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.COPY);}
                    }, new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        System.out.println("---CellClickEvent---");
                      DiceView diceView = clickedObject.getClickedDice();
                      System.out.println("---"+diceView.getDiceID()+"---");
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
                              DiceEvent diceEvent = new DiceEvent(username, idDice, position, diceSource);
                              try {
                                  client.sendRemoteMessage(diceEvent);
                              } catch (RemoteException e) {
                                  LOGGER.log(Level.SEVERE, e::getMessage);
                              }

                              event.consume();
                          }
                      }
             }

             });

        });
    }

    /**
     * Sets the draft listener.
     */
    private void setDraftListener() {
        Platform.runLater(() -> {
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    DiceView diceView = (DiceView) event.getSource();
                    clickedObject.setClickedDice(diceView);
                    Dragboard db = diceView.startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(diceView.getImage());
                    db.setContent(content);
                    event.consume();
                    diceSource = CommandKeyword.DRAFT;
                    System.out.println("Selected dice " + diceView.getValue() + " " + diceView.getColor());

                }
            };


            this.gameView.setDraftClickHandler(eventHandler);
        });
    }

    /**
     * Sets the tool handler.
     */
    private void setToolHandler(Client client) {
        Platform.runLater(() -> {
            this.gameView.setToolClickHandler(event -> {
                ToolCardView toolCardView = (ToolCardView) event.getSource();
                ToolEvent toolEvent = new ToolEvent(gameView.getUsername(), toolCardView.getToolId());
                try {
                    client.sendRemoteMessage(toolEvent);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
            });
        });
    }

    private void setTokenWindow(int tokenNumber){
        setToken(tokenNumber);
    }



    private void setToolCardPrevListener(){
            gameView.setToolPreviewListener(event -> {
                gameView.showToolCard();
                gameView.setToolPreviewListener(eventDone -> {
                    gameView.hideToolCard();
                    setToolCardPrevListener();
                });}
            );

    }

    private void setPrivatePrevListener(){
            gameView.setPrivatePreviewListener(event -> {
                gameView.showPrivateCard();
                gameView.setPrivatePreviewListener(eventDone -> {
                    gameView.hidePrivateCard();
                    setPrivatePrevListener();
                });}
            );

    }

    private void setPublicPrevListener(){
            gameView.setPublicPreviewListener(event -> {
                gameView.showPublicCard();
                gameView.setPublicPreviewListener(eventDone -> {
                    gameView.hidePublicCard();
                    setPublicPrevListener();
                });}
            );
    }



    private void setCardPreviewListener(){
        Platform.runLater(() -> {
            setToolCardPrevListener();
            setPrivatePrevListener();
            setPublicPrevListener();
        });
    }

    // Tool effect: change dice value in draft adding one OR rolls again dice, according to value it gets
    // can be used for toolcards: 1, 6, 10

    private void enableDraftChangeValue(Client client){
        this.gameView.enableDraftChangeValue(event ->
        {
            DiceView diceView = (DiceView) event.getSource();
            DiceDraftSelectionEvent diceDraftSelectionEvent = new DiceDraftSelectionEvent(gameView.getUsername(), diceView.getDiceID());
            System.out.println("click");
            try {
                client.sendRemoteMessage(diceDraftSelectionEvent);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
            disableGuiElement();
            // IMPORTANT : model must control that the used dice is actually the chosen dice for toolcard 1 and 10
        });
    }

    public void disableDraftChangeValue(){
        this.gameView.disableDraftChangeValue();
    }

    // Tool effect: enable moving dice on your own window
    // can be used for toolcards: 2, 3, 4, 12

    private void enableWindowDiceDrag() {
        System.out.println("---EnableDrag---");
        this.gameView.enableWindowDiceDrag(event -> {
            System.out.println("---Window click---");
            DiceView diceView = (DiceView) event.getSource();
            System.out.println(diceView.getDiceID());
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

    public void disableWindowDiceDrag(){
            gameView.disableWindowDiceDrag();
        }

     /**
     * Sets the round track click.
     */


     // Tool effect: enable drag on dice in roundtrack
     // can be used for toolcards: 5
    public void enableRoundTrackClick(Client client){
        Platform.runLater(() -> {


            enableDraftChangeValue(client);

            this.gameView.setRoundtrackClickHandler(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DiceView diceView = (DiceView) event.getSource();
                    DiceDraftSelectionEvent diceDraftSelectionEvent = new DiceDraftSelectionEvent(gameView.getUsername(), diceView.getDiceID());

                    try {
                        client.sendRemoteMessage(diceDraftSelectionEvent);
                    } catch (RemoteException e) {
                        LOGGER.log(Level.SEVERE, e::getMessage);
                    }

                }
            });
        });
    }

    public void disableRoundTrackClick(Client client){

    }




    /**
     * Adds the dice roundtrack.
     *
     * @param diceViewList the dice view list
     * @param roundNumber the round number
     */
    //method to call this on server demand must be created
    private void addDiceRoundtrack(List<DiceView> diceViewList, int roundNumber){
        Platform.runLater(() -> {
            this.gameView.setRoundtrackImage(diceViewList, roundNumber);

        });
    }

    /**
     * Sets the token.
     *
     * @param tokenNumber the new token
     */
    public void setToken(int tokenNumber){
        Platform.runLater(() -> {
            this.gameView.setToken(tokenNumber);
        });
    }

    /**
     * Removes the mistaken dice.
     *
     * @param row the row
     * @param col the col
     */
    //method to call this on server demand must be created
    public void removeMistakenDice(int row, int col){
        Platform.runLater(() -> {
            this.gameView.removeMistakenDice(row, col);
        });
    }

    /**
     * Sets the draft.
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
     * Sets the dice list.
     *
     * @param diceResponse the new dice list
     */
    public void setDiceList(DiceResponse diceResponse) {
        if(diceResponse.getDst().equals(CommandKeyword.DRAFT))
            setDraft(diceResponse);
        else if(diceResponse.getDst().equals(CommandKeyword.ROUND_TRACK))
        {setRoundTrack(diceResponse);
        setShowRoundDicesHandler();}
    }

    void setShowRoundDicesHandler(){
        gameView.setShowRoundDicesHandler(event -> {
            RoundCellView roundCellView = (RoundCellView) event.getSource();
            roundCellView.showAllDice();
                    gameView.setShowRoundDicesHandler(eventDone -> {
                        roundCellView.hideDice();
                        setShowRoundDicesHandler();
                    });

        });
    }

    /**
     * Sets the round track.
     *
     * @param diceResponse the new round track
     */
    private void setRoundTrack(DiceResponse diceResponse) {
        Platform.runLater(() -> {
            List<DiceView> diceViews = new ArrayList<>();
            diceResponse.getDiceList().forEach(dice -> diceViews.add(new DiceView(Constraint.getColorConstraint(dice.getColor()), Constraint.getValueConstraint(dice.getValue()), dice.getId())));
            gameView.setRoundtrackImage(diceViews, currentRound);
            setShowRoundDicesHandler();
        });
    }



    /**
     * Sets the private objective.
     *
     * @param id the new private objective
     */
    public void setPrivateObjective(int id){
        Platform.runLater(() -> gameView.setPrivateObjective(id));
    }

    /**
     * Sets the public objectives.
     *
     * @param publicObjectives the new public objectives
     */
    public void setPublicObjectives(List<Integer> publicObjectives) {
        Platform.runLater(() -> gameView.setPublicObjectives(publicObjectives));
    }

    /**
     * Removes the player.
     *
     * @param playerId the player id
     */
    public void removePlayer(String playerId) {
        Platform.runLater(() -> gameView.removePlayer(playerId));
    }

    /**
     * Sets the tool cards.
     *
     * @param toolCards the new tool cards
     */
    public void setToolCards(List<Integer> toolCards, Client client) {
        Platform.runLater(() -> gameView.setToolCards(toolCards));
        setToolHandler(client);
    }




    /**
     * Notify turn.
     */
    public void notifyTurn() {
        Platform.runLater(gameView::notifyTurn);
    }

    /**
     * Notify end turn.
     */
    public void notifyEndTurn() {
        Platform.runLater(gameView::notifyEndTurn);
    }

    /**
     * Notify move response.
     *
     * @param ruleResponse the rule response
     */
    public void notifyMoveResponse(RuleResponse ruleResponse) {
        Platform.runLater(() -> {
            if(!ruleResponse.isMoveValid()) {
                System.out.println("Invalid move removing dice");
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
        Platform.runLater(() -> gameView.setOpponentWindow(opponentDiceMoveResponse.getIdPlayer(),
                                        opponentDiceMoveResponse.getDice(),
                                        opponentDiceMoveResponse.getPosition()));

    }

    public void setNotification(String message) {
        Platform.runLater(() -> gameView.setNotification(message));
    }

    public void removeToken(int num) {
        Platform.runLater(()-> gameView.removeToken(num));
    }

    /**
     * Sets the round.
     *
     * @param round the new round
     */
    public void setRound(int round) {
        currentRound = round;
    }

    public void setTimeRemaining(int time) {
        Platform.runLater(() -> gameView.setTimeRemaining(time));
    }

    public Stage getStage() {
        return gameView.getStage();
    }

    public byte[] getWindowAsByteArray() {
        return gameView.getWindowAsByteArray();
    }

    public void enableGuiElement(int toolId, Client client) {
        System.out.println("---GameGuiAdapter enable GUI element---");
        if(toolId==0 || toolId==5 || toolId==6 || toolId==9) enableDraftChangeValue(client);
        if(toolId==1 || toolId==2) enableWindowDiceDrag();
    }

    public void disableGuiElement() {
        disableDraftChangeValue();
        disableWindowDiceDrag();
    }
}