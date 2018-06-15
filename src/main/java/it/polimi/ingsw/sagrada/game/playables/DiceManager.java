package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


/**
 * The Class DiceManager.
 */
public class DiceManager implements Channel<DiceEvent, DiceResponse> {
    
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
        int id = 0;
        for (Colors color : Colors.getColorList()) {
            for (int j = 0; j < DICE_PER_COLOR; j++) {
                bagPool.add(new Dice(id++, color));
            }
        }
        this.numberOfPlayers = numberOfPlayers;
        diceNumber = this.numberOfPlayers * 2 + 1;

        this.dispatchGameManager = dispatchGameManager;
        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(DiceEvent.class, this);
    }

    /**
     * Bag to draft.
     */
    public void bagToDraft() {
        diceDraftBackup = null;
        draftPool.clear();
        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();
        for (int i = 0; i < diceNumber; i++) {
            Dice dice = bagPicker.next();
            dice.roll();
            draftPool.add(dice);
        }
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
        return draftPool;
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
    public void dispatch(DiceEvent message) {
        Dice dice = getDiceDraft(message.getIdDice());
        DiceGameManagerEvent diceGameManagerEvent = new DiceGameManagerEvent(dice, message);
        dispatchGameManager.accept(diceGameManagerEvent);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(DiceResponse message) {
        dynamicRouter.dispatch(message);
    }
}