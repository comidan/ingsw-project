package it.polimi.ingsw.sagrada.game.base;

import java.util.*;

import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.*;
import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.SCORE;
import static it.polimi.ingsw.sagrada.game.base.StateGameEnum.TURN;

public class PlayerIterator implements Iterator<Player> {


    private static PlayerIterator playerIterator;
    private Player currentPlayer;
    private int turnNumber;
    private int playerNumber;
    private int roundNumber;
    private ReverseCircularList<Player> playerList;

    private PlayerIterator(List<Player> players) {
        turnNumber = 0;
        roundNumber = 1;
        playerList = new ReverseCircularList<>();
        playerList.addAll(players);
        currentPlayer = null;
        this.playerNumber = playerList.size();

    }


    public static PlayerIterator getPlayerIterator(List<Player> players) {
        if (playerIterator == null) playerIterator = new PlayerIterator(players);
        return playerIterator;
    }

    public List<Player> playerList() {
        return this.playerList;
    }


    @Override
    public boolean hasNext() {
        return (turnNumber < playerNumber * 2);
    }


    private int selectStarterPlayer() {
        Random rand = new Random();
        return rand.nextInt(playerNumber);
    }


    @Override
    public Player next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException();
        if (roundNumber == 1 && turnNumber == 0)
            playerList.setOffset(selectStarterPlayer());
        currentPlayer = playerList.get(turnNumber);
        turnNumber++;
        if (turnNumber == playerNumber * 2) {
            playerList.setOffset(playerList.getOffset() + 1);
            roundNumber++;
        }

        return currentPlayer;

    }
}
