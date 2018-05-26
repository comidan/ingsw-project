package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class BeginTurnEvent implements Message {

    private String idPlayer;

    public BeginTurnEvent(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return null;
    }
}
