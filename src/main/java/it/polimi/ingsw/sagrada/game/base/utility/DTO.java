package it.polimi.ingsw.sagrada.game.base.utility;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 * Data Transfer Object class, used as container for various kind of different un-related objects
 */
public class DTO {

    private Dice dice;

    private Dice secondDice;

    private int diceId;

    private String playerId;

    private int imposedDiceValue;

    private Colors imposedColor;

    private Consumer<Integer> ignoreColorSet;

    private Consumer<Integer> ignoreValueSet;

    private Cell[][] windowMatrix;

    private Position currentPosition;

    private Position secondCurrentPosition;

    private Position newPosition;

    private Position secondNewPosition;

    private Runnable rollDraft;

    private BiConsumer<Dice, Dice> exchangeDraftDice;

    private BiConsumer<Dice, Dice> exchangeRoundTrackDice;

    private BiConsumer<String, Integer> moveDiceFromDraftToBag;

    private Consumer<Integer> ignoreSequenceDice;

    private BiConsumer<Integer, Integer> exchangeIgnoreSequenceDice;

    /**
     * Gets the set dice
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Sets a dice
     *
     * @param dice the new dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public int getDiceId() {
        return diceId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setDiceId(int diceId) {
        this.diceId = diceId;
    }
    /**
     * Gets the ignore color set based on tool cards usages
     *
     * @return the ignore color set
     */
    public Consumer<Integer> getIgnoreColorSet() {
        return ignoreColorSet;
    }

    /**
     * Sets the ignore color set based on tool cards usages
     *
     * @param ignoreColorSet the new ignore color set
     */
    public void setIgnoreColorSet(Consumer<Integer> ignoreColorSet) {
        this.ignoreColorSet = ignoreColorSet;
    }

    /**
     * Gets the ignore value set based on tool cards usages
     *
     * @return the ignore value set
     */
    public Consumer<Integer> getIgnoreValueSet() {
        return ignoreValueSet;
    }

    /**
     * Sets the ignore value set based on tool cards usages
     *
     * @param ignoreValueSet the new ignore value set
     */
    public void setIgnoreValueSet(Consumer<Integer> ignoreValueSet) {
        this.ignoreValueSet = ignoreValueSet;
    }

    /**
     * Gets the window matrix
     *
     * @return the window matrix
     */
    public Cell[][] getWindowMatrix() {
        return windowMatrix;
    }

    /**
     * Sets the window matrix
     *
     * @param windowMatrix the new window matrix
     */
    public void setWindowMatrix(Cell[][] windowMatrix) {
        this.windowMatrix = windowMatrix;
    }

    /**
     * Gets the current set position
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

    public Dice getSecondDice() {
        return secondDice;
    }

    public void setSecondDice(Dice secondDice) {
        this.secondDice = secondDice;
    }

    public Position getSecondNewPosition() {
        return secondNewPosition;
    }

    public void setSecondNewPosition(Position secondNewPosition) {
        this.secondNewPosition = secondNewPosition;
    }

    public BiConsumer<Dice, Dice> getExchangeDraftDice() {
        return exchangeDraftDice;
    }

    public void setExchangeDraftDice(BiConsumer<Dice, Dice> exchangeDraftDice) {
        this.exchangeDraftDice = exchangeDraftDice;
    }

    public BiConsumer<Dice, Dice> getExchangeRoundTrackDice() {
        return exchangeRoundTrackDice;
    }

    public void setExchangeRoundTrackDice(BiConsumer<Dice, Dice> exchangeRoundTrackDice) {
        this.exchangeRoundTrackDice = exchangeRoundTrackDice;
    }

    public Runnable getRollDraft() {
        return rollDraft;
    }

    public void setRollDraft(Runnable rollDraft) {
        this.rollDraft = rollDraft;
    }

    public int getImposedDiceValue() {
        return imposedDiceValue;
    }

    public void setImposedDiceValue(int imposedDiceValue) {
        this.imposedDiceValue = imposedDiceValue;
    }

    public BiConsumer<String, Integer> getMoveDiceFromDraftToBag() {
        return moveDiceFromDraftToBag;
    }

    public void setMoveDiceFromDraftToBag(BiConsumer<String, Integer> moveDiceFromDraftToBag) {
        this.moveDiceFromDraftToBag = moveDiceFromDraftToBag;
    }

    public Colors getImposedColor() {
        return imposedColor;
    }

    public void setImposedColor(Colors imposedColor) {
        this.imposedColor = imposedColor;
    }

    public Position getSecondCurrentPosition() {
        return secondCurrentPosition;
    }

    public void setSecondCurrentPosition(Position secondCurrentPosition) {
        this.secondCurrentPosition = secondCurrentPosition;
    }

    public Consumer<Integer> getIgnoreSequenceDice() {
        return ignoreSequenceDice;
    }

    public void setIgnoreSequenceDice(Consumer<Integer> ignoreSequenceDice) {
        this.ignoreSequenceDice = ignoreSequenceDice;
    }

    public BiConsumer<Integer, Integer> getExchangeIgnoreSequenceDice() {
        return exchangeIgnoreSequenceDice;
    }

    public void setExchangeIgnoreSequenceDice(BiConsumer<Integer, Integer> exchangeIgnoreSequenceDice) {
        this.exchangeIgnoreSequenceDice = exchangeIgnoreSequenceDice;
    }
}
