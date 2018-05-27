package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.Player;

import java.util.ArrayList;
import java.util.List;


//TO BE FULLY IMPLEMENTED

public class GameModel {


    List<PlayerModel> playerList = new ArrayList<>();

    public void setPlayerList(List<PlayerModel> playerList) {
        this.playerList = playerList;
    }

    public List<PlayerModel> getPlayerList() {
        return playerList;
    }


}
