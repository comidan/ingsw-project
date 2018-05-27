package it.polimi.ingsw.sagrada.network;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public enum LoginState implements Message {
    AUTH_OK,
    AUTH_FAILED_USER_ALREADY_LOGGED,
    AUTH_FAILED_USER_NOT_EXIST,
    AUTH_WRONG_PASSWORD,
    AUTH_FATAL_ERROR;

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
