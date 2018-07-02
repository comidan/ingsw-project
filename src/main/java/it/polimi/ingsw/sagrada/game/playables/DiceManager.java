package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ChangeDiceValueToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RollAllDiceToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerVisitor;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;


/**
 * The Class DiceManager.
 */
public class DiceManager implements Channel<Message, DiceResponse>, DiceManagerMessageVisitor {
    
    /** The draft pool. */
    private List<Dice> draftPool;
    
    /** The bag pool. */
    private List<Dice> bagPool;
    
    /** The dice draft backup. */
    private Dice diceDraftBackup;
    
    /** The Constant DICE_PER_COLOR. */
    private static final int DICE_PER_COLOR = 18;
    
    /** The dice number. */
    private int diceNumber;
    
    /** The number of players. */
    private int numberOfPlayers; // missing method to fetch this value, temporary value for testing
    
    /** The dispatch game manager. */
    private Consumer<Message> dispatchGameManager;
    
    /** The dynamic router. */
    private DynamicRouter dynamicRouter;

    /**
     * initialize pools.
     *
     * @param numberOfPlayers the number of players
     * @param dispatchGameManager the dispatch game manager
     * @param dynamicRouter the dynamic router
     */
    public DiceManager(int numberOfPlayers, Consumer<Message> dispatchGameManager, DynamicRouter dynamicRouter) {
        bagPool = new ArrayList<>();
        draftPool = new ArrayList<>();
        AtomicInteger id = new AtomicInteger(0);
        Colors.getColorList().forEach(color -> IntStream.range(0, DICE_PER_COLOR).forEach(v -> bagPool.add(new Dice(id.getAndIncrement(), color))));
        this.numberOfPlayers = numberOfPlayers;
        diceNumber = this.numberOfPlayers * 2 + 1;
        this.dispatchGameManager = dispatchGameManager;
        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(DiceEvent.class, this);
        this.dynamicRouter.subscribeChannel(ChangeDiceValueToolMessage.class, this);
        this.dynamicRouter.subscribeChannel(RollAllDiceToolMessage.class, this);
    }

    /**
     * Bag to draft.
     */
    public void bagToDraft() {
        diceDraftBackup = null;
        draftPool.clear();
        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();
        IntStream.range(0, diceNumber).forEach(i -> {
            Dice dice = bagPicker.next();
            dice.roll();
            draftPool.add(dice);
        });
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }

    /**
     * Gets the dice draft.
     *
     * @param idDice the id dice
     * @return the dice draft
     */
    private Dice getDiceDraft(int idDice) {
        for (Dice dice : draftPool) {
            if (dice.getId() == idDice) {
                draftPool.remove(dice);
                diceDraftBackup = dice;
                return dice;
            }
        }
        return null;
    }

    /**
     * Revert.
     */
    public void revert() {
        draftPool.add(diceDraftBackup);
        diceDraftBackup = null;
    }

    /**
     * Gets the draft.
     *
     * @return the draft
     */
    public List<Dice> getDraft() {
        return draftPool.subList(0, draftPool.size());
    }

    /**
     * Put dice round track.
     *
     * @return the list
     */
    public List<Dice> putDiceRoundTrack() {
        return new ArrayList<>(draftPool);
    }

    /**
     * Sets the number of players.
     *
     * @param numberOfPlayers the new number of players
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        diceNumber = this.numberOfPlayers * 2 + 1;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(Message message) {
        DiceManagerVisitor diceManagerVisitor = (DiceManagerVisitor)message;
        System.out.println("Received diceManagerVisitor");
        diceManagerVisitor.accept(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(DiceResponse message) {
        dynamicRouter.dispatch(message);
    }


    @Override
    public void visit(DiceEvent diceEvent) {
        if(diceEvent.getSrc().equals(CommandKeyword.DRAFT)) {
            Dice dice = getDiceDraft(diceEvent.getIdDice());
            DiceGameManagerEvent diceGameManagerEvent = new DiceGameManagerEvent(dice, diceEvent);
            dispatchGameManager.accept(diceGameManagerEvent);
        }
    }

    @Override
    public void visit(ChangeDiceValueToolMessage changeDiceValueToolMessage) {
        DTO dto = new DTO();
        dto.setDice(getDiceDraft(changeDiceValueToolMessage.getDiceId()));
        dto.setIgnoreValueSet(changeDiceValueToolMessage.getIgnoreValueSet());
        changeDiceValueToolMessage.getToolCard().getRule().checkRule(dto);
        putDiceDraft(dto.getDice());
        System.out.println("---DiceManager, sending new draft---");
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }

    @Override
    public void visit(RollAllDiceToolMessage rollAllDiceToolMessage) {
        DTO dto = new DTO();
        dto.setRollDraft(this::rollDraft);
        rollAllDiceToolMessage.getToolCard().getRule().checkRule(dto);
        System.out.println("---DiceManager, sending new draft---");
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }

    private void putDiceDraft(Dice dice) {
        draftPool.add(dice);
    }

    /**
     * Exchange one dice for an external one
     *
     * @param oldDice - Dice to be removed
     * @param newDice - Dice to be added
     */
    public void exchangeDice(Dice oldDice, Dice newDice) {
        if(draftPool.remove(oldDice)) {
            draftPool.add(newDice);
            sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
        }
    }

    public void moveDiceFromDraftToBag(Dice diceFromDraft) {
        draftPool.remove(diceFromDraft);
        bagPool.add(diceFromDraft);
    }

    public void rollDraft() {
        draftPool.forEach(Dice::roll);
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }
}