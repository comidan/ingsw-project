package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class ErrorEvent implements Message {

    private String error;

    public ErrorEvent(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
