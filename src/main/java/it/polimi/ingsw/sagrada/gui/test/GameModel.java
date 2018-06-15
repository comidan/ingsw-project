package it.polimi.ingsw.sagrada.gui.test;

import java.util.ArrayList;
import java.util.List;



//TO BE FULLY IMPLEMENTED

/**
 * The Class GameModel.
 */
public class GameModel {


    /** The player list. */
    List<PlayerModel> playerList = new ArrayList<>();

    /**
     * Sets the player list.
     *
     * @param playerList the new player list
     */
    public void setPlayerList(List<PlayerModel> playerList) {
        this.playerList = playerList;
    }

    /**
     * Gets the player list.
     *
     * @return the player list
     */
    public List<PlayerModel> getPlayerList() {
        return playerList;
    }


}
