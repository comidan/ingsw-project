package it.polimi.ingsw.sagrada.game.base.state;

import java.util.*;
import java.util.stream.IntStream;



/**
 * PlayerIterator class, its job is to iterate over players and decide who's up next in a turn during the game.
 */
public class PlayerIterator implements Iterator<String> {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    /** The current player. */
    private String currentPlayer;

    /** The players. */
    private List<String> players;

    /** The removed players. */
    private List<String> removedPlayers;

    /** The momentary removed player. */
    private List<String> momentaryRemovedPlayer;

    /** The turn iteration. */
    private List<String> turnIteration;

    /** The num player. */
    private int numPlayer;

    /** The num P. */
    private int numP;

    /** The itr. */
    private int itr;

    /** The turn num. */
    private int turnNum;

    /**
     * Instantiates a new player iterator.
     *
     * @param players players list as strings
     */
    public PlayerIterator(List<String> players) {
        this.players = new ArrayList<>(players);
        turnIteration  = new ArrayList<>();
        removedPlayers = new ArrayList<>();
        momentaryRemovedPlayer = new ArrayList<>();
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
        if(itr < numP*2 && turnNum < MAX_ROUND) {
            if(checkPlayerLeft(itr)) return true;
            else {
                momentaryRemovedPlayer.clear();
                itr=0;
                turnNum++;
                getRoundSequence();
                return false;
            }
        }
        momentaryRemovedPlayer.clear();
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
        if(itr >= 2 * numP) throw new NoSuchElementException();
        String p;
        do {
            p=turnIteration.get(itr++);
        }while(removedPlayers.contains(p) || momentaryRemovedPlayer.contains(p));
        currentPlayer = p;
        return p;
    }

    /**
     * Gets the round sequence, internal use only.
     *
     * @return the round sequence
     */
    private void getRoundSequence() {
        turnIteration.clear();

        List<String> currentPlayer = new ArrayList<>();

        players.forEach(p -> {
            if(!removedPlayers.contains(p)) currentPlayer.add(p);
        });
        numP = numPlayer-removedPlayers.size();

        int offset = turnNum % numP;
        IntStream.range(0, (numPlayer-removedPlayers.size())*2).forEach(i -> {
            if(i<numP) {
                String currentP = currentPlayer.get(i+offset);
                turnIteration.add(currentP);
            }
            else {
                String currentP2 = currentPlayer.get(2*numP-1-i+offset);
                turnIteration.add(currentP2);
            }
        });
        turnIteration.forEach(elem -> System.out.println(elem));
    }

    /**
     * Check player left.
     *
     * @param index the index
     * @return true, if successful
     */
    private boolean checkPlayerLeft(int index) {
        for(int i=index; i<turnIteration.size(); i++) {
            if(!removedPlayers.contains(turnIteration.get(i)) && !momentaryRemovedPlayer.contains(turnIteration.get(i)))
                return true;
        }
        return false;
    }

    /**
     * Gets the current player number.
     *
     * @return the current player number
     */
    public int getCurrentPlayerNumber() {
        return numPlayer-removedPlayers.size();
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public String getCurrentPlayer() { return currentPlayer; }

    /**
     * Removes player from iteration and coming up rounds. Called only by a direct order from above.
     *
     * @param playerId player's id which is going to be removed
     */
    public void removePlayer(String playerId) {
        synchronized (removedPlayers) {
            removedPlayers.add(playerId);
            while(turnIteration.contains(playerId)) turnIteration.remove(playerId);
        }
    }

    /**
     * Adds player from iteration and coming up rounds. Called only by a direct order from above.
     *
     * @param playerId player's id which is going to be added
     */
    public void addPlayer(String playerId) {
        synchronized (removedPlayers) {
            removedPlayers.remove(playerId);
        }
    }

    /**
     * Can apply tool change.
     *
     * @param idPlayer the id player
     * @return true, if successful
     */
    public boolean canApplyToolChange(String idPlayer) {
        if(itr<=turnIteration.size()/2) {
            momentaryRemovedPlayer.add(idPlayer);
            return true;
        } else {
            return false;
        }
    }
}