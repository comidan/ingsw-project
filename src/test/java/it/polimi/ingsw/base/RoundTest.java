package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.StateIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {

    @Test
    public void testRound() {
        StateGameEnum state;
        StateIterator itr = StateIterator.getInstance();
        int index=0;

        List<StateGameEnum> stateSequence = new ArrayList<>();
        stateSequence.add(StateGameEnum.DEAL_PRIVATE_OBJECTIVE);
        stateSequence.add(StateGameEnum.DEAL_WINDOWS);
        stateSequence.add(StateGameEnum.DEAL_TOOL);
        stateSequence.add(StateGameEnum.DEAL_PUBLIC_OBJECTIVE);
        for(int i=0; i<10; i++) {
            stateSequence.add(StateGameEnum.TURN);
        }
        stateSequence.add(StateGameEnum.SCORE);

        while(itr.hasNext()) {
            state = itr.next();
            assertEquals(stateSequence.get(index), state);
            index++;
        }

        /**
         * Method used only for testing. Please read StateIterator method to get full image of what's going on
         */
        itr.forceState(StateGameEnum.DEAL_PRIVATE_OBJECTIVE);
    }
}
