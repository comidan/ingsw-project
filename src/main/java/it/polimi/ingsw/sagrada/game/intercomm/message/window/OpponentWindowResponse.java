package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
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
        players.forEach(player -> this.windows.put(player,  new Pair<Integer, WindowSide>(windows.get(players.indexOf(player)), sides.get(players.indexOf(player)))));
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
}
