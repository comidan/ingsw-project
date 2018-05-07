package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.StateGameController;
import it.polimi.ingsw.sagrada.game.base.StateIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {

    @Test
    public void testRound() {
        StateGameController state;
        Iterator<StateGameController> itr = StateIterator.getInstance();
        int index=0;

        List<StateGameController> stateSequence = new ArrayList<>();
        stateSequence.add(StateGameController.DEAL_PRIVATE_OBJECTIVE);
        stateSequence.add(StateGameController.DEAL_WINDOWS);
        stateSequence.add(StateGameController.DEAL_TOOL);
        stateSequence.add(StateGameController.DEAL_PUBLIC_OBJECTIVE);
        for(int i=0; i<10; i++) {
            stateSequence.add(StateGameController.TURN);
        }
        stateSequence.add(StateGameController.SCORE);

        while(itr.hasNext()) {
            state = itr.next();
            assertEquals(stateSequence.get(index), state);
            index++;
        }
    }
}
