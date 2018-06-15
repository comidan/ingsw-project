package it.polimi.ingsw.sagrada.game.base.state;

import java.util.*;


/**
 * PlayerIterator class, its job is to iterate over players and decide who's up next in a turn during the game
 */
public class PlayerIterator implements Iterator<String> {

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
        this.players = players;
        turnIteration  = new ArrayList<>();
        int size = players.size();
        for(int i=0; i<size-1; i++) { //example 0-1-2-0-1
            this.players.add(players.get(i));
        }
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
        if(itr<2*numPlayer && turnNum<10) return true;
        else {
            itr=0;
            turnNum++;
            getRoundSequence();
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public String next() {
        if(itr>=2*numPlayer) throw new NoSuchElementException();
        return turnIteration.get(itr++);
    }

    /**
     * Gets the round sequence, internal use only
     */
    private void getRoundSequence() {
        turnIteration.clear();
        int offset = turnNum%numPlayer;
        for(int i=0; i<2*numPlayer; i++) {
            if(i<numPlayer) {
                turnIteration.add(players.get(i+offset));
            }
            else {
                turnIteration.add(players.get(2*numPlayer-1-i+offset));
            }
        }
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
}
