package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class BeginTurnEvent implements Message {

    private int idPlayer;

    public BeginTurnEvent(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return null;
    }
}
