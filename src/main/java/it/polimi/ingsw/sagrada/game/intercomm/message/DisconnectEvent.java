package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class DisconnectEvent implements Message{

    private String username;

    public DisconnectEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
