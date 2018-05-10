package it.polimi.ingsw.sagrada.game.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.polimi.ingsw.sagrada.game.base.RoundStateEnum;

/**
 * This Iterator manages various phase of the game. It's a Singleton
 */

public class RoundIterator implements Iterator<RoundStateEnum> {
    private static RoundIterator roundIterator;
    private RoundStateEnum currentState;
    private int roundNumber;


    private RoundIterator() {
        roundNumber = 1;
        currentState = null;
    }

    public static RoundIterator getRoundIterator() {
        if (roundIterator == null) roundIterator = new RoundIterator();
        return roundIterator;
    }

    public RoundStateEnum getCurrentState() {
        return currentState;
    }


    @Override
    public boolean hasNext() {
        System.out.print(roundNumber);
        return (!(currentState == RoundStateEnum.END_ROUND && roundNumber == 10));
    }

    @Override
    public RoundStateEnum next() {
        if (currentState == null) {
            currentState = RoundStateEnum.SETUP_ROUND;
        } else {
            switch (currentState) {
                case SETUP_ROUND:
                    currentState = RoundStateEnum.IN_GAME;
                    break;
                case IN_GAME:
                    currentState = RoundStateEnum.END_ROUND;
                    break;
                case END_ROUND:
                    currentState = RoundStateEnum.SETUP_ROUND;
                    roundNumber++;
                    break;

            }
        }
        return currentState;
    }
}
