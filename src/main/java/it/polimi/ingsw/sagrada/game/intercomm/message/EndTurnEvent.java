package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class EndTurnEvent implements Message {
    private int idPlayer;

    public EndTurnEvent(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
