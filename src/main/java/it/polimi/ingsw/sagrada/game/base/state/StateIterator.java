package it.polimi.ingsw.sagrada.game.base.state;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.sagrada.game.base.state.StateGameEnum.*;


/**
 * This Iterator manages various phase of the game
 */

public class StateIterator implements Iterator<StateGameEnum> {
    
    /** The current state. */
    private StateGameEnum currentState;
    
    /** The round number. */
    private int roundNumber;

    /**
     * Instantiates a new state iterator.
     */
    StateIterator() {
        roundNumber=1;
        currentState=null;
    }

    /**
     * This method only purpose is for testing. It will be removed
     * DON'T USE IT!
     * Because this class is a Singleton in theory there should not be more than one existing test
     * using this class because the first test will modify permanently StateIterator
     *
     * @param s stateGame going to be set
     */
    public void forceState(StateGameEnum s) {
        currentState = s;
        roundNumber = 1;
    }

    /**
     * Gets the current state
     *
     * @return the current state
     */
    public StateGameEnum getCurrentState() {
        return currentState;
    }

    /**
     * Gets the round number
     *
     * @return the round number
     */
    public int getRoundNumber() {
        return  roundNumber;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return (currentState!=SCORE);
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public StateGameEnum next() {
        if(currentState==null){
            currentState= StateGameEnum.getFirstState();
        } else {
            switch (currentState) {
                case DEAL_PRIVATE_OBJECTIVE: currentState=DEAL_TOOL; break;
                case DEAL_TOOL: currentState=DEAL_PUBLIC_OBJECTIVE; break;
                case DEAL_PUBLIC_OBJECTIVE: currentState=DEAL_WINDOWS; break;
                case DEAL_WINDOWS: currentState=TURN; break;
                case TURN: if(roundNumber<10) roundNumber++;else currentState=SCORE; break;
                default: throw new NoSuchElementException();
            }
        }
        return currentState;
    }
}
