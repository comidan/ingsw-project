package it.polimi.ingsw.sagrada.game.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;

/**
 * This Iterator manages various phase of the game. It's a Singleton
 */

public class StateIterator implements Iterator<StateGameEnum> {
    private static StateIterator instance;
    private StateGameEnum currentState;
    private int roundNumber;

    private StateIterator() {
        roundNumber=0;
        currentState=null;
    }

    /**
     * This method only purpose is for testing. It will be removed
     * DON'T USE IT!
     * Because this class is a Singleton in theory there should not be more than one existing test
     * using this class because the first test will modify permanently StateIterator
     */
    public void forceState(StateGameEnum s) {
        currentState=s;
    }

    public static StateIterator getInstance() {
        if(instance==null) instance = new StateIterator();
        return instance;
    }

    public StateGameEnum getCurrentState() {
        return currentState;
    }

    public int getRoundNumber() {
        return  roundNumber;
    }

    @Override
    public boolean hasNext() {
        return (currentState!=SCORE);
    }

    @Override
    public StateGameEnum next() throws NoSuchElementException {
        if(currentState==null){
            currentState= StateGameEnum.getFirstState();
        } else {
            switch (currentState) {
                case DEAL_PRIVATE_OBJECTIVE: currentState=DEAL_TOOL; break;
                case DEAL_TOOL: currentState=DEAL_PUBLIC_OBJECTIVE; break;
                case DEAL_PUBLIC_OBJECTIVE: currentState=DEAL_WINDOWS; break;
                case DEAL_WINDOWS: currentState=TURN; break;
                case TURN: if(roundNumber<9) roundNumber++;else currentState=SCORE; break;
                default: throw new NoSuchElementException();
            }
        }
        return currentState;
    }
}
