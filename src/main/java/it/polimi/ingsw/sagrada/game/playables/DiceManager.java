package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class DiceManager implements Channel<DiceEvent, DiceResponse> {
    private List<Dice> draftPool;
    private List<Dice> bagPool;
    private static final int DICE_PER_COLOR = 18;
    private int diceNumber;
    private int numberOfPlayers; // missing method to fetch this value, temporary value for testing
    private Consumer<Message> dispatchGameManager;
    private DynamicRouter dynamicRouter;
    

    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    /**
     * initialize pools
     */
    public DiceManager(int numberOfPlayers, Consumer<Message> dispatchGameManager, DynamicRouter dynamicRouter) {
        bagPool = new ArrayList<>();
        draftPool = new ArrayList<>();
        int id = 0;
        for (Color color : Colors.getColorList()) {
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

    public void bagToDraft() {
        draftPool.clear();
        Iterator<Dice> bagPicker = new Picker<>(bagPool).pickerIterator();
        System.out.println("Printing");
        for (int i = 0; i < diceNumber; i++) {
            Dice dice = bagPicker.next();
            dice.roll();
            System.out.println(this.toString()+ " - " + dice.getId());
            draftPool.add(dice);
        }
        System.out.println("End print");
        sendMessage(new DiceResponse("draft", new ArrayList<>(draftPool)));
    }

    private Dice getDiceDraft(int idDice) {
        for (Dice dice : draftPool) {
            if (dice.getId() == idDice) {
                draftPool.remove(dice);
                return dice;
            }
        }
        return null;
    }

    public List<Dice> putDiceRoundTrack() {
        return new ArrayList<>(draftPool);
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public void dispatch(DiceEvent message) {
        Dice dice = getDiceDraft(message.getIdDice());
        DiceGameManagerEvent diceGameManagerEvent = new DiceGameManagerEvent(dice, message);
        dispatchGameManager.accept(diceGameManagerEvent);
    }

    @Override
    public void sendMessage(DiceResponse message) {
        dynamicRouter.dispatch(message);
    }
}