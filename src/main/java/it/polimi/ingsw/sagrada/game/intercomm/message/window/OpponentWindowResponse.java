package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class OpponentWindowResponse.
 */
public class OpponentWindowResponse implements Message, ResponseVisitor {

    /** The players. */
    private List<String> players;
    
    /** The windows. */
    private Map<String, Pair<Integer, WindowSide>> windows;

    /**
     * Instantiates a new opponent window response.
     *
     * @param players the players
     * @param windows the windows
     * @param sides the sides
     */
    public OpponentWindowResponse(List<String> players, List<Integer> windows, List<WindowSide> sides) {
        this.windows = new HashMap<>();
        this.players = players;
        players.forEach(player -> this.windows.put(player,  new Pair<Integer, WindowSide>(windows.get(players.indexOf(player)), sides.get(player.indexOf(player)))));
    }


    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /**
     * Gets the players.
     *
     * @return the players
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Gets the player window id.
     *
     * @param username the username
     * @return the player window id
     */
    public Integer getPlayerWindowId(String username) {
        return windows.get(username).getFirstEntry();
    }

    /**
     * Gets the player window side.
     *
     * @param username the username
     * @return the player window side
     */
    public WindowSide getPlayerWindowSide(String username) {
        return windows.get(username).getSecondEntry();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor)
     */
    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }

    /**
     * The Class Pair.
     *
     * @param <T> the generic type
     * @param <U> the generic type
     */
    private static class Pair<T, U> implements Serializable {
        
        /** The t. */
        private final T t;
        
        /** The u. */
        private final U u;

        /**
         * Instantiates a new pair.
         *
         * @param t the t
         * @param u the u
         */
        Pair(T t, U u) {
            this.t= t;
            this.u= u;
        }

        /**
         * Gets the first entry.
         *
         * @return the first entry
         */
        T getFirstEntry() {
            return t;
        }

        /**
         * Gets the second entry.
         *
         * @return the second entry
         */
        U getSecondEntry() {
            return u;
        }
    }
}
