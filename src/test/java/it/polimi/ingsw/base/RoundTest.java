package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateIteratorSingletonPool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {

    @Test
    public void testRound() {
        StateGameEnum state;
        StateIterator itr = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());
        int index=0;

        //Method used only for testing. Please read StateIterator method to get full image of what's going on
        itr.forceState(null);

        List<StateGameEnum> stateSequence = new ArrayList<>();
        stateSequence.add(StateGameEnum.DEAL_PRIVATE_OBJECTIVE);
        stateSequence.add(StateGameEnum.DEAL_TOOL);
        stateSequence.add(StateGameEnum.DEAL_PUBLIC_OBJECTIVE);
        stateSequence.add(StateGameEnum.DEAL_WINDOWS);
        for(int i=0; i<10; i++) {
            stateSequence.add(StateGameEnum.TURN);
        }
        stateSequence.add(StateGameEnum.SCORE);

        while(itr.hasNext()) {
            state = itr.next();
            assertEquals(stateSequence.get(index), state);
            index++;
        }


         //Method used only for testing. Please read StateIterator method to get full image of what's going on
        itr.forceState(null);
    }
}
