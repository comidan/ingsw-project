package it.polimi.ingsw.sagrada.game.base.utility;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;



/**
 * Data Transfer Object class, used as container for various kind of different un-related objects.
 */
public class DTO {

    /** The dice. */
    private Dice dice;

    /** The second dice. */
    private Dice secondDice;

    /** The dice id. */
    private int diceId;

    /** The player id. */
    private String playerId;

    /** The imposed dice value. */
    private int imposedDiceValue;

    /** The imposed color. */
    private Colors imposedColor;

    /** The ignore color set. */
    private Consumer<Integer> ignoreColorSet;

    /** The ignore value set. */
    private Consumer<Integer> ignoreValueSet;

    /** The window matrix. */
    private Cell[][] windowMatrix;

    /** The current position. */
    private Position currentPosition;

    /** The second current position. */
    private Position secondCurrentPosition;

    /** The new position. */
    private Position newPosition;

    /** The second new position. */
    private Position secondNewPosition;

    /** The roll draft. */
    private Runnable rollDraft;

    /** The exchange draft dice. */
    private BiConsumer<Dice, Dice> exchangeDraftDice;

    /** The exchange round track dice. */
    private BiConsumer<Dice, Dice> exchangeRoundTrackDice;

    /** The move dice from draft to bag. */
    private BiConsumer<String, Integer> moveDiceFromDraftToBag;

    /** The ignore sequence dice. */
    private Consumer<Integer> ignoreSequenceDice;

    /** The exchange ignore sequence dice. */
    private BiConsumer<Integer, Integer> exchangeIgnoreSequenceDice;

    /**
     * Gets the set dice.
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Sets a dice.
     *
     * @param dice the new dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Gets the dice id.
     *
     * @return the dice id
     */
    public int getDiceId() {
        return diceId;
    }

    /**
     * Gets the player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Sets the player id.
     *
     * @param playerId the new player id
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Sets the dice id.
     *
     * @param diceId the new dice id
     */
    public void setDiceId(int diceId) {
        this.diceId = diceId;
    }
    
    /**
     * Gets the ignore color set based on tool cards usages.
     *
     * @return the ignore color set
     */
    public Consumer<Integer> getIgnoreColorSet() {
        return ignoreColorSet;
    }

    /**
     * Sets the ignore color set based on tool cards usages.
     *
     * @param ignoreColorSet the new ignore color set
     */
    public void setIgnoreColorSet(Consumer<Integer> ignoreColorSet) {
        this.ignoreColorSet = ignoreColorSet;
    }

    /**
     * Gets the ignore value set based on tool cards usages.
     *
     * @return the ignore value set
     */
    public Consumer<Integer> getIgnoreValueSet() {
        return ignoreValueSet;
    }

    /**
     * Sets the ignore value set based on tool cards usages.
     *
     * @param ignoreValueSet the new ignore value set
     */
    public void setIgnoreValueSet(Consumer<Integer> ignoreValueSet) {
        this.ignoreValueSet = ignoreValueSet;
    }

    /**
     * Gets the window matrix.
     *
     * @return the window matrix
     */
    public Cell[][] getWindowMatrix() {
        return windowMatrix;
    }

    /**
     * Sets the window matrix.
     *
     * @param windowMatrix the new window matrix
     */
    public void setWindowMatrix(Cell[][] windowMatrix) {
        this.windowMatrix = windowMatrix;
    }

    /**
     * Gets the current set position.
     *
     * @return the current position
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position.
     *
     * @param currentPosition the new current position
     */
    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the new set position.
     *
     * @return the new position
     */
    public Position getNewPosition() {
        return newPosition;
    }

    /**
     * Sets the new position.
     *
     * @param newPosition the new new position
     */
    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    /**
     * Gets the second dice.
     *
     * @return the second dice
     */
    public Dice getSecondDice() {
        return secondDice;
    }

    /**
     * Sets the second dice.
     *
     * @param secondDice the new second dice
     */
    public void setSecondDice(Dice secondDice) {
        this.secondDice = secondDice;
    }

    /**
     * Gets the second new position.
     *
     * @return the second new position
     */
    public Position getSecondNewPosition() {
        return secondNewPosition;
    }

    /**
     * Sets the second new position.
     *
     * @param secondNewPosition the new second new position
     */
    public void setSecondNewPosition(Position secondNewPosition) {
        this.secondNewPosition = secondNewPosition;
    }

