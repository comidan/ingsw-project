package it.polimi.ingsw.sagrada.game.base.utility;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;


/**
 * Data Transfer Object class, used as container for various kind of different un-related objects
 */
public class DTO {

    private Dice dice;

    private Set<Integer> ignoreColorSet;

    private Set<Integer> ignoreValueSet;

    private Cell[][] windowMatrix;

    private Position currentPosition;

    private Position newPosition;

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

    /**
     * Gets the ignore color set based on tool cards usages
     *
     * @return the ignore color set
     */
    public Set<Integer> getIgnoreColorSet() {
        return ignoreColorSet;
    }

    /**
     * Sets the ignore color set based on tool cards usages
     *
     * @param ignoreColorSet the new ignore color set
     */
    public void setIgnoreColorSet(Set<Integer> ignoreColorSet) {
        this.ignoreColorSet = ignoreColorSet;
    }

    /**
     * Gets the ignore value set based on tool cards usages
     *
     * @return the ignore value set
     */
    public Set<Integer> getIgnoreValueSet() {
        return ignoreValueSet;
    }

    /**
     * Sets the ignore value set based on tool cards usages
     *
     * @param ignoreValueSet the new ignore value set
     */
    public void setIgnoreValueSet(Set<Integer> ignoreValueSet) {
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



}
