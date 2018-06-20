package it.polimi.ingsw.sagrada.game.base.state;

import java.util.HashMap;
import java.util.Map;

/**
 * This singleton pool iterators gets you the right iterator based on a specific lobby server in-game
 */
public class StateIteratorSingletonPool {

    /**
     * Hashed map representing various iterator per lobby server
     */
    private static Map<Integer, StateIterator> instances = new HashMap<>();

    /**
     * Private constructor to disallow instances of this class
     */
    private StateIteratorSingletonPool(){}

    /**
     *  Gets a specific game's state iterator
     *
     * @param hash the representing hashed value of a game
     * @return a specific game's state iterator, create new one if absent
     */
    public static StateIterator getStateIteratorInstance(int hash) {
        instances.computeIfAbsent(hash, key -> instances.put(key, new StateIterator()));
        return instances.get(hash);
    }
}