    /**
     * Gets the exchange draft dice.
     *
     * @return the exchange draft dice
     */
    public BiConsumer<Dice, Dice> getExchangeDraftDice() {
        return exchangeDraftDice;
    }

    /**
     * Sets the exchange draft dice.
     *
     * @param exchangeDraftDice the exchange draft dice
     */
    public void setExchangeDraftDice(BiConsumer<Dice, Dice> exchangeDraftDice) {
        this.exchangeDraftDice = exchangeDraftDice;
    }

    /**
     * Gets the exchange round track dice.
     *
     * @return the exchange round track dice
     */
    public BiConsumer<Dice, Dice> getExchangeRoundTrackDice() {
        return exchangeRoundTrackDice;
    }

    /**
     * Sets the exchange round track dice.
     *
     * @param exchangeRoundTrackDice the exchange round track dice
     */
    public void setExchangeRoundTrackDice(BiConsumer<Dice, Dice> exchangeRoundTrackDice) {
        this.exchangeRoundTrackDice = exchangeRoundTrackDice;
    }

    /**
     * Gets the roll draft.
     *
     * @return the roll draft
     */
    public Runnable getRollDraft() {
        return rollDraft;
    }

    /**
     * Sets the roll draft.
     *
     * @param rollDraft the new roll draft
     */
    public void setRollDraft(Runnable rollDraft) {
        this.rollDraft = rollDraft;
    }

    /**
     * Gets the imposed dice value.
     *
     * @return the imposed dice value
     */
    public int getImposedDiceValue() {
        return imposedDiceValue;
    }

    /**
     * Sets the imposed dice value.
     *
     * @param imposedDiceValue the new imposed dice value
     */
    public void setImposedDiceValue(int imposedDiceValue) {
        this.imposedDiceValue = imposedDiceValue;
    }

    /**
     * Gets the move dice from draft to bag.
     *
     * @return the move dice from draft to bag
     */
    public BiConsumer<String, Integer> getMoveDiceFromDraftToBag() {
        return moveDiceFromDraftToBag;
    }

    /**
     * Sets the move dice from draft to bag.
     *
     * @param moveDiceFromDraftToBag the move dice from draft to bag
     */
    public void setMoveDiceFromDraftToBag(BiConsumer<String, Integer> moveDiceFromDraftToBag) {
        this.moveDiceFromDraftToBag = moveDiceFromDraftToBag;
    }

    /**
     * Gets the imposed color.
     *
     * @return the imposed color
     */
    public Colors getImposedColor() {
        return imposedColor;
    }

    /**
     * Sets the imposed color.
     *
     * @param imposedColor the new imposed color
     */
    public void setImposedColor(Colors imposedColor) {
        this.imposedColor = imposedColor;
    }

    /**
     * Gets the second current position.
     *
     * @return the second current position
     */
    public Position getSecondCurrentPosition() {
        return secondCurrentPosition;
    }

    /**
     * Sets the second current position.
     *
     * @param secondCurrentPosition the new second current position
     */
    public void setSecondCurrentPosition(Position secondCurrentPosition) {
        this.secondCurrentPosition = secondCurrentPosition;
    }

    /**
     * Gets the ignore sequence dice.
     *
     * @return the ignore sequence dice
     */
    public Consumer<Integer> getIgnoreSequenceDice() {
        return ignoreSequenceDice;
    }

    /**
     * Sets the ignore sequence dice.
     *
     * @param ignoreSequenceDice the new ignore sequence dice
     */
    public void setIgnoreSequenceDice(Consumer<Integer> ignoreSequenceDice) {
        this.ignoreSequenceDice = ignoreSequenceDice;
    }

    /**
     * Gets the exchange ignore sequence dice.
     *
     * @return the exchange ignore sequence dice
     */
    public BiConsumer<Integer, Integer> getExchangeIgnoreSequenceDice() {
        return exchangeIgnoreSequenceDice;
    }

    /**
     * Sets the exchange ignore sequence dice.
     *
     * @param exchangeIgnoreSequenceDice the exchange ignore sequence dice
     */
    public void setExchangeIgnoreSequenceDice(BiConsumer<Integer, Integer> exchangeIgnoreSequenceDice) {
        this.exchangeIgnoreSequenceDice = exchangeIgnoreSequenceDice;
    }
}
