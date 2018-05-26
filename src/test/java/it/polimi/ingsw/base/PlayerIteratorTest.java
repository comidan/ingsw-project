package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.state.PlayerIterator;
import it.polimi.ingsw.sagrada.game.base.state.StateGameEnum;
import it.polimi.ingsw.sagrada.game.base.state.StateIterator;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {
    private String[][] results = {
            {"Aldo", "Giovanni", "Giacomo", "Giacomo", "Giovanni", "Aldo"},
            {"Giovanni", "Giacomo", "Aldo", "Aldo", "Giacomo", "Giovanni"},
            {"Giacomo", "Aldo", "Giovanni", "Giovanni", "Aldo", "Giacomo"},
            {"Aldo", "Giovanni", "Giacomo", "Giacomo", "Giovanni", "Aldo"},
            {"Giovanni", "Giacomo", "Aldo", "Aldo", "Giacomo", "Giovanni"},
            {"Giacomo", "Aldo", "Giovanni", "Giovanni", "Aldo", "Giacomo"},
            {"Aldo", "Giovanni", "Giacomo", "Giacomo", "Giovanni", "Aldo"},
            {"Giovanni", "Giacomo", "Aldo", "Aldo", "Giacomo", "Giovanni"},
            {"Giacomo", "Aldo", "Giovanni", "Giovanni", "Aldo", "Giacomo"},
            {"Aldo", "Giovanni", "Giacomo", "Giacomo", "Giovanni", "Aldo"}};

    @Test
    public void testPlayerIterator() {
        int index = 0;
        List<String> playerList = new ArrayList<>();
        playerList.add("Aldo");
        playerList.add("Giovanni");
        playerList.add("Giacomo");

        StateIterator stateIterator = StateIterator.getInstance();
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
