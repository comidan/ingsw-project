package it.polimi.ingsw.sagrada.game.base.utility;

import it.polimi.ingsw.sagrada.game.base.Cell;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.Set;

public class DTO {

    private Dice dice;
    private Set<Integer> ignoreColorSet;
    private Set<Integer> ignoreValueSet;
    private Cell[][] windowMatrix;
    private Position currentPosition;
    private Position newPosition;

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Set<Integer> getIgnoreColorSet() {
        return ignoreColorSet;
    }

    public void setIgnoreColorSet(Set<Integer> ignoreColorSet) {
        this.ignoreColorSet = ignoreColorSet;
    }

    public Set<Integer> getIgnoreValueSet() {
        return ignoreValueSet;
    }

    public void setIgnoreValueSet(Set<Integer> ignoreValueSet) {
        this.ignoreValueSet = ignoreValueSet;
    }

    public Cell[][] getWindowMatrix() {
        return windowMatrix;
    }

    public void setWindowMatrix(Cell[][] windowMatrix) {
        this.windowMatrix = windowMatrix;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }



}
