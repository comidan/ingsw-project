package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.state.PlayerIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateIteratorSingletonPool;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {

    private static final String FIRST_USER = "Aldo";
    private static final String SECOND_USER = "Giovanni";
    private static final String THIRD_USER = "Giacomo";

    private String[][] results = {
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER},
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER},
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER},
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER},
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER},
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER},
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER},
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER},
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER},
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}};

    @Test
    public void testPlayerIterator() {
        int index = 0;
        List<String> playerList = new ArrayList<>();
        playerList.add(FIRST_USER);
        playerList.add(SECOND_USER);
        playerList.add(THIRD_USER);

        StateIterator stateIterator = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());
        stateIterator.forceState(StateGameEnum.DEAL_WINDOWS);
        PlayerIterator playerIterator = new PlayerIterator(playerList);
        while(stateIterator.next()==StateGameEnum.TURN) {
            while (playerIterator.hasNext()) {
                assertEquals(results[stateIterator.getRoundNumber()-1][index++], playerIterator.next());
            }
            index = 0;
        }
        stateIterator.forceState(null);
    }
}
