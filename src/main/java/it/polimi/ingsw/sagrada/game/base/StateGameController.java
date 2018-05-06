package it.polimi.ingsw.sagrada.game.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

public enum StateGameController {
    DEAL_PRIVATE_OBJECTIVE,
    DEAL_WINDOWS,
    DEAL_TOOL,
    DEAL_PUBLIC_OBJECTIVE,
    TURN,
    SCORE;

    private int roundNum=0;
    private StateGameController currentState=null;

    public static StateGameController getFirstState() {
        return DEAL_PRIVATE_OBJECTIVE;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public StateGameController getCurrentState() {
        return currentState;
    }

    public StateIterator stateIterator() {
        return new StateIterator();
    }

    private class StateIterator implements Iterator<StateGameController> {
        private StateIterator() {
            currentState = null;
        }

        @Override
        public boolean hasNext() {
            return (currentState!=SCORE);
        }

        @Override
        public StateGameController next() {
            if(currentState==null){
                currentState=DEAL_PRIVATE_OBJECTIVE;
            } else {
                switch (currentState) {
                    case DEAL_PRIVATE_OBJECTIVE: currentState=DEAL_WINDOWS; break;
                    case DEAL_WINDOWS: currentState=DEAL_TOOL; break;
                    case DEAL_TOOL: currentState=DEAL_PUBLIC_OBJECTIVE; break;
                    case DEAL_PUBLIC_OBJECTIVE: currentState=TURN; break;
                    case TURN: if(roundNum<9) roundNum++;else currentState=SCORE; break;
                    default: throw new NoSuchElementException();
                }
            }
            return currentState;
        }
    }
}
