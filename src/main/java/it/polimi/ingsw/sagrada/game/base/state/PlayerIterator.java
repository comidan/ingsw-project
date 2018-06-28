package it.polimi.ingsw.sagrada.game.base.state;

import java.util.*;
import java.util.stream.IntStream;


/**
 * PlayerIterator class, its job is to iterate over players and decide who's up next in a turn during the game
 */
public class PlayerIterator implements Iterator<String> {

    private static final int MAX_ROUND = 10;

    private List<String> players;

    private List<String> turnIteration;

    private int numPlayer;

    private int itr;

    private int turnNum;

    /**
     * Instantiates a new player iterator.
     *
     * @param players players list as strings
     */
    public PlayerIterator(List<String> players) {
        this.players = new ArrayList<>(players);
        turnIteration  = new ArrayList<>();
        int size = players.size();
        IntStream.range(0, size - 1).forEach(i -> this.players.add(players.get(i)));
        this.itr = 0;
        this.numPlayer = size;
        this.turnNum = 0;
        getRoundSequence();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        if(itr < 2 * numPlayer && turnNum < MAX_ROUND)
            return true;
        itr=0;
        turnNum++;
        getRoundSequence();
        return false;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public String next() {
        if(itr >= 2 * numPlayer) throw new NoSuchElementException();
        return turnIteration.get(itr++);
    }

    /**
     * Gets the round sequence, internal use only
     */
    private void getRoundSequence() {
        turnIteration.clear();
        int offset = turnNum % numPlayer;
        IntStream.range(0, 2 * numPlayer).forEach(i -> {
            if(i<numPlayer) {
                turnIteration.add(players.get(i+offset));
            }
            else {
                turnIteration.add(players.get(2*numPlayer-1-i+offset));
            }
        });
    }

    /**
     * Removes player from iteration and coming up rounds. Called only by a direct order from above.
     *
     * @param playerId player's id which is going to be removed
     */
    public void removePlayer(String playerId) {
        synchronized (players) {
            players.remove(playerId);
            numPlayer = players.size();
            getRoundSequence();
        }
    }

    /**
     * Adds player from iteration and coming up rounds. Called only by a direct order from above.
     *
     * @param playerId player's id which is going to be added
     */
    public void addPlayer(String playerId) {
        synchronized (players) {
            players.add(playerId);
            numPlayer = players.size();
            getRoundSequence();
        }
    }
}