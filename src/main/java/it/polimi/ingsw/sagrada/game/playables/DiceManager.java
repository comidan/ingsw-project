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
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceValueEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.*;
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
public class DiceManager implements Channel<Message, Message>, DiceManagerMessageVisitor {
    
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
        this.dynamicRouter.subscribeChannel(SwapDiceToolMessage.class, this);
        this.dynamicRouter.subscribeChannel(DiceValueEvent.class, this);
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
     * Gets the dice from bag.
     *
     * @return the dice from bag
     */
    private Dice getDiceFromBag() {
        return bagPool.remove(new Random().nextInt(bagPool.size()));
    }

    /**
     * Gets the dice draft and removed it.
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
     * Gets the dice draft no backup.
     *
     * @param idDice the id dice
     * @return the dice draft no backup
     */
    private Dice getDiceDraftNoBackup(int idDice) {
        for (Dice dice : draftPool) {
            if (dice.getId() == idDice) {
                draftPool.remove(dice);
                return dice;
            }
        }
        return null;
    }

    /**
     * Gets the dice draft and removed it.
     *
     * @param idDice the id dice
     * @return the dice draft
     */
    private Dice getDiceDraftNoDeletion(int idDice) {
        for (Dice dice : draftPool) {
            if (dice.getId() == idDice) {
                return dice;
            }
        }
        return null;
    }

    /**
     * Revert.
     */
    public void revert() {
        if(diceDraftBackup != null) {
            draftPool.add(diceDraftBackup);
            diceDraftBackup = null;
        }
    }

    /**
     * Gets the draft.
     *
     * @return the draft
     */
    public List<Dice> getDraft() {
        return new ArrayList<>(draftPool.subList(0, draftPool.size()));
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
        diceManagerVisitor.accept(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }


    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent)
     */
    @Override
    public void visit(DiceEvent diceEvent) {
        if(diceEvent.getSrc().equals(CommandKeyword.DRAFT)) {
            Dice dice = getDiceDraft(diceEvent.getIdDice());
            DiceGameManagerEvent diceGameManagerEvent = new DiceGameManagerEvent(dice, diceEvent);
            dispatchGameManager.accept(diceGameManagerEvent);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.ChangeDiceValueToolMessage)
     */
    @Override
    public void visit(ChangeDiceValueToolMessage changeDiceValueToolMessage) {
        DTO dto = new DTO();
        dto.setDice(getDiceDraft(changeDiceValueToolMessage.getDiceId()));
        changeDiceValueToolMessage.getToolCard().getRule().checkRule(dto);
        putDiceDraft(dto.getDice());
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.RollAllDiceToolMessage)
     */
    @Override
    public void visit(RollAllDiceToolMessage rollAllDiceToolMessage) {
        DTO dto = new DTO();
        dto.setRollDraft(this::rollDraft);
        rollAllDiceToolMessage.getToolCard().getRule().checkRule(dto);
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.SwapDiceToolMessage)
     */
    @Override
    public void visit(SwapDiceToolMessage swapDiceToolMessage) {
        sendMessage(new CompleteSwapDiceToolMessage(
                this::exchangeDice,
                getDiceDraftNoDeletion(swapDiceToolMessage.getDraftDiceId()),
                swapDiceToolMessage));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.DiceManagerMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceValueEvent)
     */
    @Override
    public void visit(DiceValueEvent diceValueEvent) {
        DiceEvent diceEvent = diceValueEvent.getDiceEvent();
        Dice dice = getDiceDraftNoBackup(diceEvent.getIdDice());
        dice.setValue(diceValueEvent.getValue());
        DiceGameManagerEvent diceGameManagerEvent = new DiceGameManagerEvent(dice, diceEvent);
        dispatchGameManager.accept(diceGameManagerEvent);
    }

    /**
     * Put dice draft.
     *
     * @param dice the dice
     */
    private void putDiceDraft(Dice dice) {
        draftPool.add(dice);
    }

    /**
     * Exchange one dice for an external one.
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

    /**
     * Move dice from draft to bag.
     *
     * @param playerId the player id
     * @param diceId the dice id
     */
    public void moveDiceFromDraftToBag(String playerId, int diceId) {
        Dice dice = getDiceDraftNoBackup(diceId);
        if(dice!= null){
            bagPool.add(dice);
            Dice diceBag = getDiceFromBag();
            draftPool.add(diceBag);
            sendMessage(new ColorBagToolResponse(playerId, diceBag.getColor(), diceBag.getId()));
            sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
        }
    }

    /**
     * Roll draft.
     */
    private void rollDraft() {
        draftPool.forEach(Dice::roll);
        sendMessage(new DiceResponse(CommandKeyword.DRAFT, new ArrayList<>(draftPool)));
    }
}