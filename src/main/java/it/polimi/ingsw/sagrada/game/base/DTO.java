package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.HashSet;

public class DTO {

    private Dice dice;
    private HashSet<Integer> ignoreColorSet;
    private HashSet<Integer> ignoreValueSet;
    private Cell[][] windowMatrix;
    private Position currentPosition;
    private Position newPosition;

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public HashSet<Integer> getIgnoreColorSet() {
        return ignoreColorSet;
    }

    public void setIgnoreColorSet(HashSet<Integer> ignoreColorSet) {
        this.ignoreColorSet = ignoreColorSet;
    }

    public HashSet<Integer> getIgnoreValueSet() {
        return ignoreValueSet;
    }

    public void setIgnoreValueSet(HashSet<Integer> ignoreValueSet) {
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
