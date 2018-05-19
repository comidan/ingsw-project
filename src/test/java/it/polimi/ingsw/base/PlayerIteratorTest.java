package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.state.PlayerIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {
    private int[][] results = {
            {0, 1, 2, 2, 1, 0},
            {1, 2, 0, 0, 2, 1},
            {2, 0, 1, 1, 0, 2},
            {0, 1, 2, 2, 1, 0},
            {1, 2, 0, 0, 2, 1},
            {2, 0, 1, 1, 0, 2},
            {0, 1, 2, 2, 1, 0},
            {1, 2, 0, 0, 2, 1},
            {2, 0, 1, 1, 0, 2},
            {0, 1, 2, 2, 1, 0}};

    @Test
    public void testPlayerIterator() {
        int index = 0;
        List<Integer> playerList = new ArrayList<>();
        playerList.add(0);
        playerList.add(1);
        playerList.add(2);

        StateIterator stateIterator = StateIterator.getInstance();
        stateIterator.forceState(StateGameEnum.DEAL_WINDOWS);
        PlayerIterator playerIterator = new PlayerIterator(playerList);
        while(stateIterator.next()==StateGameEnum.TURN) {
            while (playerIterator.hasNext()) {
                assertEquals(results[stateIterator.getRoundNumber()-1][index++], playerIterator.next().intValue());
            }
            index = 0;
        }
        stateIterator.forceState(null);
    }
}
