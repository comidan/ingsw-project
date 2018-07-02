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

    private String[][] resultsRemove = {
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}, //1round
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER}, //2round
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER}, //3round
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}, //4round
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER}, //5round
            {THIRD_USER, SECOND_USER, SECOND_USER, THIRD_USER}, //6round
            {SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER}, //7round
            {THIRD_USER, SECOND_USER, SECOND_USER, THIRD_USER}, //8round
            {SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER}, //9round
            {THIRD_USER, SECOND_USER, SECOND_USER, THIRD_USER}}; //10round

    private String[][] resultsRemoveAdd = {
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}, //1round
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER}, //2round
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER}, //3round
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}, //4round
            {SECOND_USER, THIRD_USER, FIRST_USER, FIRST_USER, THIRD_USER, SECOND_USER}, //5round
            {THIRD_USER, SECOND_USER, SECOND_USER, THIRD_USER}, //6round
            {SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER}, //7round
            {THIRD_USER, SECOND_USER, SECOND_USER, THIRD_USER}, //8round
            {THIRD_USER, FIRST_USER, SECOND_USER, SECOND_USER, FIRST_USER, THIRD_USER}, //9round
            {FIRST_USER, SECOND_USER, THIRD_USER, THIRD_USER, SECOND_USER, FIRST_USER}}; //10round

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
                String p = playerIterator.next();
                assertEquals(results[stateIterator.getRoundNumber()-1][index++], p);
            }
            index = 0;
        }
        stateIterator.forceState(null);
    }

    @Test
    public void testPlayerIteratorRemove() {
        int index = 0;
        List<String> playerList = new ArrayList<>();
        playerList.add(FIRST_USER);
        playerList.add(SECOND_USER);
        playerList.add(THIRD_USER);

        StateIterator stateIterator = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());
        stateIterator.forceState(StateGameEnum.DEAL_WINDOWS);
        PlayerIterator playerIterator = new PlayerIterator(playerList);
        while(stateIterator.next()==StateGameEnum.TURN) {
            if(stateIterator.getRoundNumber()==6) {
                playerIterator.removePlayer(FIRST_USER);
            }
            while (playerIterator.hasNext()) {
                String p = playerIterator.next();
                assertEquals(resultsRemove[stateIterator.getRoundNumber()-1][index++], p);
            }
            index = 0;
        }
        stateIterator.forceState(null);
    }

    @Test
    public void testPlayerIteratorRemoveAdd() {
        int index = 0;
        List<String> playerList = new ArrayList<>();
        playerList.add(FIRST_USER);
        playerList.add(SECOND_USER);
        playerList.add(THIRD_USER);

        StateIterator stateIterator = StateIteratorSingletonPool.getStateIteratorInstance(hashCode());
        stateIterator.forceState(StateGameEnum.DEAL_WINDOWS);
        PlayerIterator playerIterator = new PlayerIterator(playerList);
        while(stateIterator.next()==StateGameEnum.TURN) {
            if(stateIterator.getRoundNumber()==6) {
                playerIterator.removePlayer(FIRST_USER);
            }
            if(stateIterator.getRoundNumber()==8) {
                playerIterator.addPlayer(FIRST_USER);
            }
            while (playerIterator.hasNext()) {
                String p = playerIterator.next();
                assertEquals(resultsRemoveAdd[stateIterator.getRoundNumber()-1][index++], p);
            }
            index = 0;
        }
        stateIterator.forceState(null);
    }
}
